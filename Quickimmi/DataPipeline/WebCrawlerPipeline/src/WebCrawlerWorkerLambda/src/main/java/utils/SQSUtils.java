package utils;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import model.FinishedURLQueueMessage;
import model.UnvisitedUrlQueueMessage;

import java.util.List;
import java.util.Optional;

public class SQSUtils {
    private final AmazonSQS sqs;
    private final LambdaLogger logger;

    public SQSUtils(LambdaLogger logger) {
        this.logger = logger;
        this.sqs = AmazonSQSClientBuilder.defaultClient();
    }

    // Send a message to an SQS queue
    public void sendMessage(String queueUrl, String messageBody) {
        sqs.sendMessage(new SendMessageRequest(queueUrl, messageBody));
    }

    public void sendUnvisitedUrlMessage(UnvisitedUrlQueueMessage message, List<String> urls, Optional<String> urlTypeOptional) {
        String queueUrl = System.getenv("UNVISITEDURL_QUEUE_URL");
        try {
            for (String url : urls) {
                UnvisitedUrlQueueMessage unvisitedUrlQueueMessage = new UnvisitedUrlQueueMessage()
                        .setTaskId(message.getTaskId())
                        .setDepth(message.getDepth())
                        .setCurDepth(message.getCurDepth() + 1)
                        .setParentURL(message.getUrl())
                        .setRootUrl(message.getRootUrl())
                        .setSource(message.getSource())
                        .setUrl(url);
                if(urlTypeOptional.isPresent()){
                    unvisitedUrlQueueMessage.setUrlType(urlTypeOptional.get());
                }
                String messageBody = unvisitedUrlQueueMessage.toJson();
                sqs.sendMessage(new SendMessageRequest(queueUrl, messageBody));
                logger.log(String.format("successfully sending message:%s to SQS %s", messageBody, queueUrl));
            }
        } catch (Exception exp) {
            throw new RuntimeException("Failed to sendUnvisitedUrlMessage, queueUrl = " + queueUrl, exp);
        }
    }

    public void sendFinishedUrlMessage(FinishedURLQueueMessage message) {
        String queueUrl = System.getenv("FINISHEDURL_QUEUE_URL");

        try {
            String messageBody = message.toJson();
            sqs.sendMessage(new SendMessageRequest(queueUrl, messageBody));
            logger.log(String.format("successfully sending message:%s to SQS %s", messageBody, queueUrl));
        } catch (Exception exp) {
            String errorMessage = "Failed to sendFinishedUrlMessage, queueUrl = " + queueUrl + ": " + exp.getMessage();
            throw new RuntimeException(errorMessage, exp);
        }
    }
}