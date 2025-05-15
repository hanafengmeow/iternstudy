/* (C) 2024 */
package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

@Data
public class Interpreter {
  // 484
  private String lastName;
  // 483
  private String firstName;
  // 485
  private String businessName;
  // 487
  private String streetNumberAndName;
  // Part 7-3b 491
  private boolean aptCheckbox;
  private boolean steCheckbox;
  private boolean flrCheckbox;
  private String aptSteFlrNumber;
  // 486
  private String city;
  // 494
  private String state;
  // 493
  private String zipCode;
  // 496
  private String province;
  // 492
  private String postalCode;
  // 495
  private String country;
  // 503
  private String daytimeTelephoneNumber;
  // 505
  private String mobileTelephoneNumber;
  // 504
  private String emailAddress;
  // 497
  private String fluentLanguage;
}
