/* (C) 2024 */
package com.quick.immi.ai.entity.familyBased.businss;

import lombok.Data;

@Data
public class Address {
  private String inCareOf;
  private String streetNumberAndName;
  private boolean aptCheckbox;
  private boolean steCheckbox;
  private boolean flrCheckbox;
  private String aptSteFlrNumber;
  private  boolean inUSA;
  private String cityOrTown;
  private String state;
  private String zipCode;
  private String province;
  private String postalCode;
  private String country;
  private String dateFrom;
  private String dateTo;
}
