/* (C) 2024 */
package com.quick.immi.ai.service;

import static com.quick.immi.ai.exception.Constant.ACCESS_TOKEN_EXPIRED_ERROR;
import static com.quick.immi.ai.exception.Constant.INVALID_ACCESS_TOKEN_ERROR;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.quick.immi.ai.exception.AuthException;
import com.quick.immi.ai.service.helper.EntityCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

  @Autowired private AWSCognitoIdentityProvider cognitoClient;
  @Autowired private EntityCacheService entityCacheService;

  public boolean verifyToken(String role, String token) {
    String userName = getUserName(token);
    entityCacheService.saveAuth(role, token, userName);
    // Token is valid
    return true;
  }

  public String getUserName(String token) {
    try {
      GetUserRequest getUserRequest = new GetUserRequest();
      getUserRequest.setAccessToken(token);
      GetUserResult result = cognitoClient.getUser(getUserRequest);
      return result.getUsername();
    } catch (Exception e) {
      log.warn("fail to getUserName due to " + e.getMessage());
      if (e.getMessage().contains("expired")) {
        throw new AuthException(ACCESS_TOKEN_EXPIRED_ERROR, e.getMessage());
      }
      throw new AuthException(INVALID_ACCESS_TOKEN_ERROR, e.getMessage());
    }
  }
}
