/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefinePSRequestDto {
  private Language OriginalLanguage;
  private String EnglishPS;
  private String OriginalLanguagePS;
  private String prompt;
}
