package com.quick.immi.ai.entity.familyBased.i864;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrincipalImmigrant {
  // 140 - Family Name (Last Name)
  private String lastName;

  // 138 - Given Name (First Name)
  private String firstName;

  // 139 - Middle Name
  private String middleName;

  // 134 - In Care Of Name

  // 124 - 129
  private MailAddress mailAddress;

  //Other Information
  // 144 - Country of Citizenship or Nationality
  private String countryOfCitizenship;

  // 143 - Date of Birth (mm/dd/yyyy)
  private String dateOfBirth;

  // 141 - Alien Registration Number (A-Number)
  private String alienNumber;

  // 142 - USCIS Online Account Number (if any)
  private String uSCISOnlineAccountNumber;

  // 145 - Daytime Telephone Number
  private String daytimeTelephoneNumber;

}
