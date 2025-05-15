/* (C) 2024 */
package com.quick.immi.ai.utils.asylum;

import static com.quick.immi.ai.constant.Constants.SUPPORTED_AFFIRMATIVE_COVER_LETTER_DOCUMENTS;
import static com.quick.immi.ai.constant.Constants.SUPPORTED_DEFENSIVE_COVER_LETTER_DOCUMENTS;
import static com.quick.immi.ai.entity.DocumentType.*;
import static com.quick.immi.ai.utils.FormGenerationBaseUtils.createDocumentGenerationTask;
import static com.quick.immi.ai.utils.FormGenerationBaseUtils.shouldSkipFormGeneration;

import com.google.gson.Gson;
import com.quick.immi.ai.dao.FormGenerationTaskMapper;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.asylum.AsylumCaseProfile;
import com.quick.immi.ai.entity.asylum.AsylumType;
import com.quick.immi.ai.entity.asylum.EntryRecord;
import com.quick.immi.ai.entity.sqs.AsylumPleadingGenerationEvent;
import com.quick.immi.ai.entity.sqs.CoverletterGenerationEvent;
import com.quick.immi.ai.service.DocumentMgtService;
import com.quick.immi.ai.service.helper.SQSService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsylumFormGenerationTaskUtils {

  public static boolean isSupplementForm(DocumentType documentType) {
    return documentType.getName().startsWith("certificate_of_translation_for")
        || SUPPORTED_AFFIRMATIVE_COVER_LETTER_DOCUMENTS.contains(documentType)
        || SUPPORTED_DEFENSIVE_COVER_LETTER_DOCUMENTS.contains(documentType)
        || DocumentType.ASYLUM_PLEADING == documentType;
  }

  public static void buildAllFormGenerationTaskAndSendToSQS(
      Integer userId,
      Long caseId,
      boolean isMarried,
      boolean spouseIncluded,
      Integer childCount,
      List<Integer> childrenIncluded,
      AsylumType asylumType,
      AsylumCaseProfile profile,
      Lawyer assignedLawyer,
      SQSService sqsService,
      FormGenerationTaskMapper formGenerationTaskMapper,
      DocumentMgtService documentService) {

    List<DocumentType> supplementFromList = new ArrayList<>();
    List<DocumentType> mainFromList = new ArrayList<>();

    mainFromList.add(I589);

    if (asylumType == AsylumType.AFFIRMATIVE) {
      mainFromList.add(G28_FOR_APPLICANT);
      supplementFromList.addAll(SUPPORTED_AFFIRMATIVE_COVER_LETTER_DOCUMENTS);

      if (isMarried && spouseIncluded) {
        mainFromList.add(DocumentType.valueOf("G28_FOR_SPOUSE"));
      }

      if (childCount > 0) {
        for (int childIndex : childrenIncluded) {
          mainFromList.add(DocumentType.valueOf("G28_FOR_CHILD_" + childIndex));
        }
      }
    } else {
      mainFromList.add(EOIR28);
      supplementFromList.add(ASYLUM_PLEADING);
      supplementFromList.addAll(SUPPORTED_DEFENSIVE_COVER_LETTER_DOCUMENTS);
    }

    if (isMarried) {
      supplementFromList.add(DocumentType.CERTIFICATE_OF_TRANSLATION_FOR_MARRIAGE_CERTIFICATE);
      supplementFromList.add(DocumentType.MARRIAGE_CERTIFICATE_ENGLISH);
    }

    for (DocumentType mainForm : mainFromList) {
      buildMainFormGenerationTaskAndSendToSQS(
          userId,
          caseId,
          mainForm,
          assignedLawyer,
          sqsService,
          formGenerationTaskMapper,
          documentService,
          true);
    }

    for (DocumentType supplementForm : supplementFromList) {
      buildSupplementFormGenerationTaskAndSendToSQS(
          userId,
          caseId,
          supplementForm,
          profile,
          assignedLawyer,
          sqsService,
          formGenerationTaskMapper,
          documentService,
          true);
    }
  }

  public static void buildMainFormGenerationTaskAndSendToSQS(
      Integer userId,
      Long caseId,
      DocumentType documentType,
      Lawyer assignedLawyer,
      SQSService sqsService,
      FormGenerationTaskMapper formGenerationTaskMapper,
      DocumentMgtService documentService,
      boolean checkManualOverrideFlag) {
    if (shouldSkipFormGeneration(checkManualOverrideFlag, documentService, caseId, documentType))
      return;
    Optional<FormGenerationTask> task;
    if (documentType == DocumentType.I589) {
      task =
          create589FormGenerationTask(
              userId, caseId, assignedLawyer, formGenerationTaskMapper, documentService);
    } else {
      task =
          createDocumentGenerationTask(
              userId,
              caseId,
              assignedLawyer,
              documentType,
              CaseType.Asylum.getValue(),
              formGenerationTaskMapper,
              documentService,
              Optional.empty());
    }
    if (task.isPresent()) {
      sqsService.sendMessageToJavaLambda(task.get());
    }
  }

  public static void buildSupplementFormGenerationTaskAndSendToSQS(
      Integer userId,
      Long caseId,
      DocumentType documentType,
      AsylumCaseProfile profile,
      Lawyer assignedLawyer,
      SQSService sqsService,
      FormGenerationTaskMapper formGenerationTaskMapper,
      DocumentMgtService documentService,
      boolean checkManualOverrideFlag) {

    if (shouldSkipFormGeneration(checkManualOverrideFlag, documentService, caseId, documentType))
      return;

    Optional<FormGenerationTask> task;
    if (documentType == ASYLUM_PLEADING) {
      task =
          generateAsylumPleadingTask(
              userId,
              caseId,
              profile,
              assignedLawyer,
              documentType.getName(),
              formGenerationTaskMapper,
              documentService);
    } else {
      task =
          generateCoverLetterTask(
              userId,
              caseId,
              profile,
              assignedLawyer,
              documentType,
              formGenerationTaskMapper,
              documentService);
    }
    if (task.isPresent()) {
      sqsService.sendMessageToPythonLambda(task.get());
    }
  }

  private static Optional<FormGenerationTask> create589FormGenerationTask(
      Integer userId,
      Long caseId,
      Lawyer assignedLawyer,
      FormGenerationTaskMapper formGenerationTaskMapper,
      DocumentMgtService documentService) {

    Optional<FormGenerationTask> i589Task =
        createDocumentGenerationTask(
            userId,
            caseId,
            assignedLawyer,
            I589,
            CaseType.Asylum.getValue(),
            formGenerationTaskMapper,
            documentService,
            Optional.empty());

    Optional<FormGenerationTask> i589SupplementTask =
        createDocumentGenerationTask(
            userId,
            caseId,
            assignedLawyer,
            I589_SUPPLEMENT,
            CaseType.Asylum.getValue(),
            formGenerationTaskMapper,
            documentService,
            Optional.empty());
    if (i589Task.isPresent() && i589SupplementTask.isPresent()) {
      FormGenerationTask formGenerationTaskForI589 = i589Task.get();
      formGenerationTaskForI589.setMetadata(new Gson().toJson(i589SupplementTask.get()));
      return Optional.of(formGenerationTaskForI589);
    } else {
      return Optional.empty();
    }
  }

  public static Optional<FormGenerationTask> generateCoverLetterTask(
      Integer userId,
      Long caseId,
      AsylumCaseProfile profile,
      Lawyer assignedLawyer,
      DocumentType documentType,
      FormGenerationTaskMapper formGenerationTaskMapper,
      DocumentMgtService documentService) {
    Optional<Document> existingForm =
        documentService.getExistDocument(caseId, documentType.getName(), Identify.Applicant);
    Document document =
        documentService.createOrUpdateDocument(
            existingForm,
            caseId,
            userId,
            documentType,
            GenerationType.SYSTEM_AUTO_GENERATED,
            Identify.Applicant);
    try {
      CoverletterGenerationEvent coverLetterGenerationTask =
          AsylumCoverLetterUtils.createCoverLetterGenerationEvent(
              userId, caseId, profile, assignedLawyer, documentType.getName());

      FormGenerationTask coverLetterFormGenerationTask =
          FormGenerationTask.builder()
              .userId(userId)
              .caseId(caseId)
              .documentId(document.getId())
              .status(TaskStatus.IN_PROGRESS.getValue())
              .formName(documentType.getName())
              .lawyerId(assignedLawyer.getId())
              .metadata(new Gson().toJson(coverLetterGenerationTask))
              .createdAt(System.currentTimeMillis())
              .build();

      formGenerationTaskMapper.create(coverLetterFormGenerationTask);
      return Optional.of(coverLetterFormGenerationTask);
    } catch (Exception exp) {
      log.error(
          String.format(
              "fail to create FormGenerationTask for %s  for caseID %s", documentType, caseId),
          exp);
      documentService.updateDocumentWithFailure(document, exp.getMessage());
      return Optional.empty();
    }
  }

  private static Optional<FormGenerationTask> generateAsylumPleadingTask(
      Integer userId,
      Long caseId,
      AsylumCaseProfile profile,
      Lawyer assignedLawyer,
      String documentType,
      FormGenerationTaskMapper formGenerationTaskMapper,
      DocumentMgtService documentService) {
    Optional<Document> existingForm =
        documentService.getExistDocument(caseId, documentType, Identify.Applicant);
    Document document =
        documentService.createOrUpdateDocument(
            existingForm,
            caseId,
            userId,
            DocumentType.fromName(documentType),
            GenerationType.SYSTEM_AUTO_GENERATED,
            Identify.Applicant);
    try {
      List<EntryRecord> entryRecords = profile.getApplicant().getEntryRecords();
      if (entryRecords == null || entryRecords.isEmpty()) {
        log.error("missing entry record information.... fail to generateAsylumPleadingTask");
        documentService.markDocumentAsFailed(Optional.of(document));
        return Optional.empty();
      }
      EntryRecord entryRecord = entryRecords.get(0);
      String mostRecentEntryDate = entryRecord.getDate();
      String mostRecentEntryCity = entryRecord.getCity();
      String mostRecentEntryState = entryRecord.getState();

      AsylumPleadingGenerationEvent asylumPleadingGenerationEvent =
          AsylumPleadingGenerationEvent.builder()
              .caseId(String.valueOf(caseId))
              .userId(String.valueOf(userId))
              .attorney(
                  AsylumPleadingGenerationEvent.Attorney.builder()
                      .firstName(assignedLawyer.getFirstName())
                      .lastName(assignedLawyer.getLastName())
                      .build())
              .client(
                  AsylumPleadingGenerationEvent.Client.builder()
                      .firstName(profile.getApplicant().getFirstName())
                      .lastName(profile.getApplicant().getLastName())
                      .middleName(profile.getApplicant().getMiddleName())
                      .noticeOfAppearDate(
                          profile
                              .getSupplementDocument()
                              .getMasterHearingDetail()
                              .getNoticeOfAppearDate())
                      .presentNationality(profile.getApplicant().getNationality())
                      .nativeLanguage(profile.getApplicant().getNativeLanguage())
                      .mostRecentEntryDate(mostRecentEntryDate)
                      .mostRecentEntryCity(mostRecentEntryCity)
                      .mostRecentEntryState(mostRecentEntryState)
                      .build())
              .documentType(documentType)
              .documentName(documentType)
              .build();

      FormGenerationTask formGenerationTask =
          FormGenerationTask.builder()
              .userId(userId)
              .caseId(caseId)
              .documentId(document.getId())
              .status(TaskStatus.IN_PROGRESS.getValue())
              .formName(documentType)
              .lawyerId(assignedLawyer.getId())
              .metadata(new Gson().toJson(asylumPleadingGenerationEvent))
              .createdAt(System.currentTimeMillis())
              .build();

      formGenerationTaskMapper.create(formGenerationTask);
      return Optional.of(formGenerationTask);
    } catch (Exception exp) {
      log.error(
          String.format("fail to create %s document for caseID %s", documentType, caseId), exp);
      documentService.updateDocumentWithFailure(document, exp.getMessage());
      return Optional.empty();
    }
  }

  public static Long generateCrawlerTask(
      Integer userId,
      Long caseId,
      String aNumber,
      String crawlerType,
      SQSService sqsService,
      FormGenerationTaskMapper formGenerationTaskMapper) {

    CrawlerEvent crawlerEvent = CrawlerEvent.builder().a_number(aNumber).build();

    FormGenerationTask task =
        FormGenerationTask.builder()
            .userId(userId)
            .caseId(caseId)
            .status(TaskStatus.IN_PROGRESS.getValue())
            .formName(crawlerType)
            .metadata(new Gson().toJson(crawlerEvent))
            .createdAt(System.currentTimeMillis())
            .build();

    formGenerationTaskMapper.create(task);
    sqsService.sendMessageToPythonCrawlerLambda(task);
    return task.getId();
  }
}
