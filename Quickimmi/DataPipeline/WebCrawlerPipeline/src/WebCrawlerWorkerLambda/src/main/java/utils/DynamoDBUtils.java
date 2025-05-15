package utils;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import constant.Constant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DynamoDBUtils {
    private final AmazonDynamoDB client;
    private final DynamoDB dynamoDB;
    private LambdaLogger logger;
    private String tableName;

    public DynamoDBUtils(LambdaLogger logger) {
        // Create an AmazonDynamoDB client using the default credentials provider chain
        // You can also specify your AWS region and endpoint if needed
        AWSCredentialsProvider credentialsProvider = DefaultAWSCredentialsProviderChain.getInstance();
        this.client = AmazonDynamoDBClientBuilder.standard().withCredentials(credentialsProvider).build();
        this.dynamoDB = new DynamoDB(client);
        this.logger = logger;
        this.tableName = System.getenv("PAGECRAWLINGTASK_TABLE_NAME");
    }

    // Put an item into the DynamoDB table
    public void putItem(Map<String, AttributeValue> item) {
        PutItemRequest putItemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
        this.client.putItem(putItemRequest);
    }

    // Update an item in the DynamoDB table
    public void finishPage(String task,
                           String url,
                           String contentType,
                           String s3Key) {
        // Specify the key of the item to update
        Map<String, AttributeValue> key = createKey(task, url);

        // Create an attribute value map for the update expression
        Map<String, AttributeValue> attributeValues = new HashMap<>();
        attributeValues.put(":val1", new AttributeValue(Constant.FINISH));
        attributeValues.put(":val2", new AttributeValue(contentType));
        attributeValues.put(":val3", new AttributeValue(s3Key));

        // Create an update expression
        String updateExpression = "SET #status = :val1, contentType = :val2, s3Key = :val3";

        Map<String, String> attributeNames = new HashMap<>();
        attributeNames.put("#status", "status");

        // Create an UpdateItemRequest
        UpdateItemRequest updateItemRequest = new UpdateItemRequest()
                .withTableName(this.tableName)
                .withKey(key)
                .withUpdateExpression(updateExpression)
                .withExpressionAttributeValues(attributeValues)
                .withExpressionAttributeNames(attributeNames)
                .withReturnValues("ALL_NEW"); // Specify the return values you want

        // Perform the update
        client.updateItem(updateItemRequest);
    }

    // Update an item in the DynamoDB table
    public void failPage(String task,
                         String url,
                         String errorMessage) {
        // Specify the key of the item to update
        Map<String, AttributeValue> key = createKey(task, url);

        // Create an attribute value map for the update expression
        Map<String, AttributeValue> attributeValues = new HashMap<>();
        attributeValues.put(":val1", new AttributeValue(Constant.FAIL));
        attributeValues.put(":val2", new AttributeValue(errorMessage));

        // Create an update expression
        String updateExpression = "SET #status = :val1, errorMessage = :val2";

        Map<String, String> attributeNames = new HashMap<>();
        attributeNames.put("#status", "status");

        // Create an UpdateItemRequest
        UpdateItemRequest updateItemRequest = new UpdateItemRequest()
                .withTableName(this.tableName)
                .withKey(key)
                .withUpdateExpression(updateExpression)
                .withExpressionAttributeValues(attributeValues)
                .withExpressionAttributeNames(attributeNames)
                .withReturnValues("ALL_NEW"); // Specify the return values you want

        // Perform the update
        client.updateItem(updateItemRequest);
    }

    public Map<String, AttributeValue> getItem(String taskId, String url) {
        GetItemRequest getItemRequest = new GetItemRequest()
                .withTableName(this.tableName).withKey(createKey(taskId, url));
        return this.client.getItem(getItemRequest).getItem();
    }

    public Set<String> filterUnDiscoveredPages(Collection<String> urls, String taskId) {
        if (urls == null || urls.isEmpty()) {
            return Sets.newHashSet();
        }
        //one batch size of DynamoDB is 100, thus we need to split the urls into batch
        List<List<String>> partitions = Lists.partition(new ArrayList<>(urls), 100);
        Set<String> result = Sets.newHashSet();
        partitions.forEach(urlList -> result.addAll(batchFilter(urlList, taskId)));
        return result;
    }

    public void createNewPages(Collection<String> urls,
                               String taskId,
                               String parent,
                               Integer depth) {
        if (urls == null || urls.isEmpty()) {
            return;
        }
        List<List<String>> partitions = Lists.partition(new ArrayList<>(urls), 25);
        partitions.forEach(urlList -> createNewPagesBatch(this.tableName, urlList, taskId, parent, depth));
    }

    private void createNewPagesBatch(String tableName,
                                     Collection<String> urls,
                                     String taskId,
                                     String parent,
                                     Integer depth) {

        List<Item> items = urls.stream().map(url -> newPageEntryItem(url, taskId, parent, depth))
                .collect(Collectors.toList());

        // Create a batch write operation
        TableWriteItems itemsToWrite = new TableWriteItems(tableName);
        // Add items to put, update, or delete
        itemsToWrite.withItemsToPut(items);
        // Create a BatchWriteItemOperation and execute the batch write
        dynamoDB.batchWriteItem(itemsToWrite);
    }

    private Set<String> batchFilter(Collection<String> urls, String taskId) {
        // Create a BatchGetItemRequest
        List<Map<String, AttributeValue>> keys = urls.stream().map(url -> createKey(taskId, url)).collect(Collectors.toList());
        logger.log(String.format("taskId=%s, createKeys = %s", taskId, keys));

        BatchGetItemRequest batchGetItemRequest = new BatchGetItemRequest();
        batchGetItemRequest.addRequestItemsEntry(this.tableName, new KeysAndAttributes().withKeys(keys));
        // Perform the batch get operation
        BatchGetItemResult batchGetItemResult = this.client.batchGetItem(batchGetItemRequest);

        // Check the results
        Map<String, List<Map<String, AttributeValue>>> responses = batchGetItemResult.getResponses();
        List<Map<String, AttributeValue>> results = responses.get(this.tableName);
        Set<String> existUrls = results.stream().map(m -> m.get("urlId").getS()).collect(Collectors.toSet());
        logger.log(String.format("taskId=%s, existUrls=%s", taskId, existUrls));
        return urls.stream().filter(url -> !existUrls.contains(url)).collect(Collectors.toSet());
    }

    private static Map<String, AttributeValue> createKey(String taskId, String url) {
        Map<String, AttributeValue> key = new HashMap<>();
        //taskId is the hashKey, url is sortKey
        key.put("taskId", new AttributeValue(taskId));
        key.put("urlId", new AttributeValue(url));
        return key;
    }

    private Item newPageEntryItem(String url,
                                  String taskId,
                                  String parent,
                                  Integer depth) {
        return new Item()
                .withString("urlId", url)
                .withString("taskId", taskId)
                .withString("parent", parent)
                .withString("depth", depth.toString())
                .withString("createAt", String.valueOf(System.currentTimeMillis()))
                .withString("status", Constant.START);
    }
}