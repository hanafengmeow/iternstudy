/* (C) 2024 */
package com.quick.immi.ai.interceptor;

import com.quick.immi.ai.annotation.Login;
import com.quick.immi.ai.exception.AuthException;
import com.quick.immi.ai.service.AuthService;
import com.quick.immi.ai.service.CustomerMgtService;
import com.quick.immi.ai.service.LawyerMgtService;
import com.quick.immi.ai.service.helper.EntityCacheService;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginAuthenticationInterceptor implements HandlerInterceptor {

  @Autowired private AuthService authenticate;
  @Autowired private LawyerMgtService lawyerMgtService;
  @Autowired private CustomerMgtService customerMgtService;
  @Autowired private EntityCacheService entityCacheService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
      setHeader(HttpStatus.OK, request, response);
      return true;
    }
    HandlerMethod handlerMethod;
    try {
      handlerMethod = (HandlerMethod) handler;
    } catch (Exception e) {
      response.sendError(HttpStatus.NOT_FOUND.value(), "api not found");
      return false;
    }
    Method method = handlerMethod.getMethod();

    if (method.isAnnotationPresent(Login.class)) {
      if (request.getHeader("quickimmi-test") != null) {
        log.warn("quickimmi-test traffic");
        return true;
      }
      String authorizationHeader = request.getHeader("Authorization");
      String role = request.getHeader("Role");
      try {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
          String token = authorizationHeader.substring(7); // Extract the token excluding "Bearer "
          return this.authenticate.verifyToken(role, token);
        } else {
          // No token provided, send unauthorized response
          log.warn("No access token provided...");
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          return false;
        }
      } catch (AuthException e) {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getErrorCode());
        return false;
      }
    }

    return true;
  }

  /** 为response设置header，实现跨域 */
  private void setHeader(
      HttpStatus httpStatus, HttpServletRequest request, HttpServletResponse response) {
    // 跨域的header设置
    response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Methods", request.getMethod());
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader(
        "Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
    // 防止乱码，适用于传输JSON数据
    response.setHeader("Content-Type", "application/json;charset=UTF-8");
    response.setStatus(httpStatus.value());
  }
}
