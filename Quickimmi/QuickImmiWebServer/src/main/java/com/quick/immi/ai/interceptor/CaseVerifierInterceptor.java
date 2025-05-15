/* (C) 2024 */
package com.quick.immi.ai.interceptor;

import com.quick.immi.ai.annotation.CaseVerifier;
import com.quick.immi.ai.entity.ApplicationCase;
import com.quick.immi.ai.entity.Customer;
import com.quick.immi.ai.entity.Lawyer;
import com.quick.immi.ai.exception.AuthException;
import com.quick.immi.ai.exception.CaseNotFundException;
import com.quick.immi.ai.service.*;
import com.quick.immi.ai.service.helper.EntityCacheService;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class CaseVerifierInterceptor implements HandlerInterceptor {

  @Autowired private AuthService authService;
  @Autowired private CaseMgtBaseService caseMgtBaseService;
  @Autowired private LawyerMgtService lawyerMgtService;
  @Autowired private CustomerMgtService customerMgtService;
  @Autowired private EntityCacheService entityCacheService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HandlerMethod handlerMethod = getHandlerMethod(handler, response);

    Method method = handlerMethod.getMethod();
    if (method.isAnnotationPresent(CaseVerifier.class)) {
      if (isTestTraffic(request)) return true;

      String caseId = request.getHeader("Case-Id");
      if (caseId == null) {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Case ID is required");
        return false;
      }

      String role = request.getHeader("Role");
      if (role == null) {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Role is required");
        return false;
      }

      String cognitoIdFromCaseId = getCognitoUsernameFromCase(role, caseId, response);
      String cognitoIdFromToken = getCognitoUsername(role, request, response);
      if (cognitoIdFromCaseId == null || cognitoIdFromToken == null) return false;
      if (!cognitoIdFromToken.equals(cognitoIdFromCaseId)) {
        response.sendError(HttpStatus.FORBIDDEN.value(), "You are not allowed to access this case");
        return false;
      }
    }

    return true;
  }

  private boolean isTestTraffic(HttpServletRequest request) {
    if (request.getHeader("quickimmi-test") != null) {
      log.warn("quickimmi-test traffic");
      return true;
    }
    return false;
  }

  private HandlerMethod getHandlerMethod(Object handler, HttpServletResponse response)
      throws Exception {
    try {
      return (HandlerMethod) handler;
    } catch (ClassCastException e) {
      response.sendError(HttpStatus.NOT_FOUND.value(), "API not found");
      return null;
    }
  }

  private String getCognitoUsernameFromCase(
      String role, String caseId, HttpServletResponse response) throws Exception {
    Long caseIdLong = Long.valueOf(caseId);
    String cognitoId = entityCacheService.getAuthCase(role, caseIdLong);
    try {
      if (cognitoId == null) {
        ApplicationCase applicationCase = caseMgtBaseService.get(caseIdLong);
        if (role.equals("LAWYER")) {
          Integer lawyerId = applicationCase.getAssignedLawyer();
          Lawyer lawyer = lawyerMgtService.get(lawyerId);
          if (lawyer == null) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Lawyer not found");
            return null;
          }
          cognitoId = lawyer.getCognitoUsername();
        } else if (role.equals("CUSTOMER")) {
          Integer userId = applicationCase.getUserId();
          Customer customer = customerMgtService.get(userId);
          if (customer == null) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Customer not found");
            return null;
          }
          cognitoId = customer.getCognitoUsername();
        }
        entityCacheService.saveAuthCase(role, caseIdLong, cognitoId);
      }
      return cognitoId;
    } catch (CaseNotFundException e) {
      response.sendError(HttpStatus.NOT_FOUND.value(), "Case not found");
      return null;
    }
  }

  private String getCognitoUsername(
      String role, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String authorizationHeader = request.getHeader("Authorization");

    String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
    try {
      String cognitoId = entityCacheService.getAuth(role, token);
      if (cognitoId != null) {
        return cognitoId;
      }
      String userName = authService.getUserName(token);
      entityCacheService.saveAuth(role, token, userName);
      return userName;
    } catch (AuthException e) {
      response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getErrorCode());
      return null;
    }
  }
}
