/* (C) 2024 */
package com.quick.immi.ai.service;

import static com.quick.immi.ai.utils.CaseProgressUtils.initEmploymentBasedCaseProgress;

import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.quick.immi.ai.dto.request.CreateCaseByLawyerRequestDto;
import com.quick.immi.ai.dto.request.CreateEmploymentBasedCaseByLawyerRequestDto;
import com.quick.immi.ai.dto.response.CaseSummaryResponseDto;
import com.quick.immi.ai.dto.response.EmploymentBasedCaseSummaryResponseDto;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.employmentbased.EmploymentBasedType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmploymentBasedCaseMgtService extends CaseMgtBaseService {

  @Override
  public <T extends CreateCaseByLawyerRequestDto> Long createByLawyer(T requestDto) {
    CreateEmploymentBasedCaseByLawyerRequestDto caseRequestDto =
        (CreateEmploymentBasedCaseByLawyerRequestDto) requestDto;

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
            .progress(new Gson().toJson(initEmploymentBasedCaseProgress()))
            .type(CaseType.EmploymentBased.getValue())
            .subType(caseRequestDto.getEmploymentBasedType().getValue())
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
        EmploymentBasedCaseSummaryResponseDto.builder()
            .id(applicationCase.getId())
            .caseName(applicationCase.getCaseName())
            .applicantName(applicationCase.getApplicantName())
            .caseType(CaseType.fromValue(applicationCase.getType()))
            .subType(EmploymentBasedType.fromValue(applicationCase.getSubType()))
            .currentStep(applicationCase.getCurrentStep())
            .progress(
                new Gson().fromJson(applicationCase.getProgress(), ApplicationCaseProgress.class))
            .updatedAt(applicationCase.getUpdatedAt())
            .createdAt(applicationCase.getCreatedAt())
            .build();
  }
}
