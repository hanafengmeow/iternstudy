/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.ApplicationCaseProgress;
import com.quick.immi.ai.entity.CaseProgressStep;
import lombok.Data;

@Data
public class UpdateProgressRequestDto {
  private Long caseId;
  private CaseProgressStep currentStep;
  private ApplicationCaseProgress asylumCaseProgress;
}
