/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import lombok.Data;

@Data
public class ResponseDto<T> {
  private T data;
  private String message;
  private String errorCode;

  public ResponseDto() {}

  public static <T> ResponseDto<T> newInstance(String message, String errorCode, T data) {
    ResponseDto<T> result = new ResponseDto<T>();
    result.data = data;
    result.message = message;
    result.errorCode = errorCode;
    return result;
  }

  public static <T> ResponseDto<T> newInstance(String errorCode, String message) {
    ResponseDto<T> result = new ResponseDto<T>();
    result.message = message;
    result.errorCode = errorCode;
    return result;
  }

  public static <T> ResponseDto<T> newInstance(T data) {
    return newInstance("SUCCESS", null, data);
  }
}
