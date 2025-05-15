/* (C) 2024 */
package com.quick.immi.ai.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderResult {
  private String clientSecret;
  private String ephemeralKey;
  private String stripCustomerId;
  private String orderId;
}
