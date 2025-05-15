package com.quick.immi.ai.entity;

public enum Operation {
  REPLACE("Replace"),
  NEW("New");
  String value;

  Operation(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Operation fromValue(int value) {
    for (Operation status : Operation.values()) {
      if (status.getValue().equals(value)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Invalid Operation value: " + value);
  }
}
