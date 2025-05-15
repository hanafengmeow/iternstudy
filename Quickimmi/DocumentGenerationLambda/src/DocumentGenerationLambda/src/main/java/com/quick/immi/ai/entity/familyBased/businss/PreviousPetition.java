/* (C) 2024 */
package com.quick.immi.ai.entity.familyBased.businss;

import lombok.Data;

import java.util.List;

@Data
public class PreviousPetition {
  // part 5-1
  private boolean filedPetitionYesCheckbox;
  private boolean filedPetitionNoCheckbox;

  // 456
  private String lastName;
  // 457
  private String firstName;
  // 458
  private String middleName;
  // 461
  private String city;
  // 462
  private String state;
  // 460
  private String date;
  // 459
  private String result;

  private List<AdditionalRelative> additionalRelatives;
}
