package utils;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.InputStream;

public class S3Utils {
    private final AmazonS3 s3;

    public S3Utils() {
        // Create an AmazonS3 client using the default credentials provider chain
        // You can also specify your AWS region and endpoint if needed
        AWSCredentialsProvider credentialsProvider = DefaultAWSCredentialsProviderChain.getInstance();
        this.s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .build();
    }

    public String writePageContentToS3(String bucketName, String taskId, String url, InputStream stream) {
        String s3Key = String.format("%s/%s", taskId, convertUrlToS3Key(url));
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(stream.available()); // Set the content length
            PutObjectRequest request = new PutObjectRequest(bucketName, s3Key, stream, metadata);
            this.s3.putObject(request);
            return s3Key;
        } catch (Exception exp) {
            throw new RuntimeException(String.format("fail to write data into S3, taskId=%s, url=%s", taskId, url), exp);
        }
    }

    private String convertUrlToS3Key(String url) {
        // Remove "http://" or "https://" from the URL
        url = url.replace("http://", "").replace("https://", "");
        // Replace slashes and special characters with hyphens
        String s3Key = url.replaceAll("[^a-zA-Z0-9-_]+", "-");
        return s3Key;
    }
}
