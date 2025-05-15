/* (C) 2024 */
package com.quick.immi.ai.api;

import static com.quick.immi.ai.exception.Constant.APPLICATION_CASE_NOT_FOUND_ERROR;
import static com.quick.immi.ai.exception.Constant.SERVER_SIDE_ERROR;

import com.quick.immi.ai.annotation.CaseVerifier;
import com.quick.immi.ai.annotation.Login;
import com.quick.immi.ai.dto.common.MergePdfsDto;
import com.quick.immi.ai.dto.request.*;
import com.quick.immi.ai.dto.response.*;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.asylum.AsylumCaseProfile;
import com.quick.immi.ai.service.AsylumCaseMgtService;
import com.quick.immi.ai.service.MergeAsylumDocumentsService;
import com.quick.immi.ai.service.helper.RedisService;
import com.quick.immi.ai.service.helper.SQSService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/case/asylum")
@Slf4j
public class AsylumCaseMgtController {

  @Autowired private AsylumCaseMgtService asylumCaseMgtService;
  @Autowired private MergeAsylumDocumentsService mergeAsylumDocumentsService;
  @Autowired private SQSService sqsService;
  @Autowired private RedisService redisService;

  // TODO: change the path to createByCustomer
  @Login
  @PostMapping("/create")
  public ResponseEntity<ResponseDto<Long>> createByCustomer(
      @RequestBody CreateAsylumCaseByCustomerRequestDto caseRequestDto) {
    log.info(String.format("createByCustomer api receive %s", caseRequestDto));
    try {
      Long caseId = asylumCaseMgtService.createByCustomer(caseRequestDto);
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
  @PostMapping("/createByLawyer")
  public ResponseEntity<ResponseDto<Long>> createByLawyer(
      @RequestBody CreateAsylumCaseByLawyerRequestDto caseRequestDto) {
    log.info(String.format("createByLawyer api receive %s", caseRequestDto));
    try {
      Long caseId = asylumCaseMgtService.createByLawyer(caseRequestDto);
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
  @CaseVerifier
  @GetMapping("/getCaseSummary")
  public ResponseEntity<ResponseDto<AsylumCaseSummaryResponseDto>> getCaseSummary(
      @RequestParam("id") Long caseId) {
    try {
      AsylumCaseSummaryResponseDto caseSummary = asylumCaseMgtService.getCaseSummary(caseId);
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

  @Login
  @CaseVerifier
  @GetMapping("/getCaseProfile")
  public ResponseEntity<ResponseDto<CaseProfileResponseDto<AsylumCaseProfile>>> getCaseProfile(
      @RequestParam("id") Long caseId) {
    try {
      CaseProfileResponseDto<AsylumCaseProfile> caseProfile =
          asylumCaseMgtService.getCaseProfile(caseId, AsylumCaseProfile.class);
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
  @PostMapping("/update")
  public ResponseEntity<ResponseDto<Boolean>> update(
      @RequestBody AsylumApplicationCaseDto applicationCaseDto) {
    log.info(String.format("update application case with %s", applicationCaseDto));
    try {
      Long caseId = applicationCaseDto.getId();
      if (caseId == null) {
        return ResponseEntity.status(400)
            .body(
                ResponseDto.newInstance(
                    APPLICATION_CASE_NOT_FOUND_ERROR, String.format("no case id provided")));
      }
      asylumCaseMgtService.update(applicationCaseDto);
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (Exception exp) {
      log.error(
          String.format("fail to update application case given input : %s", applicationCaseDto),
          exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to update case due to %s", exp.getMessage())));
    }
  }

  @GetMapping("/checkEligibility")
  @CaseVerifier
  @Login
  public ResponseEntity<ResponseDto<EligibilityCheckResultDto>> checkEligibility(
      @RequestParam("id") Long caseId) {
    try {
      EligibilityCheckResultDto result =
          asylumCaseMgtService.checkEligibilityForDocumentGeneration(caseId);
      return ResponseEntity.ok().body(ResponseDto.newInstance(result));
    } catch (Exception exp) {
      log.error(String.format("fail to eligibilityCheck for caseId : %s", caseId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to eligibilityCheck due to %s", exp.getMessage())));
    }
  }

  @GetMapping("/checkEligibilityForMerge")
  @CaseVerifier
  @Login
  public ResponseEntity<ResponseDto<EligibilityCheckResultDto>> checkEligibilityForMerge(
      @RequestParam("id") Long caseId) {
    try {
      EligibilityCheckResultDto result =
          asylumCaseMgtService.checkEligibilityForDocumentMerge(caseId);
      return ResponseEntity.ok().body(ResponseDto.newInstance(result));
    } catch (Exception exp) {
      log.error(String.format("fail to checkEligibilityForMerge for caseId : %s", caseId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to checkEligibilityForMerge due to %s", exp.getMessage())));
    }
  }

  @GetMapping("/generateDocumentsByDocumentType")
  @CaseVerifier
  @Login
  public ResponseEntity<ResponseDto<List<GenerateDocumentResultDto>>>
      generateDocumentsByDocumentType(
          @RequestParam("id") Long caseId,
          @RequestParam("documentType") DocumentType documentType) {
    try {
      asylumCaseMgtService.generateDocumentsByDocumentType(caseId, documentType);
      return ResponseEntity.ok().body(ResponseDto.newInstance(new ArrayList<>()));
    } catch (Exception exp) {
      log.error(
          String.format("fail to generateDocumentsByDocumentType for caseId : %s", caseId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format(
                      "Failed to generateDocumentsByDocumentType due to %s", exp.getMessage())));
    }
  }

  @Login
  @CaseVerifier
  @PostMapping("/mergePDFs")
  public ResponseEntity<ResponseDto<Long>> mergePDFs(
      @RequestParam("id") Long caseId, @RequestBody MergePdfsDto mergePdfsDto) {
    try {
      Long taskId = mergeAsylumDocumentsService.mergePDFs(caseId, mergePdfsDto);
      return ResponseEntity.ok().body(ResponseDto.newInstance(taskId));
    } catch (Exception exp) {
      log.error(String.format("failed to merge pdfs for caseId : %s", caseId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to merge pdfs due to %s", exp.getMessage())));
    }
  }

  @Login
  @CaseVerifier
  @PostMapping("/defaultMerge")
  public ResponseEntity<ResponseDto<List<GenerateDocumentResultDto>>> defaultMerge(
      @RequestParam("id") Long caseId) {
    try {
      List<GenerateDocumentResultDto> generateDocumentResultDtos =
          mergeAsylumDocumentsService.defaultMerge(caseId);
      return ResponseEntity.ok().body(ResponseDto.newInstance(generateDocumentResultDtos));
    } catch (Exception exp) {
      log.error(String.format("failed to default merge for caseId : %s", caseId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to default merge due to %s", exp.getMessage())));
    }
  }

  @Login
  @PostMapping("/refine")
  public ResponseEntity<ResponseDto<String>> refine(
      @RequestBody RefineRequestDto refineRequestDto) {
    try {
      String refinedContent = asylumCaseMgtService.refine(refineRequestDto);
      return ResponseEntity.ok().body(ResponseDto.newInstance(refinedContent));
    } catch (Exception exp) {
      log.error(String.format("Failed to refine case due to: %s", exp.getMessage()), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to refine case due to %s", exp.getMessage())));
    }
  }

  @Login
  @PostMapping("/refineWithPrompt")
  public ResponseEntity<ResponseDto<RefineResponseDto>> refineWithPrompt(
      @RequestBody RefineRequestDto refineRequestDto) {
    try {
      RefineResponseDto refinedContent = asylumCaseMgtService.refineWithPrompt(refineRequestDto);
      return ResponseEntity.ok().body(ResponseDto.newInstance(refinedContent));
    } catch (Exception exp) {
      log.error(String.format("Failed to refine case due to: %s", exp.getMessage()), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to refine case due to %s", exp.getMessage())));
    }
  }

  @Login
  @GetMapping("/generatePersonalStatement")
  public ResponseEntity<ResponseDto<String>> generatePersonalStatement(
      @RequestParam("caseId") Long caseId, @RequestParam("language") Language language) {
    try {
      String ps = asylumCaseMgtService.generatePersonalStatement(caseId, language);
      return ResponseEntity.ok().body(ResponseDto.newInstance(ps));
    } catch (Exception exp) {
      log.error(
          String.format("Failed to generatePersonalStatement due to: %s", exp.getMessage()), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format(
                      "Failed to generatePersonalStatement due to %s", exp.getMessage())));
    }
  }

  @Login
  @GetMapping("/generatePSWithAIForCase")
  public ResponseEntity<ResponseDto<RefinePSResponseDto>> generatePSWithAIForCase(
      @RequestParam("caseId") Long caseId, @RequestParam("language") Language originalLanguage) {
    try {

      RefinePSResponseDto ps =
          asylumCaseMgtService.generatePSWithAIForCase(caseId, originalLanguage);
      return ResponseEntity.ok().body(ResponseDto.newInstance(ps));
    } catch (Exception exp) {
      log.error(
          String.format("Failed to generatePersonalStatement due to: %s", exp.getMessage()), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format(
                      "Failed to generatePersonalStatement due to %s", exp.getMessage())));
    }
  }

  @Login
  @PostMapping("/RefinePSWithPrompt")
  public ResponseEntity<ResponseDto<RefinePSResponseDto>> RefinePSWithPrompt(
      @RequestBody RefinePSRequestDto refinePSRequestDto) {
    try {
      RefinePSResponseDto ps = asylumCaseMgtService.refinePSWithPrompt(refinePSRequestDto);
      return ResponseEntity.ok().body(ResponseDto.newInstance(ps));
    } catch (Exception exp) {
      log.error(
          String.format("Failed to refinePersonalStatement due to: %s", exp.getMessage()), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to refinePersonalStatement due to %s", exp.getMessage())));
    }
  }

  @Login
  @GetMapping("/generateCoverLetter")
  public ResponseEntity<ResponseDto<String>> generateCoverLetter(
      @RequestParam("caseId") Long caseId) {
    try {
      String cl = asylumCaseMgtService.generateCoverLetter(caseId);
      return ResponseEntity.ok().body(ResponseDto.newInstance(cl));
    } catch (Exception exp) {
      log.error(String.format("Failed to generateCoverLetter due to: %s", exp.getMessage()), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to generateCoverLetter due to %s", exp.getMessage())));
    }
  }

  @Login
  @PostMapping("/translatePersonalStatementToOriginalLanguage")
  public ResponseEntity<ResponseDto<String>> translatePersonalStatementToOriginalLanguage(
      @RequestBody SavePersonalStatementRequestDto savePersonalStatementRequestDto) {
    try {
      String ps =
          asylumCaseMgtService.translatePersonalStatementToOriginalLanguage(
              savePersonalStatementRequestDto.getContent(),
              savePersonalStatementRequestDto.getLanguage());
      return ResponseEntity.ok().body(ResponseDto.newInstance(ps));
    } catch (Exception exp) {
      log.error(
          String.format("Failed to generatePersonalStatement due to: %s", exp.getMessage()), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format(
                      "Failed to generatePersonalStatement due to %s", exp.getMessage())));
    }
  }

  @Login
  @PostMapping("/translatePersonalStatementToEnglishAndOriginalLanguage")
  public ResponseEntity<ResponseDto<RefinePSResponseDto>>
      translatePersonalStatementToEnglishAndOriginalLanguage(
          @RequestBody SavePersonalStatementRequestDto savePersonalStatementRequestDto) {
    try {
      RefinePSResponseDto bilingualPS =
          asylumCaseMgtService.translatePersonalStatementToEnglishAndOriginalLanguage(
              savePersonalStatementRequestDto.getContent(),
              savePersonalStatementRequestDto.getLanguage());

      return ResponseEntity.ok().body(ResponseDto.newInstance(bilingualPS));
    } catch (Exception exp) {
      log.error(
          String.format(
              "Failed to translate personal statement into two languages due to: %s",
              exp.getMessage()),
          exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format(
                      "Failed to translate personal statement into two languages due to %s",
                      exp.getMessage())));
    }
  }

  @GetMapping("/triggerEOIRCrawlingJob")
  @Login
  public ResponseEntity<ResponseDto<TriggerDataCrawlingResultDto>> triggerEOIRCrawlingJob(
      @RequestParam("caseId") Long caseId, @RequestParam("aNumber") String aNumber) {
    try {
      TriggerDataCrawlingResultDto triggerDataCrawlingResultDto =
          asylumCaseMgtService.triggerEOIRCrawlingJob(caseId, aNumber);
      return ResponseEntity.ok().body(ResponseDto.newInstance(triggerDataCrawlingResultDto));
    } catch (Exception exp) {
      String errMesg =
          String.format(
              "fail to trigger eoir crawling task for caseId %s with aNumber %s", caseId, aNumber);
      log.error(errMesg, exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to triggerEOIRCrawlingJob due to %s", exp.getMessage())));
    }
  }
}
