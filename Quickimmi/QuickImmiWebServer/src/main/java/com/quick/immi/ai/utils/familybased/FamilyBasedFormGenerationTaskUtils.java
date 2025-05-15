/* (C) 2024 */
package com.quick.immi.ai.utils.familybased;

import static com.quick.immi.ai.utils.FormGenerationBaseUtils.*;

import com.google.gson.Gson;
import com.quick.immi.ai.dao.FormGenerationTaskMapper;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.familybased.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.familybased.I864Metadata;
import com.quick.immi.ai.entity.familybased.Sponsor;
import com.quick.immi.ai.service.DocumentMgtService;
import com.quick.immi.ai.service.helper.SQSService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;

@Slf4j
public class FamilyBasedFormGenerationTaskUtils {

  public static List<DocumentType> MAIN_FROM_SET =
      List.of(
          DocumentType.I130,
          DocumentType.I130a,
          DocumentType.G28_FOR_BENEFICIARY,
          DocumentType.G28_FOR_PETITIONER,
          DocumentType.I131,
          DocumentType.I864,
          DocumentType.I485,
          DocumentType.I765);

  public static List<DocumentType> MAIN_FROM_SET_WITHOUT_SUPPLEMENT =
      List.of(DocumentType.I864, DocumentType.G28, DocumentType.I131, DocumentType.I765);

  public static List<DocumentType> MAIN_FROM_SET_WITH_SUPPLEMENT =
      List.of(DocumentType.I130, DocumentType.I130a, DocumentType.I485);

  public static List<Pair<DocumentType, DocumentType>> MAIN_FROM_TO_SUPPLEMENT_FORM_PAIR_LIST =
      List.of(
          Pair.of(DocumentType.I130, DocumentType.I130_SUPPLEMENT),
          Pair.of(DocumentType.I130a, DocumentType.I130a_SUPPLEMENT),
          Pair.of(DocumentType.I485, DocumentType.I485_SUPPLEMENT));

  public static void buildMainFormGenerationTaskAndSendToSQS(
      Integer userId,
      Long caseId,
      DocumentType documentType,
      Lawyer assignedLawyer,
      FamilyBasedCaseProfile profile,
      SQSService sqsService,
      FormGenerationTaskMapper formGenerationTaskMapper,
      DocumentMgtService documentService,
      boolean checkManualOverrideFlag) {
    if (shouldSkipFormGeneration(checkManualOverrideFlag, documentService, caseId, documentType))
      return;
    if (documentType == DocumentType.I864) {
      List<Sponsor> sponsorList = profile.getSponsorList();
      if (sponsorList != null && !sponsorList.isEmpty()) {
        for (int i = 0; i < sponsorList.size(); i++) {
          String metadata = getSponsorMetadata(sponsorList, i);
          Optional<FormGenerationTask> task =
              createDocumentGenerationTaskWithIdentity(
                  userId,
                  caseId,
                  assignedLawyer,
                  documentType,
                  CaseType.FamilyBased.getValue(),
                  Identify.getSponsor(i),
                  formGenerationTaskMapper,
                  documentService,
                  Optional.of(metadata));
          if (task.isPresent()) {
            sqsService.sendMessageToJavaLambda(task.get());
          }
        }
      } else {
        log.warn("sponsor is empty, skipping i864 generation");
      }
    } else if (MAIN_FROM_SET_WITH_SUPPLEMENT.contains(documentType)) {
      Optional<Pair<DocumentType, DocumentType>> first =
          MAIN_FROM_TO_SUPPLEMENT_FORM_PAIR_LIST.stream()
              .filter(p -> p.getFirst() == documentType)
              .findFirst();
      Pair<DocumentType, DocumentType> documentTypeDocumentTypePair = first.get();

      Optional<FormGenerationTask> formGenerationTaskWithSupplement =
          createFormGenerationTaskWithSupplement(
              userId,
              caseId,
              documentTypeDocumentTypePair.getFirst(),
              documentTypeDocumentTypePair.getSecond(),
              CaseType.FamilyBased,
              assignedLawyer,
              formGenerationTaskMapper,
              documentService);
      if (formGenerationTaskWithSupplement.isPresent()) {
        sqsService.sendMessageToJavaLambda(formGenerationTaskWithSupplement.get());
      }
    } else {
      Optional<String> g28FormMetadata = getG28FormMetadata(profile, documentType);
      Optional<FormGenerationTask> task =
          createDocumentGenerationTask(
              userId,
              caseId,
              assignedLawyer,
              documentType,
              CaseType.FamilyBased.getValue(),
              formGenerationTaskMapper,
              documentService,
              g28FormMetadata);
      if (task.isPresent()) {
        sqsService.sendMessageToJavaLambda(task.get());
      }
    }
  }

  private static Optional<String> getG28FormMetadata(
      FamilyBasedCaseProfile caseProfile, DocumentType documentType) {
    Gson gson = new Gson();
    if (documentType == DocumentType.G28_FOR_BENEFICIARY) {
      return Optional.of(
          gson.toJson(
              G28FormMetadata.builder().mainForm("").identify(Identify.Beneficiary).build()));
    } else if (documentType == DocumentType.G28_FOR_PETITIONER) {
      return Optional.of(
          gson.toJson(
              G28FormMetadata.builder().mainForm("").identify(Identify.Petitioner).build()));
    } else if (documentType == DocumentType.G28_FOR_JOINT_SPONSOR) {
      return Optional.of(
          gson.toJson(
              G28FormMetadata.builder()
                  .mainForm("I864")
                  .identify(Identify.Petitioner)
                  .sponsorIndex(getJointSponsorIndex(caseProfile).get())
                  .build()));
    } else {
      return Optional.empty();
    }
  }

  private static String getSponsorMetadata(List<Sponsor> sponsorList, int index) {
    I864Metadata i864Metadata = new I864Metadata();
    Sponsor sponsor = sponsorList.get(index);
    i864Metadata.setIndex(index);
    i864Metadata.setSponsorFirstName(sponsor.getSponsorDetails().getFirstName());
    i864Metadata.setSponsorFirstName(sponsor.getSponsorDetails().getLastName());
    String metadata = new Gson().toJson(i864Metadata);
    return metadata;
  }

  public static void buildAllFormGenerationTaskAndSendToSQS(
      Integer userId,
      Long caseId,
      FamilyBasedCaseProfile profile,
      Lawyer assignedLawyer,
      SQSService sqsService,
      FormGenerationTaskMapper formGenerationTaskMapper,
      DocumentMgtService documentService) {

    for (Pair<DocumentType, DocumentType> pair : MAIN_FROM_TO_SUPPLEMENT_FORM_PAIR_LIST) {
      Optional<FormGenerationTask> formGenerationTaskWithSupplement =
          createFormGenerationTaskWithSupplement(
              userId,
              caseId,
              pair.getFirst(),
              pair.getSecond(),
              CaseType.FamilyBased,
              assignedLawyer,
              formGenerationTaskMapper,
              documentService);
      if (formGenerationTaskWithSupplement.isPresent()) {
        sqsService.sendMessageToJavaLambda(formGenerationTaskWithSupplement.get());
      }
    }

    Optional<Integer> jointSponsorIndex = getJointSponsorIndex(profile);

    if (jointSponsorIndex.isPresent()) {
      MAIN_FROM_SET_WITHOUT_SUPPLEMENT.add(DocumentType.G28_FOR_JOINT_SPONSOR);
    }

    for (DocumentType mainForm : MAIN_FROM_SET_WITHOUT_SUPPLEMENT) {
      buildMainFormGenerationTaskAndSendToSQS(
          userId,
          caseId,
          mainForm,
          assignedLawyer,
          profile,
          sqsService,
          formGenerationTaskMapper,
          documentService,
          true);
    }
  }

  private static Optional<Integer> getJointSponsorIndex(FamilyBasedCaseProfile profile) {
    List<Sponsor> sponsorList = profile.getSponsorList();
    if (sponsorList != null && !sponsorList.isEmpty()) {
      for (int i = 0; i < sponsorList.size(); i++) {
        Sponsor sponsor = sponsorList.get(i);
        if (sponsor != null
            && sponsor.getBasicInfo() != null
            && sponsor.getBasicInfo().isFirstJointSponsorCheckbox()) {
          return Optional.of(i);
        }
      }
    }
    return Optional.empty();
  }
}
