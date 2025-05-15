/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.TaskStatus;
import lombok.Data;

@Data
public class UpdateDocumentStatusDto {
  private Long documentId;
  private Boolean manualOverride;
  private TaskStatus documentStatus;
}
