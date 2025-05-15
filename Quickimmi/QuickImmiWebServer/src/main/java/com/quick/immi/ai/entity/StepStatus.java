/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum StepStatus {
  NOT_START("NOT_START"),
  IN_PROGRESS("IN_PROGRESS"),
  COMPLETED("COMPLETED");

  private String value;

  StepStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static StepStatus fromValue(String value) {
    for (StepStatus type : StepStatus.values()) {
      if (type.getValue().equals(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid StepStatus value: " + value);
  }
}
