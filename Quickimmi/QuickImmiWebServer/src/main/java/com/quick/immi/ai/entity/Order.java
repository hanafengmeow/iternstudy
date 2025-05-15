/* (C) 2024 */
package com.quick.immi.ai.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class Order {
  private Long id;
  // user id
  private Integer userId;
  // used case id
  private Long caseId;
  private Integer productId;
  private String productName;
  private Integer totalMoney;
  private String stripeOrderId;
  private String currency;
  private String paymentMethodType;
  private String status;
  private Long createdAt;
  private Long updatedAt;
}
