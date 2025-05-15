/* (C) 2024 */
package com.quick.immi.ai.service;

import com.quick.immi.ai.dto.request.CaseQueryByLawyerRequestDto;
import com.quick.immi.ai.dto.request.CreateCaseByCustomerRequestDto;
import com.quick.immi.ai.dto.request.CreateCaseByLawyerRequestDto;
import com.quick.immi.ai.dto.response.CaseProfileResponseDto;
import com.quick.immi.ai.dto.response.CaseSummaryResponseDto;
import com.quick.immi.ai.dto.response.EligibilityCheckResultDto;
import com.quick.immi.ai.dto.response.ListCaseResponseDto;
import com.quick.immi.ai.entity.*;

public interface CaseMgtService {

  /**
   * Creates a new case by the customer based on the provided request data.
   *
   * @param createCaseRequestDto the request data containing details necessary to create the case.
   * @return the created ApplicationCase Id.
   */
  <T extends CreateCaseByCustomerRequestDto> Long createByCustomer(T createCaseRequestDto);

  /**
   * Creates a new case by the laywer based on the provided request data.
   *
   * @param createCaseRequestDto the request data containing details necessary to create the case.
   * @return the created ApplicationCase Id.
   */
  <T extends CreateCaseByLawyerRequestDto> Long createByLawyer(T createCaseRequestDto);

  /**
   * fetch the case list that are assigned to a specific lawyer, with additional filtering and
   * sorting options. The results can be paginated:
   *
   * <p>Parameters: lawyerId: The ID of the lawyer to whom the cases are assigned. This is a
   * required parameter. query: An optional search term that can be used to filter cases by the
   * applicant's name or by the case ID. currentStep: An optional parameter that filters cases based
   * on their current step in the process. sortedBy: An optional parameter that determines the
   * sorting order of the results (e.g., by update date, ID, or case name). pageSize: The number of
   * records to retrieve in a single query (used for pagination). offset: The offset from where to
   * start retrieving records (used for pagination).
   */
  ListCaseResponseDto queryByLawyer(CaseQueryByLawyerRequestDto caseQueryRequestDto);

  /**
   * Retrieves a list of cases associated with a specific customer based on the provided query
   * criteria.
   *
   * @param caseQueryRequestDto The DTO containing the necessary parameters to filter and retrieve
   *     the cases associated with the customer. This may include customer ID, case status, date
   *     range, or other relevant criteria.
   * @return ListCaseResponseDto A DTO containing the list of cases that match the provided query
   *     criteria. This response may include details such as case ID, status, creation date, and
   *     other relevant case information.
   * @apiNote This method is intended for querying cases by customer information and may return a
   *     large dataset depending on the query parameters. Consider using pagination in the request
   *     DTO for better performance.
   */
  ListCaseResponseDto queryByCustomer(CaseQueryByLawyerRequestDto caseQueryRequestDto);

  /**
   * Retrieves the profile of a case by its ID and maps it to the specified DTO class type.
   *
   * @param id the ID of the case to retrieve.
   * @param profileClass the class type to which the case profile should be mapped.
   * @param <T> the type of the DTO.
   * @return case profile mapped to the specified DTO class, or an empty Optional if the case is not
   *     found.
   */
  <T> CaseProfileResponseDto<T> getCaseProfile(Long id, Class<T> profileClass);

  /**
   * Retrieves the summary information of a case by its ID. The method returns a summary of the case
   * that can be of any type extending from {@link CaseSummaryResponseDto}.
   *
   * @param id the unique identifier of the case to retrieve.
   * @param <T> the type of the summary response, which must extend {@link CaseSummaryResponseDto}.
   * @return an instance of type {@code T}, representing the summary information of the case.
   */
  <T extends CaseSummaryResponseDto> T getCaseSummary(Long id);

  /**
   * get the application case by its ID.
   *
   * @param id the ID of the case to update.
   */
  ApplicationCase get(Long id);

  /**
   * Updates the details of an existing application case.
   *
   * @param applicationCase the ApplicationCase entity with updated information.
   */
  void update(ApplicationCase applicationCase);

  /**
   * Updates the name of a specific case by its ID.
   *
   * @param id the ID of the case to update.
   * @param caseName the new name to assign to the case.
   */
  void updateCaseName(Long id, String caseName);

  /**
   * Updates the progress of a specific case, including its current step and overall progress.
   *
   * @param caseId the ID of the case to update.
   * @param currentStep the current step of the case's progress.
   * @param asylumCaseProgress the overall progress details of the asylum case.
   */
  void updateProgress(
      Long caseId, CaseProgressStep currentStep, ApplicationCaseProgress asylumCaseProgress);

  /**
   * Deletes a case by its ID.
   *
   * @param caseId the ID of the case to be deleted.
   */
  void delete(Long caseId);

  /**
   * Generates application documents for a case based on the specified document type.
   *
   * @param caseId the ID of the case for which to generate documents.
   * @param documentType the type of document to generate.
   */
  void generateDocumentsByDocumentType(Long caseId, DocumentType documentType);

  /**
   * Checks the eligibility of document generation for a specific case
   *
   * @param caseId the ID of the case to check for eligibility for document generation.
   * @return an EligibilityCheckResultDto containing the result of the eligibility check for
   *     document generation.
   */
  EligibilityCheckResultDto checkEligibilityForDocumentGeneration(Long caseId);

  /**
   * Checks the eligibility of merging documents for a specific case
   *
   * @param caseId the ID of the case to check for eligibility for document merging.
   * @return an EligibilityCheckResultDto containing the result of the eligibility check for
   *     document merging.
   */
  EligibilityCheckResultDto checkEligibilityForDocumentMerge(Long caseId);
}
