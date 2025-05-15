/* (C) 2024 */
package com.quick.immi.ai.exception;

public class UserNotFundException extends RuntimeException {
  public UserNotFundException() {
    super(Constant.USE_NOT_FOUND_ERROR);
  }
}
