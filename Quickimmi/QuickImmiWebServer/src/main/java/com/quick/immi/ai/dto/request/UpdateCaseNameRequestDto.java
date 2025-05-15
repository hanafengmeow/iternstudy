/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import lombok.Data;

@Data
public class UpdateCaseNameRequestDto {
  private Long caseId;
  private String caseName;
}
