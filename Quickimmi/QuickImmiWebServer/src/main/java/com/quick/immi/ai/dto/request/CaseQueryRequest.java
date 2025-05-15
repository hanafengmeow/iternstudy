/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseQueryRequest {
  private Integer lawyerId;
  private String query;
  private String currentStep;
  private String sortedBy;
  private Integer offset;
  private Integer pageSize;
}
