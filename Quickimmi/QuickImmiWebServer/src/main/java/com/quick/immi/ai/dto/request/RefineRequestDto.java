/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefineRequestDto {
  private String type;
  private String question;
  private String content;
  private String prompt;
}
