/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.CaseType;
import lombok.Data;

@Data
public class CreateCaseByCustomerRequestDto {
  private Integer userId;
  protected String caseName;
  protected String applicantName;
  protected CaseType caseType;
}
