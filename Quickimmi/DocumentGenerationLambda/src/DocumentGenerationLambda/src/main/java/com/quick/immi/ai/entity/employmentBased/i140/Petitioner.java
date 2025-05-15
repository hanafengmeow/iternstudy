package com.quick.immi.ai.entity.employmentBased.i140;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Petitioner {
// PART 1: Information About the Person or Organization Filing This Petition
  // 108: Family Name (Last Name)
  private String lastName;

  // 109: Given Name (First Name)
  private String firstName;

  // 110: Middle Name
  private String middleName;

  // 111: Company or Organization Name
  private String companyName;

  // Using MailingAddress class for the address fields
  private MailAddress mailAddress;

  // 116: IRS Employer Identification Number (EIN)
  private String employerIdentificationNumber;

  // 136: Are you a nonprofit organized as tax exempt?
  private String nonprofitCheckboxYes;

  // 135: Are you a nonprofit organized as tax exempt? (No)
  private String nonprofitCheckboxNo;

  // 138: Do you currently employ 25 or fewer full-time equivalent employees? (Yes)
  private String employFewerThan25CheckboxYes;

  // 137: Do you currently employ 25 or fewer full-time equivalent employees? (No)
  private String employFewerThan25CheckboxNo;

  // 117: U.S. Social Security Number (SSN) (if any)
  private String ssn;

  // 115: USCIS Online Account Number (if any)
  private String uSCISOnlineAccountNumber;



}
