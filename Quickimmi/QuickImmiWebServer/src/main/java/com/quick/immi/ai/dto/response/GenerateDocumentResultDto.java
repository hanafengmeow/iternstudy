/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import com.quick.immi.ai.entity.DocumentType;
import lombok.Data;

@Data
public class GenerateDocumentResultDto {
  private Long taskId;
  private DocumentType documentType;

  public GenerateDocumentResultDto(Long taskId, DocumentType documentType) {
    this.taskId = taskId;
    this.documentType = documentType;
  }
}
