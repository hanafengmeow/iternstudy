import boto3
import json
import logging


class S3Utils:
    """Utility class for handling operations on AWS S3."""

    def __init__(self):
        """Initialize the S3 client."""
        self.s3 = boto3.client("s3")

    def save_to_s3(
        self,
        bucket_name: str,
        user_id: str,
        case_id: str,
        file_name: str,
        content: bytes,
        content_type: str = "application/pdf",
    ) -> None:
        """Save content to an S3 bucket."""
        key = f"{user_id}/{case_id}/{file_name}"
        try:
            self.s3.put_object(
                Bucket=bucket_name,
                Key=key,
                Body=content,
                ContentType=content_type,
            )
            s3_uri = f"s3://{bucket_name}/{key}"
            print(f"Document saved to S3 successfully. {s3_uri}")
            return s3_uri
        except Exception as e:
            print(f"Failed to save document to S3: {e}")
            raise

    def fetch_file(self, bucket: str, key: str):
        """Fetch a file from S3 and load its content based on file type."""
        try:
            response = self.s3.get_object(Bucket=bucket, Key=key)
            file_content = response["Body"].read()
            if key.endswith(".json"):
                return json.loads(file_content)
            elif key.endswith(".txt"):
                return file_content.decode("utf-8")
            else:
                raise ValueError("Unsupported file type")
        except Exception as e:
            logging.error(f"Failed to fetch file from S3: {e}")
            raise
