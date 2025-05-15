/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import com.quick.immi.ai.entity.ApplicationCaseProgress;
import com.quick.immi.ai.entity.CaseProgressStep;
import com.quick.immi.ai.entity.CaseType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CaseSummaryResponseDto {
  protected Long id;
  protected String caseName;
  protected String applicantName;
  protected CaseType caseType;
  protected CaseProgressStep currentStep;
  protected ApplicationCaseProgress progress;
  protected String desc;
  protected Long createdAt;
  protected Long updatedAt;
}
