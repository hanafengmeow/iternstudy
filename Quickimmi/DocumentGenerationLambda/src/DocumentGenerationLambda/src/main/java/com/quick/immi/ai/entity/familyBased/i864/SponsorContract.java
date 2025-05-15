package com.quick.immi.ai.entity.familyBased.i864;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SponsorContract {

  // Sponsor's Statement
  // 283 1.a - I can read and understand English (Checkbox)
  private String canReadUnderstandEnglishCheckbox;

  // 290 1.b - The interpreter named in Part 9 read every question and my answer (Checkbox)
  private String interpreterHelpedCheckbox;

  // 284 - Language in which I am fluent
  private String fluentLanguage;

  // Preparer information
  // 285 2 - At my request, the preparer named in Part 10 prepared this affidavit (Checkbox)
  private String preparerCheckbox;

  // 286 - Information provided by sponsor for affidavit
  private String preparerInformationProvided;

  // Sponsor's Contact Information
  // 287 - Sponsor's Daytime Telephone Number
  // 288 - Sponsor's Mobile Telephone Number (if any)
  // 289 - Sponsor's Email Address (if any)
  private ContactInformation contactInformation;

  // Sponsor's Signature
  // 306 - Sponsor's Signature
  private String sponsorSignature;

  // 305 - Date of Signature (mm/dd/yyyy)
  private String dateOfSignature;


}
