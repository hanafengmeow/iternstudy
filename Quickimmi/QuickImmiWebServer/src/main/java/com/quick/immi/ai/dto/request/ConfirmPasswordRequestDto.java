/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import lombok.Data;

@Data
public class ConfirmPasswordRequestDto {
  private String username;
  private String confirmationCode;
  private String password;
}
