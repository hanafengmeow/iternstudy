import json
from datetime import datetime
import boto3
from boto3.dynamodb.conditions import Attr, Key
from botocore.exceptions import ClientError
import time
import os

dynamodb = boto3.resource('dynamodb', region_name=os.environ.get('AWS_REGION'))
class DynamoDBUtils:
    def __init__(self, table_name, logger, pk, sk) -> None:
        self.table_name = table_name
        self.pk = pk
        self.sk = sk
        self.table = dynamodb.Table(table_name)
        self.logger = logger

    def get_by_key(self, pk_value, sk_value):
        try:
            response = self.table.get_item(
                Key={
                    self.pk: pk_value,
                    self.sk: sk_value
                }
            )
            item = response.get('Item')
            self.logger.debug("Item %s %s retrieved successfully!", pk_value, sk_value)
            return item
        except Exception as e:
            self.logger.error("Error querying item %s %s: %s", pk_value, sk_value, e)
            raise e

    def delete_by_key(self, pk_value, sk_value):
        try:
            self.table.delete_item(
                Key={
                    self.pk: pk_value,
                    self.sk: sk_value
                }
            )
            self.logger.debug("Item with pk %s and sk %s deleted successfully!", pk_value, sk_value)
        except Exception as e:
            self.logger.error("Error deleting item: %s", e)
            raise e

    def query_and_delete_by_lsi(self, lsi_name, lsi_key, lsi_value, partition_key, partition_value):
        try:
            response = self.table.query(
                IndexName=lsi_name,
                KeyConditionExpression=Key(lsi_key).eq(lsi_value) & Key(partition_key).eq(partition_value)
            )
            items = response.get('Items')

            if items:
                self.logger.debug("Duplicate Items with %s eq %s found in LSI %s.", lsi_key, lsi_value, lsi_name)
                for item in items:
                    pk_value = item.get(self.pk)
                    sk_value = item.get(self.sk)
                    self.delete_by_key(pk_value, sk_value)
        except Exception as e:
            self.logger.error("Error querying and deleting items: %s", e)
            raise e

    def delete_by_condition(self, key, value):
        try:
            response = self.table.scan(
                FilterExpression=Attr(key).eq(value)
            )
            items = response.get('Items')
            with self.table.batch_writer() as batch:
                for item in items:
                    batch.delete_item(Key={'id': item['id']})
            self.logger.debug("Item deleted successfully! with %s eq %s", key, value)
        except Exception as e:
            self.logger.error("Error deleting item:  %s", e)
            raise e

    def check_item_exists(self, pk_value, sk_value):
        try:
            response = self.table.get_item(
                Key={
                    self.pk: pk_value,
                    self.sk: sk_value
                }
            )
            item = response.get('Item')
            if item:
                self.logger.debug("Item with PK %s and SK %s exists!", pk_value, sk_value)
                return True
            else:
                self.logger.debug("Item with PK %s and SK %s does not exist.", pk_value, sk_value)
                return False
        except Exception as e:
            self.logger.error("Error checking item existence: %s", e)
            raise e

    def update_item_attribute(self, pk_value, sk_value, attribute_name, new_value):
        try:
            # Update the item with a conditional update expression
            update_expression = f"SET {attribute_name} = :new_value"
            expression_attribute_values = {":new_value": new_value}

            # Update the item
            self.table.update_item(
                Key={
                    self.pk: pk_value,
                    self.sk: sk_value
                },
                UpdateExpression=update_expression,
                ExpressionAttributeValues=expression_attribute_values
            )
            self.logger.debug("Item with PK %s and SK %s, attribute %s updated to %s", pk_value, sk_value, attribute_name, new_value)
        except Exception as e:
            self.logger.error("Error updating item attribute: %s", e)
            raise e

    def insert(self, item):
        try:
            # timestamp = datetime.utcnow().isoformat() + 'Z'  # convert current time to ISO 8601 format
            item['createdAt'] = str(int(time.time() * 1000))
            self.table.put_item(
                Item=item
            )
        except ClientError as err:
            self.logger.error(
                "Couldn't add item %s to table %s. Here's why: %s: %s",
                json.dumps(item), self.table.name,
                err.response['Error']['Code'], err.response['Error']['Message'])
            raise err

    def batch_insert_items(self, items):
        if not items:
            return
        try:
            with self.table.batch_writer() as batch:
                for item in items:
                    timestamp = datetime.utcnow().isoformat() + 'Z'  # convert current time to ISO 8601 format
                    item['created_at'] = timestamp
                    batch.put_item(Item=item)
        except Exception as e:
            self.logger.error("Error put items: %s with error: %s", json.dumps(items), str(e))
            raise e