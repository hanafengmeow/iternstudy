/* (C) 2024 */
package com.quick.immi.ai.api;

import static com.quick.immi.ai.exception.Constant.SERVER_SIDE_ERROR;

import com.quick.immi.ai.annotation.CaseVerifier;
import com.quick.immi.ai.annotation.Login;
import com.quick.immi.ai.dto.request.CreateEmploymentBasedCaseByLawyerRequestDto;
import com.quick.immi.ai.dto.request.UpdateApplicationCaseRequestDto;
import com.quick.immi.ai.dto.response.CaseProfileResponseDto;
import com.quick.immi.ai.dto.response.EmploymentBasedCaseSummaryResponseDto;
import com.quick.immi.ai.dto.response.ResponseDto;
import com.quick.immi.ai.entity.ApplicationCase;
import com.quick.immi.ai.entity.employmentbased.EmploymentBasedCaseProfile;
import com.quick.immi.ai.service.EmploymentBasedCaseMgtService;
import com.quick.immi.ai.utils.ApplicationCaseConvertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/case/employment-based")
@Slf4j
public class EmploymentBasedCaseMgtController {

  @Autowired private EmploymentBasedCaseMgtService employmentBasedCaseMgtService;

  @Login
  @PostMapping("/createByLawyer")
  public ResponseEntity<ResponseDto<Long>> createByLawyer(
      @RequestBody CreateEmploymentBasedCaseByLawyerRequestDto caseRequestDto) {
    log.info(String.format("createByLawyer api receive %s", caseRequestDto));
    try {
      Long caseId = employmentBasedCaseMgtService.createByLawyer(caseRequestDto);
      return ResponseEntity.ok().body(ResponseDto.newInstance(caseId));
    } catch (Exception exp) {
      log.error(
          String.format("fail to create application case for request: %s", caseRequestDto), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to create case due to %s", exp.getMessage())));
    }
  }

  @Login
  @PostMapping("/update")
  @CaseVerifier
  public ResponseEntity<ResponseDto<Boolean>> update(
      @RequestBody UpdateApplicationCaseRequestDto<EmploymentBasedCaseProfile> caseRequestDto) {
    log.info(String.format("UpdateApplicationCaseRequestDto api receive %s", caseRequestDto));
    try {
      ApplicationCase applicationCase = ApplicationCaseConvertor.convert(caseRequestDto);
      employmentBasedCaseMgtService.update(applicationCase);
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (Exception exp) {
      log.error(
          String.format("fail to update application case for request: %s", caseRequestDto), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to update case due to %s", exp.getMessage())));
    }
  }

  @Login
  @CaseVerifier
  @GetMapping("/getCaseProfile")
  public ResponseEntity<ResponseDto<CaseProfileResponseDto<EmploymentBasedCaseProfile>>>
      getCaseProfile(@RequestParam("id") Long caseId) {
    try {
      CaseProfileResponseDto<EmploymentBasedCaseProfile> caseProfile =
          employmentBasedCaseMgtService.getCaseProfile(caseId, EmploymentBasedCaseProfile.class);
      return ResponseEntity.ok().body(ResponseDto.newInstance(caseProfile));
    } catch (Exception exp) {
      log.error(String.format("fail to get case profile for case id: %s", caseId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to get case profile due to %s", exp.getMessage())));
    }
  }

  @Login
  @CaseVerifier
  @GetMapping("/getCaseSummary")
  public ResponseEntity<ResponseDto<EmploymentBasedCaseSummaryResponseDto>> getCaseSummary(
      @RequestParam("id") Long caseId) {
    try {
      EmploymentBasedCaseSummaryResponseDto caseSummary =
          employmentBasedCaseMgtService.getCaseSummary(caseId);
      return ResponseEntity.ok().body(ResponseDto.newInstance(caseSummary));
    } catch (Exception exp) {
      log.error(String.format("fail to get case summary for case id: %s", caseId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to get case summary due to %s", exp.getMessage())));
    }
  }
}
