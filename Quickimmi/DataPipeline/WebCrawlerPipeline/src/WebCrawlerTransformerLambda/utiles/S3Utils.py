import hashlib
import boto3
import re

class S3Utils:
    # s3 = boto3.client('s3', region_name="us-west-2")
    def __init__(self, logger, bucket_name):
        self.s3 = boto3.client('s3')
        self.logger = logger
        self.bucket_name = bucket_name

    def get_object_by_s3_key(self, s3Key):
        try:
            response = self.s3.get_object(Bucket=self.bucket_name, Key=s3Key)
            return response['Body'].read()
        except Exception as e:
            self.logger.error(f"Error getting object from S3 (s3Key: {s3Key}): {str(e)}")
            raise e

    def save_object_to_s3(self, s3Key, content):
        try:
            # Upload the content to S3
            self.s3.put_object(
                Bucket=self.bucket_name,
                Key=s3Key,
                Body=content
            )

            self.logger.info(f"Object saved to S3 (s3Key: {s3Key}, bucket: {self.bucket_name})")
        except Exception as e:
            self.logger.error(f"Error saving object to S3 (s3Key: {s3Key}): {str(e)}")
            raise e

    def delete_object_by_s3_key(self, s3Key):
        try:
            # Delete the object from S3
            self.s3.delete_object(
                Bucket=self.bucket_name,
                Key=s3Key
            )

            self.logger.info(f"Object deleted from S3 (s3Key: {s3Key}, bucket: {self.bucket_name})")
        except Exception as e:
            self.logger.error(f"Error deleting object from S3 (s3Key: {s3Key}): {str(e)}")
            raise e

    @staticmethod
    def get_content_md5(body):
        if isinstance(body, str):
            body = body.encode('utf-8')
        # create md5 hash object and update with body bytes
        hash_object = hashlib.md5()
        hash_object.update(body)

        # get hexadecimal representation of hash
        return hash_object.hexdigest()

    @staticmethod
    def convert_url_to_s3_key(url):
        # Remove "http://" or "https://" from the URL
        url = url.replace("http://", "").replace("https://", "")
        # Replace slashes and special characters with hyphens
        s3_key = re.sub(r'[^a-zA-Z0-9-_]+', '-', url)
        return s3_key