/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import lombok.Data;

@Data
public class CaseQueryByCustomerRequestDto {
  private Integer userId;
  private CaseQuerySortBy sortedBy;

  private Integer pageNumber;
  private Integer pageSize;
}
