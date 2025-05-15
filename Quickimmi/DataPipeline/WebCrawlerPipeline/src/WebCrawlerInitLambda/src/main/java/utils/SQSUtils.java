package utils;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import model.UnvisitedUrlQueueMessage;

import java.util.List;

public class SQSUtils {
    private final AmazonSQS sqs;
    private LambdaLogger logger;

    public SQSUtils(LambdaLogger logger) {
        this.logger = logger;
        this.sqs = AmazonSQSClientBuilder.defaultClient();
    }

    public void sendUnvisitedUrlMessage(String taskId, Integer depth, List<String> urls, String source) {
        String queueUrl = System.getenv("UNVISITEDURL_QUEUE_URL");
        try {
            for (String url : urls) {
                String transformedUrl = removeTailSlash(url);
                //root parent url is itself
                String messageBody = new UnvisitedUrlQueueMessage()
                        .setTaskId(taskId)
                        .setUrl(transformedUrl)
                        .setParentURL(transformedUrl)
                        .setCurDepth(0)
                        .setDepth(depth)
                        .setRootUrl(transformedUrl)
                        .setSource(source).toJson();
                sqs.sendMessage(new SendMessageRequest(queueUrl, messageBody));
                logger.log(String.format("successfully sending message:%s to SQS %s", messageBody, queueUrl));
            }
        } catch (Exception exp) {
            throw new RuntimeException("Failed to sendUnvisitedUrlMessage, queueUrl = " + queueUrl, exp);
        }
    }

    //remove the tail slash from the root urls.
    public static String removeTailSlash(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}