/* (C) 2024 */
package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

@Data
public class Preparer {
  // 502
  private String preparerLastName;
  // 500
  private String preparerFirstName;
  // 501
  private String preparerBusinessName;
  // 507
  private String preparerStreetNumberAndName;
  // Part 8-3b 511
  private String preparerAptCheckbox;
  private String preparerSteCheckbox;
  private String preparerFlrCheckbox;
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
  private String nonAttorneyPreparerCheckbox;
  private String attorneyPreparerCheckbox;
  // Part 8-7b
  private String attorneyExtendsCheckbox;
  private String attorneyNotExtendCheckbox;
  // 524
  private String preparerSignature;
  // 525
  private String dateOfSignature;
}
