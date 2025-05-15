/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import com.quick.immi.ai.entity.asylum.AsylumType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AsylumCaseSummaryResponseDto extends CaseSummaryResponseDto {
  private AsylumType asylumType;
  private String maritalStatus;
  private Boolean applyWithSpouse;
  private Integer numberOfChildren;
  private Integer numberOfApplyingChildren;
}
