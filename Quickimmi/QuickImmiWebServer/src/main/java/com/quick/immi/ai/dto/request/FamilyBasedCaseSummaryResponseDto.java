/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.dto.response.CaseSummaryResponseDto;
import com.quick.immi.ai.entity.familybased.FamilyBasedType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class FamilyBasedCaseSummaryResponseDto extends CaseSummaryResponseDto {
  private FamilyBasedType subType;
  // Spouse, Parent, Brother, Child
  private String petitionFor;
  // U.S citizen, Lawful permanent resident
  private String petitionIdentity;
  private String beneficiaryName;
  private boolean isBeneficiaryInUSA;
}
