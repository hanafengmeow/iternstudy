package com.quick.immi.ai.entity.employmentBased.i140;

import lombok.Data;

@Data
public class InterpreterInfo {

  // 1.a. Interpreter's Family Name (Last Name) - field number 320
  private String lastName;

  // 1.b. Interpreter's Given Name (First Name) - field number 322
  private String firstName;

  // 2. Interpreter's Business or Organization Name - field number 321
  private String businessOrOrganizationName;

  // 3. Interpreter's Daytime Telephone Number - field number 324
  private String daytimeTelephoneNumber;

  // 4. Interpreter's Mobile Telephone Number (if any) - field number 326
  private String mobileTelephoneNumber;

  // 5. Interpreter's Email Address (if any) - field number 325
  private String emailAddress;

  // 6. Interpreter fluent in (Language) - field number 327
  private String interpreterFluentLanguage;

  // 6.a. Interpreter's Signature - field number 328
  private String signature;

  // 6.b. Date of Signature (mm/dd/yyyy) - field number 329
  private String dateOfSignature;

}
