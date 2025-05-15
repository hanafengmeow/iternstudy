package com.quick.immi.ai.entity.employmentBased.i140;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessingInfo {

  // Part 4: Processing Information
  private String applyForVisaAbroadCheckbox;     // 1.a. Checkbox for applying visa abroad ("Y" for Yes)
  private String cityOrTown;                     // 1.b. City or Town - field number 173
  private String country;                        // 1.c. Country - field number 174
  private String applyForAdjustmentOfStatusCheckbox; // 2.a. Checkbox for adjustment of status ("Y" for Yes)

  // 2.b. Alien's current country of residence
  private String currentCountryOfResidence;      // 2.b. - field number 180

  // 3.a. to 3.f. Address if provided
  private MailAddress foreignAddress;                   // Using MailAddress class for 3.a. to 3.f. fields

  // 4.a. to 4.c. Name in native alphabet
  private String lastNameInNativeAlphabet;       // 4.a. - field number 192
  private String firstNameInNativeAlphabet;      // 4.b. - field number 191
  private String middleNameInNativeAlphabet;     // 4.c. - field number 190

  // Mailing address if provided
  private MailAddress mailAddress;            // Using MailAddress class for mailing address (5.a. to 5.g.)

  // 6.a. Are you filing any other petitions or applications
  private String filingOtherPetitionsCheckboxYes;   // 6.a. Checkbox ("Y" for Yes, "N" for No)
  private String filingOtherPetitionsCheckboxNo;   // 6.a. Checkbox ("Y" for Yes, "N" for No)

  // 6.b. Select all applicable boxes
  private String formI485Checkbox;               // Form I-485 Checkbox - field number 6.b.
  private String formI131Checkbox;               // Form I-131 Checkbox - field number 6.b.
  private String formI765Checkbox;               // Form I-765 Checkbox - field number 6.b.
  private String otherApplicationsCheckbox;      // Other Checkbox - field number 6.b.

  // 7. Removal proceedings
  private String removalProceedingsCheckboxYes;     // 7. Checkbox ("Y" for Yes, "N" for No)
  private String removalProceedingsCheckboxNo;     // 7. Checkbox ("Y" for Yes, "N" for No)

  // 8. Previous immigrant visa petition
  private String previousImmigrantVisaPetitionCheckboxYes; // 8. Checkbox ("Y" for Yes, "N" for No)
  private String previousImmigrantVisaPetitionCheckboxNo; // 8. Checkbox ("Y" for Yes, "N" for No)

  // 9. Filing without original labor certification
  private String filingWithoutLaborCertificationCheckboxYes; // 9. Checkbox ("Y" for Yes, "N" for No)
  private String filingWithoutLaborCertificationCheckboxNo; // 9. Checkbox ("Y" for Yes, "N" for No)

  // 10. Request for duplicate labor certification
  private String requestForDuplicateLaborCertificationCheckboxYes; // 10. Checkbox ("Y" for Yes, "N" for No)
  private String requestForDuplicateLaborCertificationCheckboxNo; // 10. Checkbox ("Y" for Yes, "N" for No)
}
