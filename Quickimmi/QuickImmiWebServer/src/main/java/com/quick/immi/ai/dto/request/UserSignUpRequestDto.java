/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import lombok.Data;
import lombok.ToString;
import software.amazon.awssdk.annotations.NotNull;

@Data
@ToString
public class UserSignUpRequestDto {
  @NotNull private String email;
  @NotNull private String password;
  private String phoneNumber;
}
