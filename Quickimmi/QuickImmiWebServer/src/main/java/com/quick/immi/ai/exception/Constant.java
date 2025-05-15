/* (C) 2024 */
package com.quick.immi.ai.exception;

public interface Constant {
  String USE_NOT_FOUND_ERROR = "USE_NOT_FOUND";
  String INVALID_VERIFICATION_CODE = "INVALID_VERIFICATION_CODE";

  String USER_EXIST_ERROR = "USER_EXIST";
  String WAITING_FOR_VERIFICATION_CODE = "WAITING_FOR_VERIFICATION_CODE";
  String CLIENT_SIDE_ERROR = "CLIENT_SIDE_ERROR";
  String SERVER_SIDE_ERROR = "SERVER_INNER_ERROR";
  String NOT_AUTHORIZED_ERROR = "USER_PASSWORD_OR_USERNAME_IS_WRONG";
  String MISS_REQUIRED_FIELD = "MISS_REQUIRED_FIELD";
  String ACCESS_TOKEN_EXPIRED_ERROR = "ACCESS_TOKEN_EXPIRED";
  String INVALID_ACCESS_TOKEN_ERROR = "INVALID_ACCESS_TOKEN";

  String APPLICATION_CASE_NOT_FOUND_ERROR = "APPLICATION_CASE_NOT_FOUND";

  enum StatusCode {
    USER_ERROR(400),
    SERVER_SIDE_ERROR(500);

    private final int value;

    StatusCode(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public static StatusCode fromValue(int value) {
      for (StatusCode status : StatusCode.values()) {
        if (status.getValue() == value) {
          return status;
        }
      }
      throw new IllegalArgumentException("Invalid Role value: " + value);
    }
  }
}
