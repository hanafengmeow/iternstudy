/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum CaseStatus {
  IN_PROGRESS("In Progress"),
  UNDER_REVIEW("Under Review by Lawyer"),
  SUBMITTED_FOR_SIGNATURE("Submitted for Signature"),
  RECEIPT_ISSUED("Receipt Issued"),
  INTERVIEW_AND_FINGERPRINT("Interview and Fingerprint Processing"),
  BACKGROUND_CHECK("Background Check"),
  FINAL_RESULT("Final Result");

  private String value;

  CaseStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static CaseStatus fromValue(String value) {
    for (CaseStatus type : CaseStatus.values()) {
      if (type.getValue().equals(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid CaseStatus value: " + value);
  }
}
