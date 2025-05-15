package crawler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.common.collect.Sets;
import constant.Constant;
import model.FinishedURLQueueMessage;
import model.UnvisitedUrlQueueMessage;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import utils.DynamoDBUtils;
import utils.RobotsParser;
import utils.S3Utils;
import utils.SQSUtils;
import utils.UrlUtils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static constant.Constant.PDF_CONTENT_TYPE;
import static constant.Constant.UNKNOWN;
import static utils.UrlUtils.extractBaseUrl;
import static utils.UrlUtils.isSameDomain;


/**
 *  A general web crawler used to crawler any website.
 */

// 通过bfs爬虫
public class DefaultWebCrawler implements WebCrawler {
    //403 is a retryable code.
    final static Set<Integer> NON_RETRY_HTTP_CODE = Sets.newHashSet(400, 404, 407, 405, 401);
    protected final LambdaLogger logger;
    protected final DynamoDBUtils dynamoDBUtils;
    protected final SQSUtils sqsUtils;

    final String byPassURL = "https://api.zenrows.com/v1/?apikey=b0704a2828b939aeb4f803c5c09db1b2725f3a18&url=";

    public DefaultWebCrawler(LambdaLogger logger, DynamoDBUtils dynamoDBUtils) {
        this.logger = logger;
        this.dynamoDBUtils = dynamoDBUtils;
        this.sqsUtils = new SQSUtils(logger);
    }

    // 爬去unvisited url
    @Override
    public void crawl(UnvisitedUrlQueueMessage message) {
        try {
            //1. crawl the page
            Connection.Response response = Jsoup.connect(message.getUrl())
                    .timeout(1000 * 30)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5)")
                    .ignoreContentType(true)
                    .execute();
            //2. check the content type
            String contentType = response.header("Content-Type");
            //3. process the crawled raw data
            //save the web page into S3
            if (contentType == null && message.getUrl().endsWith(".pdf")) {
                contentType = PDF_CONTENT_TYPE;
            } else if (contentType == null) {
                contentType = UNKNOWN;
            }
            extract(message, response, contentType);
            logger.log(String.format("successfully crawling url=%s for task Id=%s, curDepth=%s, content-type=%s",
                    message.getTaskId(),
                    message.getUrl(),
                    message.getCurDepth(),
                    contentType));
        } catch (HttpStatusException exp) {
            logger.log(String.format("fail to crawl %s due to %s", message.toJson(), exp.getMessage()));
            if (NON_RETRY_HTTP_CODE.contains(exp.getStatusCode())) {
                dynamoDBUtils.failPage(message.getTaskId(), message.getUrl(), exp.getMessage());
            } else {
                throw new RuntimeException(String.format("Fail to crawling %s", message.toJson()), exp);
            }
        } catch (Exception exp) {
            throw new RuntimeException(String.format("Fail to crawling %s", message.toJson()), exp);
        }
    }

    //
    protected void extract(UnvisitedUrlQueueMessage message, Connection.Response response, String contentType) {
        S3Utils s3Utils = new S3Utils();
        // 读取网页内容
        byte[] bytes = response.bodyAsBytes();
        // 把读取内容放进S3
        String s3Key = s3Utils.writePageContentToS3(
                System.getenv("WEBCRAWLERWORKER_BUCKET_NAME"),
                message.getTaskId(),
                message.getUrl(),
                new ByteArrayInputStream(bytes));

        // page mark为finished status
        dynamoDBUtils.finishPage(message.getTaskId(), message.getUrl(), contentType, s3Key);
        sqsUtils.sendFinishedUrlMessage(new FinishedURLQueueMessage(
                message.getTaskId(),
                message.getSource(),
                message.getUrl(),
                s3Key));
        // 解析网页
        // 把没有爬取的url放进queue里；解释是不是html网页
        if (contentType.toLowerCase().contains(Constant.HTML_CONTENT_TYPE)) {
            extractLinksAndEnqueue(message, bytes);
        }
    }

    // extractLinksAndEnqueue
    private void extractLinksAndEnqueue(UnvisitedUrlQueueMessage message,
                                        byte[] bytesContent) {
        if (message.getCurDepth() == message.getDepth()) {
            return;
        }
        String htmlContent = new String(bytesContent);
        Set<String> unvisitedUrls = extractUnvisitedUrls(message, htmlContent);
        RobotsParser robotsParser = new RobotsParser(this.logger);
        List<String> shouldEnqueueUrls = robotsParser.filter(unvisitedUrls);
        this.logger.log(String.format("taskId=%s, url=%s, shouldEnqueueUrls=%s", message.getTaskId(), message.getUrl(), shouldEnqueueUrls));

        dynamoDBUtils.createNewPages(shouldEnqueueUrls, message.getTaskId(), message.getUrl(), message.getCurDepth() + 1);
        this.sqsUtils.sendUnvisitedUrlMessage(message, shouldEnqueueUrls, Optional.empty());
    }

    /**
     *
     */
    protected Set<String> extractUnvisitedUrls(UnvisitedUrlQueueMessage message,
                                             String htmlContent) {
        String baseUrl = extractBaseUrl(message.getUrl());
        Document doc = Jsoup.parse(htmlContent, baseUrl);
        //1. select attributes with href
        Elements links = doc.select("a[href]");
        //2. get all links with abs path, then
        Set<String> urls = filter(links, message.getRootUrl());

        logger.log(String.format("taskId=%s, url=%s, links=%s", message.getTaskId(), message.getUrl(), urls));
        return dynamoDBUtils.filterUnDiscoveredPages(urls, message.getTaskId());
    }

    @Override
    public Set<String> filter(Elements links, String rootUrl) {
        Set<String> urls = links.stream().map(link -> link.attr("abs:href"))
                .map(u -> normalize(u))
                .map(u -> u.trim())
                .filter(u -> !u.isEmpty())
                .filter(u -> u.startsWith("http") || u.startsWith("https"))
                .filter(u -> isSameDomain(u, rootUrl))
                .map(u -> UrlUtils.removeTailSlash(u))
                .collect(Collectors.toSet());
        return urls;
    }

    protected String normalize(String url){
        return UrlUtils.normalize(url);
    }

    //use zenrows proxy url key to bypass anti-bot
    public String getFallBackURL(String url) throws UnsupportedEncodingException {
        String encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        String apiUrl = byPassURL + encodedUrl;
        return apiUrl;
    }

    public List<String> generateSimilarUserAgents() {
        List<String> baseUserAgents = Arrays.asList(
                "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko",
                "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; AS; rv:11.0) like Gecko",
                "Opera/9.80 (Windows NT 6.1; Win64; x64) Presto/2.12.388 Version/12.15",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:12.0) like Gecko",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0",
                "Opera/9.80 (Windows NT 6.0) Presto/2.12.388 Version/12.14"
        );

        List<String> similarUserAgents = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            String baseAgent = baseUserAgents.get(random.nextInt(baseUserAgents.size()));
            String suffix = generateRandomSuffix();
            similarUserAgents.add(baseAgent + " " + suffix);
        }

        return similarUserAgents;
    }

    public String generateRandomSuffix() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder suffix = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            suffix.append(characters.charAt(random.nextInt(characters.length())));
        }

        return suffix.toString();
    }
}
