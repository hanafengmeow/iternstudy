/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.CaseType;
import com.quick.immi.ai.entity.familybased.FamilyBasedType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateFamilyBasedCaseByLawyerRequestDto extends CreateCaseByLawyerRequestDto {
  private FamilyBasedType familyBasedType;
  private CaseType caseType = CaseType.FamilyBased;
}
