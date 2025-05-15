/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TriggerDataCrawlingResultDto {
  private Long taskId;

  public TriggerDataCrawlingResultDto(Long taskId) {
    this.taskId = taskId;
  }
}
