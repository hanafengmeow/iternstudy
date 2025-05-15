/* (C) 2024 */
package com.quick.immi.ai.api;

import static com.quick.immi.ai.exception.Constant.SERVER_SIDE_ERROR;

import com.quick.immi.ai.annotation.CaseVerifier;
import com.quick.immi.ai.annotation.Login;
import com.quick.immi.ai.dto.request.CaseQueryByLawyerRequestDto;
import com.quick.immi.ai.dto.request.UpdateCaseNameRequestDto;
import com.quick.immi.ai.dto.request.UpdateProgressRequestDto;
import com.quick.immi.ai.dto.response.ListCaseResponseDto;
import com.quick.immi.ai.dto.response.ResponseDto;
import com.quick.immi.ai.entity.Role;
import com.quick.immi.ai.service.CaseMgtBaseService;
import com.quick.immi.ai.service.helper.SQSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/case")
@Slf4j
public class CaseMgtBaseController {

  @Autowired private CaseMgtBaseService caseMgtBaseService;
  @Autowired private SQSService sqsService;

  @Login
  @CaseVerifier
  @PostMapping("/updateCaseName")
  public ResponseEntity<ResponseDto<Boolean>> updateCaseName(
      @RequestBody UpdateCaseNameRequestDto updateCaseNameRequestDto) {
    try {
      caseMgtBaseService.updateCaseName(
          updateCaseNameRequestDto.getCaseId(), updateCaseNameRequestDto.getCaseName());
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (Exception exp) {
      log.error(
          String.format(
              "fail to update case name for case id: %s", updateCaseNameRequestDto.getCaseId()),
          exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to update case name due to %s", exp.getMessage())));
    }
  }

  @PostMapping("/queryByCustomer")
  @Login
  public ResponseEntity<ResponseDto<ListCaseResponseDto>> queryByCustomer(
      @RequestHeader("role") Role Role,
      @RequestBody CaseQueryByLawyerRequestDto caseQueryRequestDto) {
    try {
      return ResponseEntity.ok()
          .body(ResponseDto.newInstance(caseMgtBaseService.queryByCustomer(caseQueryRequestDto)));
    } catch (Exception exp) {
      log.error(
          String.format("fail to queryByCustomer for Customer: %s", caseQueryRequestDto), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Fail to queryByCustomer for Customer: %s", exp.getMessage())));
    }
  }

  @PostMapping("/queryByLawyer")
  @Login
  public ResponseEntity<ResponseDto<ListCaseResponseDto>> queryByLawyer(
      @RequestHeader("role") Role Role,
      @RequestBody CaseQueryByLawyerRequestDto caseQueryRequestDto) {
    try {
      return ResponseEntity.ok()
          .body(ResponseDto.newInstance(caseMgtBaseService.queryByLawyer(caseQueryRequestDto)));
    } catch (Exception exp) {
      log.error(String.format("fail to queryByLawyer for lawyer: %s", caseQueryRequestDto), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Fail to queryByLawyer for lawyer: %s", exp.getMessage())));
    }
  }

  @Login
  @PostMapping("/updateProgress")
  public ResponseEntity<ResponseDto<Boolean>> updateProgress(
      @RequestHeader("role") Role Role,
      @RequestBody UpdateProgressRequestDto updateProgressRequestDto) {
    try {
      caseMgtBaseService.updateProgress(
          updateProgressRequestDto.getCaseId(),
          updateProgressRequestDto.getCurrentStep(),
          updateProgressRequestDto.getAsylumCaseProgress());
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (Exception exp) {
      log.error(String.format("Failed to updateProgress due to: %s", exp.getMessage()), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to updateProgress due to %s", exp.getMessage())));
    }
  }

  @GetMapping("/delete")
  @Login
  public ResponseEntity<ResponseDto<Boolean>> delete(
      @RequestHeader("role") Role Role, @RequestParam("caseId") Long caseId) {
    try {
      caseMgtBaseService.delete(caseId);
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (Exception exp) {
      log.error(String.format("fail to delete application case for case id: %s", caseId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to delete case due to %s", exp.getMessage())));
    }
  }
}
