/* (C) 2024 */
package com.quick.immi.ai.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormGenerationTaskDto {
  private Long id;
  private Long caseId;
  private String formName;
  private String status;
  private String presignedUrl;
  private Long createdAt;
  private Long updatedAt;
}
