/* (C) 2024 */
package com.quick.immi.ai.config;

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient;
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class AzureClient {

  @Value("${com.quickimmi.azure.key}")
  private String azureKey;

  @Value("${com.quickimmi.azure.endpoint}")
  private String endpoint;

  @Bean
  public DocumentAnalysisClient getDocumentAnalysisClient() {
    return new DocumentAnalysisClientBuilder()
        .credential(new AzureKeyCredential(azureKey))
        .endpoint(endpoint)
        .buildClient();
  }
}
