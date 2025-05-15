/* (C) 2024 */
package com.quick.immi.ai.entity.familyBased.businss;

import lombok.Data;

@Data
public class PreparerInformation {
  // 502
  private String preparerFamilyName;
  // 500
  private String preparerGivenName;
  // 501
  private String preparerBusinessName;
  // 507
  private String preparerStreetNumberAndName;
  // Part 8-3b 511
  private boolean preparerAptCheckbox;
  private boolean preparerSteCheckbox;
  private boolean preparerFlrCheckbox;
  private String preparerAptSteFlrNumber;
  // 506
  private String preparerCity;
  // 514
  private String preparerState;
  // 513
  private String preparerZipCode;
  // 516
  private String preparerProvince;
  // 512
  private String preparerPostalCode;
  // 515
  private String preparerCountry;
  // 518
  private String preparerDaytimeTelephoneNumber;
  // 517
  private String preparerMobileTelephoneNumber;
  // 519
  private String preparerEmailAddress;
  // Part 8-7a
  private boolean nonAttorneyPreparerCheckbox;
  private boolean attorneyPreparerCheckbox;
  // Part 8-7b
  private boolean attorneyExtendsCheckbox;
  private boolean attorneyNotExtendCheckbox;
}
