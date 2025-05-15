/* (C) 2024 */
package com.quick.immi.ai.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDto {
  private String id;
  private String card;
  private Integer expMonth;
  private Integer expYear;
  private String cvc;
  private String brand;
}
