/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.CaseType;
import com.quick.immi.ai.entity.asylum.AsylumType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateAsylumCaseByLawyerRequestDto extends CreateCaseByLawyerRequestDto {
  private AsylumType asylumType;
  private CaseType caseType = CaseType.Asylum;
}
