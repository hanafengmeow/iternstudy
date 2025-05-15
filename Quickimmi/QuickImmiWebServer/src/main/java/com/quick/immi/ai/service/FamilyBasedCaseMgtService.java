/* (C) 2024 */
package com.quick.immi.ai.service;

import static com.quick.immi.ai.utils.CaseProgressUtils.initFamilyBasedCaseProgress;
import static com.quick.immi.ai.utils.familybased.FamilyBasedFormGenerationTaskUtils.*;

import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.quick.immi.ai.dto.request.CreateCaseByLawyerRequestDto;
import com.quick.immi.ai.dto.request.CreateFamilyBasedCaseByLawyerRequestDto;
import com.quick.immi.ai.dto.request.FamilyBasedCaseSummaryResponseDto;
import com.quick.immi.ai.dto.response.CaseSummaryResponseDto;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.familybased.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.familybased.FamilyBasedType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FamilyBasedCaseMgtService extends CaseMgtBaseService {

  @Override
  public <T extends CreateCaseByLawyerRequestDto> Long createByLawyer(T requestDto) {
    CreateFamilyBasedCaseByLawyerRequestDto caseRequestDto =
        (CreateFamilyBasedCaseByLawyerRequestDto) requestDto;

    Customer customer =
        customerMgtService.createCustomerByEmailIfNotExist(
            caseRequestDto.getProvidedCustomerEmail());

    String caseName =
        StringUtils.isNullOrEmpty(caseRequestDto.getCaseName())
            ? getDefaultCaseName(caseRequestDto.getApplicantName(), caseRequestDto.getCaseType())
            : caseRequestDto.getCaseName();

    ApplicationCase applicationCase =
        ApplicationCase.builder()
            .applicantName(caseRequestDto.getApplicantName())
            .status(CaseStatus.IN_PROGRESS.getValue())
            .caseName(caseName)
            .currentStep(CaseProgressStep.FILLING_APPLICATION)
            .userId(customer.getId())
            .email(customer.getEmail())
            .progress(new Gson().toJson(initFamilyBasedCaseProgress()))
            .type(CaseType.FamilyBased.getValue())
            .subType(caseRequestDto.getFamilyBasedType().getValue())
            .assignedLawyer(caseRequestDto.getLawyerId())
            .createdBy(caseRequestDto.getLawyerId())
            .createdByLawyer(true)
            .createdAt(System.currentTimeMillis())
            .updatedAt(System.currentTimeMillis())
            .build();

    caseMapper.create(applicationCase);
    return applicationCase.getId();
  }

  @Override
  public <T extends CaseSummaryResponseDto> T getCaseSummary(Long id) {
    // Retrieve the application case
    ApplicationCase applicationCase = get(id);
    // Retrieve the profile from the application case
    String profile = applicationCase.getProfile();
    // Parse the profile to AsylumCaseProfile object
    // TODO. extract more details about the profile

    return (T)
        FamilyBasedCaseSummaryResponseDto.builder()
            .id(applicationCase.getId())
            .caseName(applicationCase.getCaseName())
            .applicantName(applicationCase.getApplicantName())
            .caseType(CaseType.fromValue(applicationCase.getType()))
            .subType(FamilyBasedType.fromValue(applicationCase.getSubType()))
            .currentStep(applicationCase.getCurrentStep())
            .progress(
                new Gson().fromJson(applicationCase.getProgress(), ApplicationCaseProgress.class))
            .updatedAt(applicationCase.getUpdatedAt())
            .createdAt(applicationCase.getCreatedAt())
            .build();
  }

  @Override
  public void generateDocumentsByDocumentType(Long caseId, DocumentType documentType) {
    ApplicationCase applicationCase = get(caseId);
    FamilyBasedCaseProfile profile =
        new Gson().fromJson(applicationCase.getProfile(), FamilyBasedCaseProfile.class);
    Lawyer assignedLawyer = getLawyer(applicationCase.getAssignedLawyer());

    // create document generation tasks
    // 1. main form table generation --- the table provided by USCIS
    if (MAIN_FROM_SET.contains(documentType)) {
      buildMainFormGenerationTaskAndSendToSQS(
          applicationCase.getUserId(),
          applicationCase.getId(),
          documentType,
          assignedLawyer,
          profile,
          sqsService,
          formGenerationTaskMapper,
          documentService,
          false);
    } else if (documentType == DocumentType.ALL) {
      buildAllFormGenerationTaskAndSendToSQS(
          applicationCase.getUserId(),
          applicationCase.getId(),
          profile,
          assignedLawyer,
          sqsService,
          formGenerationTaskMapper,
          documentService);
    } else {
      throw new RuntimeException("not supported formType: " + documentType);
    }
  }
}
