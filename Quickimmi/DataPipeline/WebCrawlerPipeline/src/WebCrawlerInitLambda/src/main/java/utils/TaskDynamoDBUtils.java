package utils;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import model.Task;
import model.TaskCreateException;

import java.util.HashMap;
import java.util.Map;

public class TaskDynamoDBUtils {

    public static void createTask(final Task task) throws TaskCreateException {
        try {
            String tableName = System.getenv("WEBCRAWLERTASK_TABLE_NAME");
            DynamoDBUtils dynamoDBUtils = new DynamoDBUtils();
            Map<String, AttributeValue> itemMap = new HashMap<>();

            itemMap.put("id", new AttributeValue(task.getId()));
            itemMap.put("source", new AttributeValue(task.getSource()));
            itemMap.put("request", new AttributeValue(task.getRequest()));
            itemMap.put("createAt", new AttributeValue(String.valueOf(System.currentTimeMillis())));

            dynamoDBUtils.putItem(tableName, itemMap);
        } catch (Exception exp) {
            throw new TaskCreateException("fail to crate crawling task with request " + task.getRequest(), exp);
        }
    }

    private static String getTaskId(String prefix) {
        return String.format("{}-" + System.currentTimeMillis(), prefix);
    }
}
