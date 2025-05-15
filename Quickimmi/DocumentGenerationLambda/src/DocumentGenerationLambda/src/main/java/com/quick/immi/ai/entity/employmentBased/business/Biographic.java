/* (C) 2024 */
package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

@Data
public class Biographic {
  // Part3-1
  private boolean ethnicityHispanicCheckbox;
  private boolean ethnicityNotHispanicCheckbox;

  // Part3-2
  private boolean raceWhiteCheckbox;
  private boolean raceAsianCheckbox;
  private boolean raceBlackCheckbox;
  private boolean raceAmericanIndianCheckbox;
  private boolean raceNativeHawaiianCheckbox;

  // 268
  private String heightFeet;
  // 269
  private String heightInches;
  // 27
  private String weightPounds1;
  private String weightPounds2;
  private String weightPounds3;
  // Part3-5
  private boolean eyeColorBlackCheckbox;
  private boolean eyeColorBlueCheckbox;
  private boolean eyeColorBrownCheckbox;
  private boolean eyeColorGrayCheckbox;
  private boolean eyeColorGreenCheckbox;
  private boolean eyeColorHazelCheckbox;
  private boolean eyeColorMaroonCheckbox;
  private boolean eyeColorPinkCheckbox;
  private boolean eyeColorUnknownCheckbox;
  // Part3-6
  private boolean hairColorBaldCheckbox;
  private boolean hairColorBlackCheckbox;
  private boolean hairColorBlondCheckbox;
  private boolean hairColorBrownCheckbox;
  private boolean hairColorGrayCheckbox;
  private boolean hairColorRedCheckbox;
  private boolean hairColorSandyCheckbox;
  private boolean hairColorWhiteCheckbox;
  private boolean hairColorUnknownCheckbox;
}
