/* (C) 2024 */
package com.quick.immi.ai.entity.asylum;

import lombok.Data;

@Data
public class AddressHistory {
  private String part = "Part A.III";
  // can be Q1 or Q2
  private String question;
  private String numberAndStreet;
  private String city;
  private String province;
  private String country;

  private String aptCheckbox;
  private String steCheckbox;
  private String flrCheckbox;

  private String aptSteFlrNumber;

  private String state;
  private String zipCode;
  private String postalCode;

  private String startDate;
  private String endDate;
}
