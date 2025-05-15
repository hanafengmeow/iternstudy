/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.DocumentType;
import com.quick.immi.ai.entity.Identify;
import com.quick.immi.ai.entity.Operation;
import lombok.Data;

@Data
public class GeneratePresignedUrlRequestDto {
  private Integer userId;
  private Long caseId;
  private Identify identify;
  private DocumentType type;
  private String documentName;
  private Operation operation;
  private String description;
}
