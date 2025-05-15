/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.CaseProgressStep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseQueryByLawyerRequestDto {
  private Integer lawyerId;
  private String query;
  private CaseProgressStep currentStep;
  private CaseQuerySortBy sortedBy;

  private Integer pageNumber;
  private Integer pageSize;
}
