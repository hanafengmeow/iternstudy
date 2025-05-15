/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum TaskStatus {
  UPLOADING("uploading"),
  UPLOADED("uploaded"),

  CREATED("Created"),
  IN_PROGRESS("In Progress"),
  SUCCESS("Success"),
  SKIPPED("Skipped"),
  FAILED("Failed");

  private String value;

  TaskStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static TaskStatus fromValue(String value) {
    for (TaskStatus type : TaskStatus.values()) {
      if (type.getValue().equals(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid TaskStatus value: " + value);
  }
}
