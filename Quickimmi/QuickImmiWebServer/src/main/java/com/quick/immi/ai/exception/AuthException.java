/* (C) 2024 */
package com.quick.immi.ai.exception;

public class AuthException extends BaseException {

  public AuthException(Integer statusCode, String errorCode, String errorMessage) {
    super(statusCode, errorCode, errorMessage);
  }

  public AuthException(String errorCode, String errorMessage) {
    super(errorCode, errorMessage);
  }
}
