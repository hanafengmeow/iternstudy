import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import constant.Constant;
import crawler.UscisWebCrawler;
import crawler.WebCrawler;
import crawler.WebCrawlerFactory;
import model.UnvisitedUrlQueueMessage;
import utils.DynamoDBUtils;
import crawler.DefaultWebCrawler;

import java.util.Map;
import java.util.Random;

public class Handler implements RequestHandler<SQSEvent, Void> {

//    {
//        "taskId": "research-university-1702782198249",
//            "source": "research-university",
//            "url": "https://research.com/university-rankings/animal-science-and-veterinary/cn",
//            "curDepth": 0,
//            "depth": 1,
//            "parentURL": "https://research.com/",
//            "rootUrl": "https://research.com/university-rankings/animal-science-and-veterinary/cn"
//    }
    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        try {
            for (SQSEvent.SQSMessage msg : event.getRecords()) {
                logger.log("Received message: " + msg.getBody());
                //random delay from 1.5s to 5s
                randomDelay(1500, 5000);

                // 反序列化。把queue里的数据转换成结构化数据
                UnvisitedUrlQueueMessage message = new Gson().fromJson(msg.getBody(), UnvisitedUrlQueueMessage.class);
                DynamoDBUtils dynamoDBUtils = new DynamoDBUtils(logger);

                //avoid processing duplicated urls.
                //check该数据在db里有没有
                Map<String, AttributeValue> item = dynamoDBUtils.getItem(message.getTaskId(), message.getUrl());
                //corn case: for root url, which doesn't insert into DB yet.
                if (item == null) {
                    dynamoDBUtils.createNewPages(Lists.newArrayList(message.getUrl()),
                            message.getTaskId(), message.getParentURL(), message.getCurDepth());
                    logger.log("create new pages for message: " + message.toJson());
                }

                // 检查：如果page 爬完了，就停止爬这个page
                if(item != null && item.get("status").getS().equals(Constant.FINISH)) {
                    logger.log(String.format("page has been crawled %s -- ignore", message));
                    continue;
                }

                WebCrawler webCrawler = WebCrawlerFactory.getInstance(message.getSource(), logger);
                webCrawler.crawl(message);
            }
        } catch (Exception e) {
            throw new RuntimeException("fail to process event: " + new Gson().toJson(event), e);
        }
        return null;
    }

    private void randomDelay(Integer start, Integer end) throws InterruptedException {
        Random rand = new Random();
        Thread.sleep(start + rand.nextInt(end - start));
    }
}