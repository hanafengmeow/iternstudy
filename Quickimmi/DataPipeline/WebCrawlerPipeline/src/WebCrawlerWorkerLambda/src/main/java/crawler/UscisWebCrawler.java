package crawler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import model.UnvisitedUrlQueueMessage;
import org.jsoup.select.Elements;
import utils.DynamoDBUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class UscisWebCrawler extends DefaultWebCrawler {
    private String esLinkPrefixHttps = "https://www.uscis.gov/es";
    private String esLinkPrefixHttp = "http://www.uscis.gov/es/";

    public UscisWebCrawler(LambdaLogger logger, DynamoDBUtils dynamoDBUtils) {
        super(logger, dynamoDBUtils);
    }

    @Override
    public void crawl(UnvisitedUrlQueueMessage message) {
        super.crawl(message);
    }

    @Override
    public Set<String> filter(Elements links, String rootUrl) {
        Set<String> urls = super.filter(links, rootUrl);
        //need to filter out ES language
        return urls.stream().filter(u ->
                !u.startsWith(esLinkPrefixHttps)
                        && !u.startsWith(esLinkPrefixHttp)).collect(Collectors.toSet());
    }

    @Override
    protected String normalize(String url) {
        if(url.contains(".pdf")){
            return url;
        } else {
            return super.normalize(url);
        }
    }
}

