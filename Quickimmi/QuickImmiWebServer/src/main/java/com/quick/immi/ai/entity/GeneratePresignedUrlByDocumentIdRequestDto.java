/* (C) 2024 */
package com.quick.immi.ai.entity;

import lombok.Data;

@Data
public class GeneratePresignedUrlByDocumentIdRequestDto {
  private Long documentId;
  private Identify identify;
  private DocumentType type;
  private String documentName;
}
