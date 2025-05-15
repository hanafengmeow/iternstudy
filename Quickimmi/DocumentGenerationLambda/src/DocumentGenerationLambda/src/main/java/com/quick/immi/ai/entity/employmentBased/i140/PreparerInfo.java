package com.quick.immi.ai.entity.employmentBased.i140;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreparerInfo {

  private String lastName;                     // 330: Preparer's Family Name (Last Name)
  private String firstName;                    // 332: Preparer's Given Name (First Name)
  private String businessOrOrganizationName;   // 331: Preparer's Business or Organization Name

  private String daytimeTelephoneNumber;       // 333: Preparer's Daytime Telephone Number
  private String mobileTelephoneNumber;        // 335: Preparer's Mobile Telephone Number (if any)
  private String emailAddress;                 // 334: Preparer's Email Address (if any)

  private String signature;                    // 337: Preparer's Signature
  private String dateOfSignature;              // 336: Date of Signature (mm/dd/yyyy)

}
