/* (C) 2024 */
package com.quick.immi.ai.exception;

public class CaseNotFundException extends RuntimeException {

  public CaseNotFundException() {
    super(Constant.APPLICATION_CASE_NOT_FOUND_ERROR);
  }
}
