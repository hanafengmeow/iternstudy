package com.quick.immi.ai.entity.familyBased.i864;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterpreterInfo {
  // Interpreter's Full Name
  // 309 - Interpreter's Family Name (Last Name)
  private String lastName;

  // 308 - Interpreter's Given Name (First Name)
  private String firstName;

  // 312 - Interpreter's Business or Organization Name (if any)
  private String businessOrOrganizationName;

  // Interpreter's Mailing Address
  // 292 - 300
  private MailAddress mailAddress;

  // Interpreter's Contact Information
  // 303 304 302
  private ContactInformation contactInformation;

  // Interpreter's Certification
  // 307 - Language in which the interpreter is fluent
  private String fluentLanguage;

  // Interpreter's Signature
  // 3 - Interpreter's Signature
  private String interpreterSignature;

  // 310 - Date of Signature (mm/dd/yyyy)
  private String dateOfSignature;
}
