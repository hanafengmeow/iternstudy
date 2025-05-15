/* (C) 2024 */
package com.quick.immi.ai.entity.asylum;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalStatementsInMultipleLanguages {
  private List<PersonalStatement> PersonalStatements;
}
