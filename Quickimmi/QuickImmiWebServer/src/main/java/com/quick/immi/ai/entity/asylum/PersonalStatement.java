/* (C) 2024 */
package com.quick.immi.ai.entity.asylum;

import com.quick.immi.ai.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalStatement {
  private Language language;
  private String content;
}
