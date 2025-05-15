package com.quick.immi.ai.entity.familyBased.i864;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailAddress {
  // Sponsor's Mailing Address
  // 199 - In Care Of Name
  private String inCareOfName;

  // 124 - Street Number and Name
  private String streetNumberAndName;

  // 2.c - Apt. Checkbox
  private String aptCheckbox;

  // 2.c - Ste. Checkbox
  private String steCheckbox;

  // 2.c - Flr. Checkbox
  private String flrCheckbox;

  // 125 - Flr. Number
  private String aptSteFlrNumber;

  // 123 - City or Town
  private String city;

  // 128 - State --Drop down
  private String state;

  // 127 - ZIP Code
  private String zipCode;

  // 130 - Province
  private String province;

  // 126 - Postal Code
  private String postalCode;

  // 129 - Country
  private String country;

}
