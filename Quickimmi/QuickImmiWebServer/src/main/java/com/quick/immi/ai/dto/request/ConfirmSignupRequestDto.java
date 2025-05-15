/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import lombok.Data;

@Data
public class ConfirmSignupRequestDto {
  private String username;
  private String verificationCode;
}
