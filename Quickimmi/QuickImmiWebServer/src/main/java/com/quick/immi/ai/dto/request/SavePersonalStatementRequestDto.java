/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.Language;
import lombok.Data;

@Data
public class SavePersonalStatementRequestDto {
  private Long caseId;
  private String content;
  private Language language;
}
