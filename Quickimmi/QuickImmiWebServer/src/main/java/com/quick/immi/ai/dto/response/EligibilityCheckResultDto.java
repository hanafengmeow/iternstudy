/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EligibilityCheckResultDto {
  private Boolean status;
  private List<String> missingFields;

  public EligibilityCheckResultDto(Boolean status, List<String> missingFields) {
    this.status = status;
    this.missingFields = missingFields;
  }
}
