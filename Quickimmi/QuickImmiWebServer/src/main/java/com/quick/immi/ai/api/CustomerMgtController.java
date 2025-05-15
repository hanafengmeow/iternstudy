/* (C) 2024 */
package com.quick.immi.ai.api;

import static com.quick.immi.ai.exception.Constant.*;

import com.quick.immi.ai.annotation.Login;
import com.quick.immi.ai.dto.response.ResponseDto;
import com.quick.immi.ai.entity.Customer;
import com.quick.immi.ai.entity.Role;
import com.quick.immi.ai.service.CustomerMgtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class CustomerMgtController {

  @Autowired private CustomerMgtService customerMgtService;

  @PostMapping("/create")
  @Login
  public ResponseEntity<ResponseDto<Integer>> create(
      @RequestHeader("role") Role Role, @RequestBody Customer customer) {
    log.info(String.format("user create api receive request %s", customer));
    try {
      Customer byCName = customerMgtService.findByCName(customer.getCognitoUsername());
      if (byCName != null) {
        log.warn(String.format("fail to create due to user %s exists", customer));
        return ResponseEntity.status(400)
            .body(ResponseDto.newInstance(USER_EXIST_ERROR, String.format("User already exists")));
      }
      customer.setCreatedAt(System.currentTimeMillis());
      customerMgtService.create(customer);
      return ResponseEntity.ok().body(ResponseDto.newInstance(customer.getId()));
    } catch (Exception exp) {
      log.error(String.format("fail to create user %s", customer), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to create user due to %s", exp.getMessage())));
    }
  }

  @PostMapping("/update")
  @Login
  public ResponseEntity<ResponseDto<Boolean>> update(
      @RequestHeader("role") Role Role, @RequestBody Customer customer) {
    log.info(String.format("user update api receive request %s", customer));
    try {
      customer.setUpdatedAt(System.currentTimeMillis());
      customerMgtService.update(customer);
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (Exception exp) {
      log.error(String.format("fail to update user %s", customer), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to update user due to %s", exp.getMessage())));
    }
  }

  @GetMapping("/get")
  @Login
  public ResponseEntity<ResponseDto<Customer>> get(
      @RequestHeader("role") Role Role, @RequestParam Integer userId) {
    try {
      Customer customer = customerMgtService.get(userId);
      if (customer == null) {
        return ResponseEntity.status(400)
            .body(
                ResponseDto.newInstance(
                    USE_NOT_FOUND_ERROR, String.format("User %s doesn't exist in the system")));
      }
      return ResponseEntity.ok().body(ResponseDto.newInstance(customer));
    } catch (Exception exp) {
      log.error(String.format("fail to get user %s", userId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to get user due to %s", exp.getMessage())));
    }
  }

  @GetMapping("/getByUsername")
  @Login
  public ResponseEntity<ResponseDto<Customer>> getByUserName(
      @RequestHeader("role") Role Role, @RequestParam String username) {
    try {
      Customer customer = customerMgtService.getByUsername(username);
      if (customer == null) {
        return ResponseEntity.status(400)
            .body(
                ResponseDto.newInstance(
                    USE_NOT_FOUND_ERROR,
                    String.format("User %s doesn't exist in the system", username)));
      }
      return ResponseEntity.ok().body(ResponseDto.newInstance(customer));
    } catch (Exception exp) {
      log.error(String.format("fail to get user %s", username), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to get user by username due to %s", exp.getMessage())));
    }
  }
}
