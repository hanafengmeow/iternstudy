package crawler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.common.collect.Lists;
import model.FinishedURLQueueMessage;
import model.UnvisitedUrlQueueMessage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import utils.DynamoDBUtils;
import utils.S3Utils;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static constant.Constant.MAIN_PAGE;
import static constant.Constant.THREAD_URL;

public class OneP3ArcWebCrawler extends DefaultWebCrawler{
    private static final String BASE_URL = "https://www.1point3acres.com/bbs";
    private static final String THREAD_URL_PREFIX = "https://www.1point3acres.com/bbs/thread-";
    private static final String THREAD_URL_SUFFIX = "-1-1.html";

    public OneP3ArcWebCrawler(LambdaLogger logger, DynamoDBUtils dynamoDBUtils) {
        super(logger, dynamoDBUtils);
    }

    @Override
    public void crawl(UnvisitedUrlQueueMessage message) {
        super.crawl(message);
    }

    @Override
    protected void extract(UnvisitedUrlQueueMessage message, Connection.Response response, String contentType) {
        if(message.getUrlType().equals(MAIN_PAGE)){
            //parse main pages and extract all thread links
            String body = response.body();
            Document doc = Jsoup.parse(body, BASE_URL);
            //1. select attributes with href
            Elements links = doc.select("a[href]");
            Set<String> urls = links.stream().map(link -> link.attr("abs:href")).collect(Collectors.toSet());
            List<String> threadBaseUrls = urls.stream()
                    .filter(u -> u.contains(THREAD_URL_PREFIX))
                    .filter(u -> u.endsWith(THREAD_URL_SUFFIX))
                    .collect(Collectors.toList());
            Set<String> newUrls = this.dynamoDBUtils.filterUnDiscoveredPages(threadBaseUrls, message.getTaskId());
            logger.log(String.format("discover new urls %s", newUrls));
            this.sqsUtils.sendUnvisitedUrlMessage(message, Lists.newArrayList(newUrls), Optional.of(THREAD_URL));
        } else if (message.getUrlType().equals(THREAD_URL)) {
            //save the thread pages into s3
            S3Utils s3Utils = new S3Utils();
            byte[] bytes = response.bodyAsBytes();
            String s3Key = s3Utils.writePageContentToS3(
                    System.getenv("WEBCRAWLERRAWPAGES_BUCKET_NAME"),
                    message.getTaskId(),
                    message.getUrl(),
                    new ByteArrayInputStream(bytes));
            dynamoDBUtils.finishPage(message.getTaskId(), message.getUrl(), contentType, s3Key);

            sqsUtils.sendFinishedUrlMessage(new FinishedURLQueueMessage(
                    message.getTaskId(),
                    message.getSource(),
                    message.getUrl(),
                    s3Key));
        } else {
            throw new RuntimeException(String.format("unknown url type with given message %s", message));
        }
    }

    @Override
    public Set<String> filter(Elements links, String rootUrl) {
        return null;
    }
}
