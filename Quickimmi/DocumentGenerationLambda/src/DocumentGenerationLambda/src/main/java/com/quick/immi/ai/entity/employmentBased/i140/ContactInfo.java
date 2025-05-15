package com.quick.immi.ai.entity.employmentBased.i140;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfo {

  private String lastName;                  // 1.a. Petitioner's or Authorized Signatory's Family Name - field number 313
  private String firstName;                 // 1.b. Petitioner's or Authorized Signatory's Given Name - field number 315
  private String title;                     // 2. Petitioner's or Authorized Signatory's Title - field number 314
  private String daytimeTelephoneNumber;    // 3. Petitioner's or Authorized Signatory's Daytime Telephone Number - field number 318
  private String mobileTelephoneNumber;     // 4. Petitioner's or Authorized Signatory's Mobile Telephone Number (if any) - field number 317
  private String emailAddress;              // 5. Petitioner's or Authorized Signatory's Email Address (if any) - field number 316
  private String signature;                 // 6.a. Petitioner's or Authorized Signatory's Signature - field number 323
  private String dateOfSignature;           // 6.b. Date of Signature (mm/dd/yyyy) - field number 319
}
