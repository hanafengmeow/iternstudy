package crawler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import utils.DynamoDBUtils;

import static constant.Constant.*;

public class WebCrawlerFactory {

    public static WebCrawler getInstance(String source, LambdaLogger logger) {
        DynamoDBUtils dynamoDBUtils = new DynamoDBUtils(logger);
        if (source.startsWith(USCIS)) {
            return new UscisWebCrawler(logger, dynamoDBUtils);
        } else {
            throw new RuntimeException("Not support source: " + source);
        }
    }
}
