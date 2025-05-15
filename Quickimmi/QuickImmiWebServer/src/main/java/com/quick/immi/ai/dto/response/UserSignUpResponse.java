/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quick.immi.ai.entity.UserStatus;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Builder
public class UserSignUpResponse {
  private String username;
  private Integer status;
  private String errorCode;
  private String errorMessage;
  private UserStatus userStatus;
  private String userCreatedDate;
}
