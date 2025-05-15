/* (C) 2024 */
package com.quick.immi.ai.config;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AWSClientConfig {

  @Bean
  public AWSCognitoIdentityProvider getAWSCognitoIdentityClient() {
    return AWSCognitoIdentityProviderClientBuilder.standard().build();
  }

  @Bean
  public S3Client getAWSS3Client() {
    return S3Client.builder().credentialsProvider(DefaultCredentialsProvider.create()).build();
  }

  @Bean
  public S3Presigner s3Presigner() {
    return S3Presigner.builder().build();
  }

  @Bean
  public AmazonSimpleEmailService getAmazonSimpleEmailService() {
    return AmazonSimpleEmailServiceClientBuilder.standard().build();
  }

  @Bean
  public AmazonSQS getAmazonSQSClient() {
    return AmazonSQSClientBuilder.defaultClient();
  }
}
