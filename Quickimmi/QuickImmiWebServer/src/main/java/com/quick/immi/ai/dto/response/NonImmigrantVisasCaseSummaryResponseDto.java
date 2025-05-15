/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import com.quick.immi.ai.entity.nonimmigrant.NonImmigrantVisaType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class NonImmigrantVisasCaseSummaryResponseDto extends CaseSummaryResponseDto {
  private NonImmigrantVisaType subType;
}
