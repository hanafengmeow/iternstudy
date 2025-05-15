/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.asylum.AsylumType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateAsylumCaseByCustomerRequestDto extends CreateCaseByCustomerRequestDto {
  private String reason;
  private AsylumType asylumType;
}
