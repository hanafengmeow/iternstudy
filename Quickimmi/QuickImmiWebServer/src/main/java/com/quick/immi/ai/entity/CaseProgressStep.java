/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum CaseProgressStep {
  FILLING_APPLICATION("FILLING_APPLICATION"),
  REVIEW_AND_SIGN("REVIEW_AND_SIGN"),
  SUBMIT_APPLICATION("SUBMIT_APPLICATION"),
  FINGERPRINT_INTERVIEW("FINGERPRINT_INTERVIEW"),
  FINAL_RESULT("FINAL_RESULT");

  private String name;

  CaseProgressStep(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
