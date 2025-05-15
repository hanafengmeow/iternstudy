/* (C) 2024 */
package com.quick.immi.ai.entity.familybased;

import lombok.Data;

@Data
public class PetitionerStatement {
  // Part 6-1a
  private boolean canReadAndUnderstandEnglishCheckbox;
  // Part 6-1b 478
  private boolean interpreterReadAndTranslatedCheckbox;
  private String interpreterReadAndTranslatedNumber;
  // 480
  private boolean preparerAssistanceCheckbox;
  private String preparerAssistanceNumber;
  // 469
  private String daytimeTelephoneNumber;
  // 471
  private String mobileTelephoneNumber;
  // 470
  private String petitionerEmailAddress;
}
