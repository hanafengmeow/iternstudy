/* (C) 2024 */
package com.quick.immi.ai.service.helper;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.gson.Gson;
import com.quick.immi.ai.entity.FormGenerationTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SQSService {

  @Autowired private AmazonSQS sqs;

  @Value("${com.quickimmi.document.generation.taskqueue.url}")
  private String queueUrl;

  @Value("${com.quickimmi.document.generation.coverletter.taskqueue.url}")
  private String coverLetterQueue;

  @Value("${com.quickimmi.document.generation.crawler.taskqueue.url}")
  private String crawlerQueue;

  // Send a message to an SQS queue
  public void sendMessageToJavaLambda(FormGenerationTask task) {
    try {
      sqs.sendMessage(new SendMessageRequest(queueUrl, new Gson().toJson(task)));
    } catch (Exception exp) {
      log.error(String.format("fail to send queue %s for task %s", queueUrl, task), exp);
      throw new RuntimeException(exp);
    }
  }

  public void sendMessageToPythonLambda(FormGenerationTask task) {
    try {
      sqs.sendMessage(new SendMessageRequest(coverLetterQueue, new Gson().toJson(task)));
    } catch (Exception exp) {
      log.error(String.format("fail to send queue %s for task %s", coverLetterQueue, task), exp);
      throw new RuntimeException(exp);
    }
  }

  public void sendMessageToPythonCrawlerLambda(FormGenerationTask task) {
    try {
      sqs.sendMessage(new SendMessageRequest(crawlerQueue, new Gson().toJson(task)));
    } catch (Exception exp) {
      log.error(String.format("fail to send queue %s for task %s", crawlerQueue, task), exp);
      throw new RuntimeException(exp);
    }
  }
}
