/* (C) 2024 */
package com.quick.immi.ai.service;

import static com.quick.immi.ai.utils.CaseProgressUtils.initNonImmigrantVisasCaseProgress;

import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.quick.immi.ai.dto.request.CreateCaseByLawyerRequestDto;
import com.quick.immi.ai.dto.request.CreateNonImmigrantVisasCaseByLawyerRequestDto;
import com.quick.immi.ai.dto.response.CaseSummaryResponseDto;
import com.quick.immi.ai.dto.response.NonImmigrantVisasCaseSummaryResponseDto;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.nonimmigrant.NonImmigrantVisaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NonImmigrantVisasCaseMgtService extends CaseMgtBaseService {

  @Override
  public <T extends CreateCaseByLawyerRequestDto> Long createByLawyer(T requestDto) {
    CreateNonImmigrantVisasCaseByLawyerRequestDto caseRequestDto =
        (CreateNonImmigrantVisasCaseByLawyerRequestDto) requestDto;

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
            .progress(new Gson().toJson(initNonImmigrantVisasCaseProgress()))
            .type(CaseType.NonImmigrantVisas.getValue())
            .subType(caseRequestDto.getNonImmigrantVisaType().getValue())
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
        NonImmigrantVisasCaseSummaryResponseDto.builder()
            .id(applicationCase.getId())
            .caseName(applicationCase.getCaseName())
            .applicantName(applicationCase.getApplicantName())
            .caseType(CaseType.fromValue(applicationCase.getType()))
            .subType(NonImmigrantVisaType.fromValue(applicationCase.getSubType()))
            .currentStep(applicationCase.getCurrentStep())
            .progress(
                new Gson().fromJson(applicationCase.getProgress(), ApplicationCaseProgress.class))
            .updatedAt(applicationCase.getUpdatedAt())
            .createdAt(applicationCase.getCreatedAt())
            .build();
  }
}
