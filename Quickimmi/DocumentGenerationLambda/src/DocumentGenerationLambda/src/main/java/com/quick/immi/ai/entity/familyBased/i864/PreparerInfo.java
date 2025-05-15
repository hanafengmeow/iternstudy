package com.quick.immi.ai.entity.familyBased.i864;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreparerInfo {
  // Preparer's Full Name
  // 314 - Preparer's Family Name (Last Name)
  private String lastName;

  // 315 - Preparer's Given Name (First Name)
  private String firstName;

  // 313 - Preparer's Business or Organization Name (if any)
  private String businessOrOrganizationName;

  // Preparer's Mailing Address
  // 330 - 328
  private MailAddress mailAddress;

  // Preparer's Contact Information
  // 317 - Preparer's Daytime Telephone Number
  // 318 - Preparer's Mobile Telephone Number (if any)
  // 316 - Preparer's Email Address (if any)
  private ContactInformation contactInformation;

  // Preparer's Statement
  // 330 7.a - I am not an attorney or accredited representative (Checkbox)
  private String notAttorneyCheckbox;

  // 331 7.b - I am an attorney or accredited representative (Checkbox)
  private String isAttorneyCheckbox;

  // 332 7.b - Representation extends beyond this affidavit (Checkbox)
  private String extendsBeyondCheckbox;

  // 333 7.b - Representation does not extend beyond this affidavit (Checkbox)
  private String doesNotExtendBeyondCheckbox;

  // Preparer's Signature
  // 334 - Preparer's Signature
  private String preparerSignature;

  // 335 - Date of Signature (mm/dd/yyyy)
  private String dateOfSignature;
}
