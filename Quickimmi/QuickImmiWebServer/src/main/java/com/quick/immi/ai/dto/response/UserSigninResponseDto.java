/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import com.quick.immi.ai.entity.Customer;
import lombok.Data;

@Data
public class UserSigninResponseDto {
  private String session;
  private String accessToken;
  private String challengeResult;
  private String idToken;
  private String refreshToken;
  private Customer user;

  public UserSigninResponseDto(
      String session,
      String accessToken,
      String challengeResult,
      String idToken,
      String refreshToken,
      Customer user) {
    this.session = session;
    this.accessToken = accessToken;
    this.challengeResult = challengeResult;
    this.idToken = idToken;
    this.refreshToken = refreshToken;
    this.user = user;
  }
}
