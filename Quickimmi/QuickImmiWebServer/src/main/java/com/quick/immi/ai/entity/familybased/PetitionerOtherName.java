/* (C) 2024 */
package com.quick.immi.ai.entity.familybased;

import lombok.Data;

@Data
public class PetitionerOtherName {
  private String pageNumber = "2";
  private String partNumber = "2";
  private String itemNumber = "5";

  // Family name for petitioner's other name used
  private String lastName;
  // Given name for petitioner's other name used
  private String firstName;
  // Middle name for petitioner's other name used
  private String middleName;
}
