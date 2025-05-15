package com.quick.immi.ai.entity.familyBased.i864;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SponsorDetails {
  // Sponsor's Full Name
  // 187 - Family Name (Last Name)
  private String lastName;

  // 186 - Given Name (First Name)
  private String firstName;

  // 185 - Middle Name
  private String middleName;

  // 189 - Street Number and Name
  private MailAddress mailAddress;

  // 3 - Is your current mailing address the same as your physical address? (Checkbox)
  private String mailingAddressSameAsPhysicalYesCheckbox;
  private String mailingAddressSameAsPhysicalNoCheckbox;

  // Sponsor's Physical Address (if different)
  // 203 - 208
  private MailAddress physicalAddress;

  // Other Information
  // 214 - Country of Domicile
  private String countryOfDomicile;

  // 213 - Date of Birth (mm/dd/yyyy)
  private String dateOfBirth;

  // 215 - City or Town of Birth
  private String cityOfBirth;

  // 216 - State or Province of Birth
  private String stateOrProvinceOfBirth;

  // 217 - Country of Birth
  private String countryOfBirth;

  // 225 - U.S. Social Security Number (Required)
  private String ssn;

  // Citizenship or Residency
  // 11.a - I am a U.S. citizen (Checkbox)
  private String isUsCitizenCheckbox;

  // 11.b - I am a U.S. national (Checkbox)
  private String isUsNationalCheckbox;

  // 11.c - I am a lawful permanent resident (Checkbox)
  private String isLawfulPermanentResidentCheckbox;

  // 12 - Sponsor's A-Number (if any)
  private String sponsorANumber;

  // 13 - Sponsor's USCIS Online Account Number (if any)
  private String uSCISOnlineAccountNumber;

  // 14 - Military Service (To be completed by petitioner sponsors only)
  private String militaryServiceActiveDutyYesCheckbox;
  private String militaryServiceActiveDutyNoCheckbox;

}
