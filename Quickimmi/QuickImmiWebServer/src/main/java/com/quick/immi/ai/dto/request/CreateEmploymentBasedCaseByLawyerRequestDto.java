/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.CaseType;
import com.quick.immi.ai.entity.employmentbased.EmploymentBasedType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateEmploymentBasedCaseByLawyerRequestDto extends CreateCaseByLawyerRequestDto {
  private EmploymentBasedType employmentBasedType;
  private CaseType caseType = CaseType.EmploymentBased;
}
