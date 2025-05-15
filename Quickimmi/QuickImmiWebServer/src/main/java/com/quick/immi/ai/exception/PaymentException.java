/* (C) 2024 */
package com.quick.immi.ai.exception;

public class PaymentException extends BaseException {

  public PaymentException(Integer statusCode, String errorCode, String errorMessage) {
    super(statusCode, errorCode, errorMessage);
  }
}
