/* (C) 2024 */
package com.quick.immi.ai.utils;

import com.google.gson.Gson;
import com.quick.immi.ai.dao.FormGenerationTaskMapper;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.service.DocumentMgtService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormGenerationBaseUtils {

  public static boolean shouldSkipFormGeneration(
      boolean checkManualOverrideFlag,
      DocumentMgtService documentService,
      Long caseId,
      DocumentType documentType) {
    if (checkManualOverrideFlag) {
      Optional<Document> document =
          documentService.getExistDocument(caseId, documentType.getName(), Identify.Applicant);
      if (document.isPresent() && Boolean.TRUE.equals(document.get().getManualOverridden())) {
        return true;
      }
    }
    return false;
  }

  public static Optional<FormGenerationTask> createDocumentGenerationTaskWithIdentity(
      Integer userId,
      Long caseId,
      Lawyer assignedLawyer,
      DocumentType documentType,
      String caseType,
      Identify identify,
      FormGenerationTaskMapper formGenerationTaskMapper,
      DocumentMgtService documentService,
      Optional<String> metadataOptional) {
    Optional<Document> existingForm =
        documentService.getExistDocument(caseId, documentType.getName(), identify);
    Document document =
        documentService.createOrUpdateDocument(
            existingForm,
            caseId,
            userId,
            documentType,
            GenerationType.SYSTEM_AUTO_GENERATED,
            identify);
    try {
      String metadata = metadataOptional.isPresent() ? metadataOptional.get() : null;
      FormGenerationTask task =
          FormGenerationTask.builder()
              .userId(userId)
              .caseId(caseId)
              .documentId(document.getId())
              .status(TaskStatus.IN_PROGRESS.getValue())
              .formName(documentType.getName())
              .lawyerId(assignedLawyer.getId())
              .createdAt(System.currentTimeMillis())
              .caseType(caseType)
              .metadata(metadata)
              .build();
      formGenerationTaskMapper.create(task);
      return Optional.of(task);
    } catch (Exception exp) {
      log.error(
          String.format("fail to create %s document for caseID %s", documentType, caseId), exp);
      documentService.updateDocumentWithFailure(document, exp.getMessage());
      return Optional.empty();
    }
  }
  /**
   * This class is for building common methods that can be shared by different case type document
   * generation
   */
  public static Optional<FormGenerationTask> createDocumentGenerationTask(
      Integer userId,
      Long caseId,
      Lawyer assignedLawyer,
      DocumentType documentType,
      String caseType,
      FormGenerationTaskMapper formGenerationTaskMapper,
      DocumentMgtService documentService,
      Optional<String> metadataOptional) {
    return createDocumentGenerationTaskWithIdentity(
        userId,
        caseId,
        assignedLawyer,
        documentType,
        caseType,
        Identify.Applicant,
        formGenerationTaskMapper,
        documentService,
        metadataOptional);
  }

  public static Optional<FormGenerationTask> createFormGenerationTaskWithSupplement(
      Integer userId,
      Long caseId,
      DocumentType documentType,
      DocumentType supplementDocumentType,
      CaseType caseType,
      Lawyer assignedLawyer,
      FormGenerationTaskMapper formGenerationTaskMapper,
      DocumentMgtService documentService) {

    Optional<FormGenerationTask> documentGenerationTask =
        createDocumentGenerationTask(
            userId,
            caseId,
            assignedLawyer,
            documentType,
            caseType.getValue(),
            formGenerationTaskMapper,
            documentService,
            Optional.empty());

    Optional<FormGenerationTask> supplementDocumentGenerationTask =
        createDocumentGenerationTask(
            userId,
            caseId,
            assignedLawyer,
            supplementDocumentType,
            caseType.getValue(),
            formGenerationTaskMapper,
            documentService,
            Optional.empty());
    if (documentGenerationTask.isPresent() && supplementDocumentGenerationTask.isPresent()) {
      FormGenerationTask formGenerationTaskForI589 = documentGenerationTask.get();
      formGenerationTaskForI589.setMetadata(
          new Gson().toJson(supplementDocumentGenerationTask.get()));
      return Optional.of(formGenerationTaskForI589);
    } else {
      return Optional.empty();
    }
  }
}
