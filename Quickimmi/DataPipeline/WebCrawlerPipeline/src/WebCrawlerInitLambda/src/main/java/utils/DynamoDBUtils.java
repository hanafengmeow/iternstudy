package utils;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;

import java.util.Map;

public class DynamoDBUtils {
    private final AmazonDynamoDB client;
    private final DynamoDB dynamoDB;

    public DynamoDBUtils() {
        // Create an AmazonDynamoDB client using the default credentials provider chain
        // You can also specify your AWS region and endpoint if needed
        AWSCredentialsProvider credentialsProvider = DefaultAWSCredentialsProviderChain.getInstance();
        this.client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .build();
        this.dynamoDB = new DynamoDB(client);
    }

    // Get a reference to the DynamoDB table by table name
    public Table getTable(String tableName) {
        return dynamoDB.getTable(tableName);
    }

    // Put an item into the DynamoDB table
    public void putItem(String tableName, Map<String, AttributeValue> item) {
        PutItemRequest putItemRequest = new PutItemRequest()
                .withTableName(tableName)
                .withItem(item);
        this.client.putItem(putItemRequest);
    }

    // Get an item from the DynamoDB table by primary key
    public Item getItem(String tableName, String primaryKey, Object primaryValue) {
        Table table = getTable(tableName);
        return table.getItem(primaryKey, primaryValue);
    }

    // Update an item in the DynamoDB table
    public void updateItem(String tableName, String primaryKey, Object primaryValue, String updateAttribute, Object updateValue) {
        Table table = getTable(tableName);

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(primaryKey, primaryValue)
                .withUpdateExpression("set " + updateAttribute + " = :val")
                .withValueMap(new ValueMap().with(updateAttribute, updateValue))
                .withNameMap(new NameMap().with("#attrName", updateAttribute))
                .withReturnValues("UPDATED_NEW");

        table.updateItem(updateItemSpec);
    }
}