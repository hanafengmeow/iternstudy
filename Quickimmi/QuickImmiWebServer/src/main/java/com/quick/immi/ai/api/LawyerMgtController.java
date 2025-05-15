/* (C) 2024 */
package com.quick.immi.ai.api;

import static com.quick.immi.ai.exception.Constant.SERVER_SIDE_ERROR;
import static com.quick.immi.ai.exception.Constant.USE_NOT_FOUND_ERROR;

import com.quick.immi.ai.annotation.Login;
import com.quick.immi.ai.dto.common.LawyerDto;
import com.quick.immi.ai.dto.request.CreateLawyerRequest;
import com.quick.immi.ai.dto.request.UpdateLawyerRequest;
import com.quick.immi.ai.dto.response.ResponseDto;
import com.quick.immi.ai.entity.Lawyer;
import com.quick.immi.ai.entity.Role;
import com.quick.immi.ai.service.LawyerMgtService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lawyer")
@Slf4j
public class LawyerMgtController {

  @Autowired private LawyerMgtService lawyerMgtService;

  @PostMapping("/create")
  @Login
  public ResponseEntity<ResponseDto<Integer>> create(
      @RequestHeader("role") Role Role, @RequestBody CreateLawyerRequest request) {
    try {
      Integer id = lawyerMgtService.create(request);
      return ResponseEntity.ok().body(ResponseDto.newInstance(id));
    } catch (Exception exp) {
      log.error("Fail to create lawyer", exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to create lawyer due to %s", exp.getMessage())));
    }
  }

  @PostMapping("/update")
  @Login
  public ResponseEntity<ResponseDto<Boolean>> update(
      @RequestHeader("role") Role Role, @RequestBody UpdateLawyerRequest request) {
    try {
      lawyerMgtService.update(request);
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (Exception exp) {
      log.error("Fail to update lawyer", exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to update lawyer due to %s", exp.getMessage())));
    }
  }

  @GetMapping("/getLawyerByCognitoId")
  @Login
  public ResponseEntity<ResponseDto<LawyerDto>> getLawyerByCognitoId(
      @RequestHeader("role") Role Role, @RequestParam("cognitoId") String cognitoId) {
    try {
      LawyerDto lawyer = this.lawyerMgtService.getByCognitoUsername(cognitoId);
      if (lawyer == null) {
        return ResponseEntity.status(400)
            .body(
                ResponseDto.newInstance(
                    USE_NOT_FOUND_ERROR,
                    String.format("User %s doesn't exist in the system", cognitoId)));
      }
      return ResponseEntity.ok().body(ResponseDto.newInstance(lawyer));
    } catch (Exception e) {
      log.error(String.format("Fail to get lawyer by cognitoId: %s", cognitoId), e);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Fail to get lawyer by cognitoId %s", e.getMessage())));
    }
  }

  // username can be email, phoneNumber or any other id that are used when signup.
  @GetMapping("/getLawyerByUsername")
  @Login
  public ResponseEntity<ResponseDto<LawyerDto>> getLawyerByUsername(
      @RequestHeader("role") Role Role, @RequestParam("username") String username) {
    try {
      LawyerDto lawyer = this.lawyerMgtService.getByUsername(username);
      if (lawyer == null) {
        return ResponseEntity.status(400)
            .body(
                ResponseDto.newInstance(
                    USE_NOT_FOUND_ERROR,
                    String.format("User %s doesn't exist in the system", username)));
      }
      return ResponseEntity.ok().body(ResponseDto.newInstance(lawyer));
    } catch (Exception e) {
      log.error(String.format("Fail to get lawyer by username: %s", username), e);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Fail to get lawyer by username %s", e.getMessage())));
    }
  }

  @GetMapping("/getAvailableLawyer")
  @Login
  @Deprecated
  public ResponseEntity<ResponseDto<List<Lawyer>>> getAvailableLawyer() {
    try {
      List<Lawyer> lawyers = this.lawyerMgtService.getAvailableLawyers();
      return ResponseEntity.ok().body(ResponseDto.newInstance(lawyers));
    } catch (Exception e) {
      log.error("Fail to get getAvailableLawyer", e);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Fail to getAvailableLawyer due to %s", e.getMessage())));
    }
  }
}
