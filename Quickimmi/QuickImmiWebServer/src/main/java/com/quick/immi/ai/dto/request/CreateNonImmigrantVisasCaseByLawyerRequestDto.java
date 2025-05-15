/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.CaseType;
import com.quick.immi.ai.entity.nonimmigrant.NonImmigrantVisaType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateNonImmigrantVisasCaseByLawyerRequestDto extends CreateCaseByLawyerRequestDto {
  private NonImmigrantVisaType nonImmigrantVisaType;
  private CaseType caseType = CaseType.NonImmigrantVisas;
}
