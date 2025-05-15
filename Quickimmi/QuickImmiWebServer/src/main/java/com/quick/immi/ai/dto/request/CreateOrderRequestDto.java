/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.PaymentMethod;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateOrderRequestDto {
  private Long caseId;
  private Integer userId;
  private String email;
  private String product;
  private Integer totalMoney;
  private String currency;
  private PaymentMethod paymentMethod;
}
