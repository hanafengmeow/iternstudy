/* (C) 2024 */
package com.quick.immi.ai.exception;

public abstract class BaseException extends RuntimeException {
  private String errorCode;
  private Integer statusCode;
  private String errorMessage;

  public BaseException(Integer statusCode, String errorCode, String errorMessage) {
    super();
    this.statusCode = statusCode;
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public BaseException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public Integer getStatusCode() {
    return statusCode;
  }
}
