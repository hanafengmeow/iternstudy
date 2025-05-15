/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum UserStatus {
  WAIT_FOR_CONFIRM(0),
  ACTIVE(1),
  IN_ACTIVE(2);

  private final Integer value;

  UserStatus(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }

  public static UserStatus fromValue(int value) {
    for (UserStatus status : UserStatus.values()) {
      if (status.getValue().equals(value)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Invalid UserStatus value: " + value);
  }
}
