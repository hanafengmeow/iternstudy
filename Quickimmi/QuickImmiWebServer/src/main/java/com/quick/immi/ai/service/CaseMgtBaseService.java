/* (C) 2024 */
package com.quick.immi.ai.service;

import com.google.gson.Gson;
import com.quick.immi.ai.dao.ApplicationCaseMapper;
import com.quick.immi.ai.dao.FormGenerationTaskMapper;
import com.quick.immi.ai.dao.LawyerMapper;
import com.quick.immi.ai.dto.request.*;
import com.quick.immi.ai.dto.response.CaseProfileResponseDto;
import com.quick.immi.ai.dto.response.CaseSummaryResponseDto;
import com.quick.immi.ai.dto.response.EligibilityCheckResultDto;
import com.quick.immi.ai.dto.response.ListCaseResponseDto;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.exception.CaseNotFundException;
import com.quick.immi.ai.exception.UserNotFundException;
import com.quick.immi.ai.service.helper.EntityCacheService;
import com.quick.immi.ai.service.helper.OpenAIService;
import com.quick.immi.ai.service.helper.SQSService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CaseMgtBaseService implements CaseMgtService {
  @Autowired protected FormGenerationTaskMapper formGenerationTaskMapper;
  @Autowired protected DocumentMgtService documentService;
  @Autowired protected SQSService sqsService;
  @Autowired protected EntityCacheService cacheService;
  @Autowired protected LawyerMapper lawyerMapper;
  @Autowired protected CustomerMgtService customerMgtService;
  @Autowired protected OpenAIService openAIService;

  @Autowired protected ApplicationCaseMapper caseMapper;

  @Override
  public <T extends CreateCaseByCustomerRequestDto> Long createByCustomer(T createCaseRequestDto) {
    throw new UnsupportedOperationException("Unsupported Operation in Base Class");
  }

  @Override
  public <T extends CreateCaseByLawyerRequestDto> Long createByLawyer(T createCaseRequestDto) {
    throw new UnsupportedOperationException("Unsupported Operation in Base Class");
  }

  @Override
  public ListCaseResponseDto queryByLawyer(CaseQueryByLawyerRequestDto caseQueryRequestDto) {
    String currentStep =
        caseQueryRequestDto.getCurrentStep() != null
            ? caseQueryRequestDto.getCurrentStep().getName()
            : null;
    String sortedBy =
        caseQueryRequestDto.getSortedBy() != null
            ? caseQueryRequestDto.getSortedBy().getValue()
            : null;

    CaseQueryRequest caseQueryRequest =
        CaseQueryRequest.builder()
            .lawyerId(caseQueryRequestDto.getLawyerId())
            .query(caseQueryRequestDto.getQuery())
            .sortedBy(sortedBy)
            .currentStep(currentStep)
            .offset((caseQueryRequestDto.getPageNumber() - 1) * caseQueryRequestDto.getPageSize())
            .pageSize(caseQueryRequestDto.getPageSize())
            .build();

    List<ApplicationCase> applicationList = caseMapper.queryByLawyer(caseQueryRequest);
    ListCaseResponseDto result = new ListCaseResponseDto();
    ListCaseResponseDto.Metadata resMetadata = getMetadata(caseQueryRequestDto);

    List<ListCaseResponseDto.Case> caseList =
        applicationList == null
            ? new ArrayList<>()
            : applicationList.stream()
                .map(applicationCase -> getCase(applicationCase))
                .collect(Collectors.toList());
    result.setCases(caseList);
    result.setMetadata(resMetadata);
    return result;
  }

  @Override
  public ListCaseResponseDto queryByCustomer(CaseQueryByLawyerRequestDto caseQueryRequestDto) {
    String currentStep =
        caseQueryRequestDto.getCurrentStep() != null
            ? caseQueryRequestDto.getCurrentStep().getName()
            : null;
    String sortedBy =
        caseQueryRequestDto.getSortedBy() != null
            ? caseQueryRequestDto.getSortedBy().getValue()
            : null;

    CaseQueryRequest caseQueryRequest =
        CaseQueryRequest.builder()
            .lawyerId(caseQueryRequestDto.getLawyerId())
            .query(caseQueryRequestDto.getQuery())
            .sortedBy(sortedBy)
            .currentStep(currentStep)
            .offset((caseQueryRequestDto.getPageNumber() - 1) * caseQueryRequestDto.getPageSize())
            .pageSize(caseQueryRequestDto.getPageSize())
            .build();

    List<ApplicationCase> applicationList = caseMapper.queryByCustomer(caseQueryRequest);
    ListCaseResponseDto result = new ListCaseResponseDto();
    ListCaseResponseDto.Metadata resMetadata = getMetadata(caseQueryRequestDto);

    List<ListCaseResponseDto.Case> caseList =
        applicationList == null
            ? new ArrayList<>()
            : applicationList.stream()
                .map(applicationCase -> getCase(applicationCase))
                .collect(Collectors.toList());
    result.setCases(caseList);
    result.setMetadata(resMetadata);
    return result;
  }

  @Override
  public <T> CaseProfileResponseDto<T> getCaseProfile(Long id, Class<T> profileClass) {
    // Fetch or generate the profile based on the given ID and profile class
    ApplicationCase applicationCase = this.get(id);

    T profile = new Gson().fromJson(applicationCase.getProfile(), profileClass);
    ApplicationCaseProgress progress =
        new Gson().fromJson(applicationCase.getProgress(), ApplicationCaseProgress.class);
    // Return a CaseProfileResponseDto object using the builder pattern
    return CaseProfileResponseDto.<T>builder().profile(profile).progress(progress).build();
  }

  @Override
  public <T extends CaseSummaryResponseDto> T getCaseSummary(Long id) {
    throw new UnsupportedOperationException("Unsupported Operation in Base Class");
  }

  @Override
  public void delete(Long caseId) {
    caseMapper.delete(caseId);
  }

  @Override
  public void generateDocumentsByDocumentType(Long caseId, DocumentType documentType) {
    throw new UnsupportedOperationException("Unsupported Operation in Base Class");
  }

  @Override
  public EligibilityCheckResultDto checkEligibilityForDocumentGeneration(Long caseId) {
    throw new UnsupportedOperationException("Unsupported Operation in Base Class");
  }

  @Override
  public EligibilityCheckResultDto checkEligibilityForDocumentMerge(Long caseId) {
    throw new UnsupportedOperationException("Unsupported Operation in Base Class");
  }

  @Override
  public void updateCaseName(Long caseId, String caseName) {
    ApplicationCase applicationCase = caseMapper.get(caseId);
    applicationCase.setCaseName(caseName);
    this.update(applicationCase);
  }

  @Override
  public void updateProgress(
      Long caseId, CaseProgressStep currentStep, ApplicationCaseProgress asylumCaseProgress) {
    caseMapper.updateProgress(caseId, currentStep.getName(), new Gson().toJson(asylumCaseProgress));
  }

  @Override
  public void update(ApplicationCase applicationCase) {
    caseMapper.update(applicationCase);
  }

  protected Lawyer getLawyer(Integer lawyerId) {
    try {
      Lawyer assignedLawyer = lawyerMapper.get(lawyerId);
      if (assignedLawyer == null) {
        throw new UserNotFundException();
      }
      return assignedLawyer;
    } catch (Exception e) {
      log.error("fail to get lawyer info ", e);
      throw new RuntimeException("Failed to get assigned lawyer with ID: " + lawyerId, e);
    }
  }

  @Override
  public ApplicationCase get(Long caseId) {
    ApplicationCase applicationCase = caseMapper.get(caseId);
    if (applicationCase == null) {
      throw new CaseNotFundException();
    }
    return applicationCase;
  }

  /** used by createdByLawyer API to generate default caseName if caseName is empty */
  @NotNull
  protected String getDefaultCaseName(String applicantName, CaseType caseType) {
    return String.format(
        "%s-%s-%s",
        applicantName,
        caseType.getValue(),
        LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
  }

  // TODO: refactor the asylum type out.
  private ListCaseResponseDto.Case getCase(ApplicationCase applicationCase) {
    ListCaseResponseDto.Case.CaseBuilder caseBuilder = ListCaseResponseDto.Case.builder();
    Long overallPercentage = getOverallPercentage(applicationCase);
    return caseBuilder
        .id(applicationCase.getId())
        .userId(applicationCase.getUserId())
        .updatedAt(applicationCase.getUpdatedAt())
        .applicantName(applicationCase.getApplicantName())
        .caseName(applicationCase.getCaseName())
        .type(applicationCase.getType())
        .subType(applicationCase.getSubType())
        .asylumType(applicationCase.getSubType())
        .currentStep(applicationCase.getCurrentStep())
        .overallPercentage(overallPercentage)
        .build();
  }

  private Long getOverallPercentage(ApplicationCase applicationCase) {
    ApplicationCaseProgress progress =
        new Gson().fromJson(applicationCase.getProgress(), ApplicationCaseProgress.class);
    if (progress != null && progress.getSteps() != null && progress.getSteps().size() > 0) {
      String metadata = progress.getSteps().get(0).getSubsteps().get(0).getMetadata();
      if (metadata != null && !metadata.isEmpty()) {
        String[] parts = metadata.split("\"overall\":\\{\"avg\":");
        String[] remainParts = parts[1].split("\\}");
        return Long.valueOf(remainParts[0]);
      }
    }
    return null;
  }

  private ListCaseResponseDto.Metadata getMetadata(
      CaseQueryByLawyerRequestDto caseQueryRequestDto) {
    ListCaseResponseDto.Metadata resMetadata = new ListCaseResponseDto.Metadata();
    resMetadata.setPageNumber(caseQueryRequestDto.getPageNumber());
    resMetadata.setPageSize(caseQueryRequestDto.getPageSize());
    resMetadata.setTotalItems(caseMapper.getCountByLawyer(caseQueryRequestDto.getLawyerId()));
    return resMetadata;
  }
}
