/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import com.quick.immi.ai.entity.employmentbased.EmploymentBasedType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class EmploymentBasedCaseSummaryResponseDto extends CaseSummaryResponseDto {
  private EmploymentBasedType subType;
}
