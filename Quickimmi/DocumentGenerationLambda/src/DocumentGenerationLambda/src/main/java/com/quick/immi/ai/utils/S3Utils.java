package com.quick.immi.ai.utils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.quick.immi.ai.entity.Env;
import com.quick.immi.ai.fillform.G28FormFiller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import static com.quick.immi.ai.constant.Constants.REGION_ENV_MAP;

public class S3Utils {
    private static final Logger logger = Logger.getLogger(S3Utils.class.getName());

    private final static AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

    public static String getCurrentRegion() {
        try {
            String awsRegion = System.getenv("AWS_REGION");
            if(awsRegion == null){
                awsRegion = System.getProperty("AWS_REGION");
                if(awsRegion == null){
                    awsRegion = "us-west-1";
                }
            }
            return awsRegion;
        } catch (Exception e) {
            throw new RuntimeException("fail to get ec2 region", e);
        }
    }

    public static String getCurrentGeneratedDocumentBucket(){
        Env env = REGION_ENV_MAP.get(getCurrentRegion());
        return env.getGeneratedDocumentBucket();
    }

    public static String getCurrentDocumentTemplateBucket(){
        Env env = REGION_ENV_MAP.get(getCurrentRegion());
        return env.getDocumentTemplateBucket();
    }

    public static void save(String bucket, String key, byte[] data) throws IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data)) {
            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentLength(data.length);
            s3.putObject(new PutObjectRequest(bucket, key, inputStream, metadata));
        }
    }

    public static S3ObjectInputStream getObjectContent(String bucket, String fileName) throws IOException {
        S3Object s3Object = s3.getObject(new GetObjectRequest(bucket, fileName));
        return s3Object.getObjectContent();
    }

    public static String getS3Location(String bucket, String key) {
        return String.format("s3://%s/%s", bucket, key);
    }
}
