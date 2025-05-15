/* (C) 2024 */
package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

import java.util.List;

@Data
public class EmpolymentBasedCaseProfile {
  private Petitioner petitioner;
  private Applicant applicant;
  private Eligibility eligibility;
  private AttorneyStatement attorneyStatement;
  private Preparer preparer;
  private Interpreter interpreter;
}
