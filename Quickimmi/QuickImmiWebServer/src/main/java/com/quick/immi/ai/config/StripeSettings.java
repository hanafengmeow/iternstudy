/* (C) 2024 */
package com.quick.immi.ai.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.annotations.NotNull;

@Data
@Configuration
public class StripeSettings {

  @NotNull
  @Value("${com.quickimmi.stripe.api.key}")
  private String apiKey;

  @NotNull
  @Value("${com.quickimmi.stripe.webhook.secret}")
  private String webhookSecretEndpointSecret;
}
