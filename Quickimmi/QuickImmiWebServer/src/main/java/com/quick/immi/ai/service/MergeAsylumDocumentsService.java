/* (C) 2024 */
package com.quick.immi.ai.service;

import com.google.gson.Gson;
import com.quick.immi.ai.dao.DocumentMapper;
import com.quick.immi.ai.dao.FormGenerationTaskMapper;
import com.quick.immi.ai.dao.LawyerMapper;
import com.quick.immi.ai.dto.common.MergePdfsDto;
import com.quick.immi.ai.dto.response.GenerateDocumentResultDto;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.asylum.AsylumCaseProfile;
import com.quick.immi.ai.entity.asylum.AsylumType;
import com.quick.immi.ai.service.helper.EntityCacheService;
import com.quick.immi.ai.service.helper.OpenAIService;
import com.quick.immi.ai.service.helper.S3Service;
import com.quick.immi.ai.service.helper.SQSService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MergeAsylumDocumentsService {

  private final AsylumCaseMgtService asylumCaseMgtService;
  private final FormGenerationTaskMapper formGenerationTaskMapper;
  private final DocumentMapper documentMapper;
  private final DocumentMgtService documentService;
  private final SQSService sqsService;
  private final TaskService taskService;
  private final EntityCacheService cacheService;
  private final LawyerMapper lawyerMapper;
  private final S3Service s3Service;
  private final OpenAIService openAIService;

  public MergeAsylumDocumentsService(
      AsylumCaseMgtService asylumCaseMgtService,
      FormGenerationTaskMapper formGenerationTaskMapper,
      DocumentMapper documentMapper,
      DocumentMgtService documentService,
      SQSService sqsService,
      TaskService taskService,
      EntityCacheService cacheService,
      LawyerMapper lawyerMapper,
      S3Service s3Service,
      OpenAIService openAIService) {
    this.asylumCaseMgtService = asylumCaseMgtService;
    this.formGenerationTaskMapper = formGenerationTaskMapper;
    this.documentMapper = documentMapper;
    this.documentService = documentService;
    this.sqsService = sqsService;
    this.taskService = taskService;
    this.cacheService = cacheService;
    this.lawyerMapper = lawyerMapper;
    this.s3Service = s3Service;
    this.openAIService = openAIService;
  }

  public Long mergePDFs(Long caseId, MergePdfsDto mergePdfsDto) {

    ApplicationCase applicationCase = asylumCaseMgtService.get(caseId);

    List<String> s3Locations = new ArrayList<>();
    for (Long documentId : mergePdfsDto.getDocumentIds()) {
      if (documentId == null) {
        continue;
      }
      Document document = documentMapper.get(documentId);
      if (document.getS3Location() != null && !document.getS3Location().isEmpty()) {
        s3Locations.add(document.getS3Location());
      }
    }

    mergePdfsDto.setS3Locations(s3Locations);
    Optional<Document> existingMerged =
        documentService.getExistDocument(caseId, mergePdfsDto.getName(), Identify.Applicant);
    System.out.println("existingMerged: " + existingMerged);
    Long documentId =
        documentService.createOrUpdateDocumentWithNewGeneration(
            existingMerged,
            caseId,
            applicationCase.getUserId(),
            DocumentType.fromName(mergePdfsDto.getName()),
            GenerationType.SYSTEM_MERGED);

    FormGenerationTask MergeFormGenerationTask =
        FormGenerationTask.builder()
            .userId(applicationCase.getUserId())
            .caseId(caseId)
            .documentId(documentId)
            .status(TaskStatus.IN_PROGRESS.getValue())
            .formName("merge")
            .metadata(new Gson().toJson(mergePdfsDto))
            .createdAt(System.currentTimeMillis())
            .build();

    formGenerationTaskMapper.create(MergeFormGenerationTask);
    sqsService.sendMessageToJavaLambda(MergeFormGenerationTask);
    return MergeFormGenerationTask.getId();
  }

  public List<GenerateDocumentResultDto> defaultMerge(Long caseId) {
    ApplicationCase applicationCase = asylumCaseMgtService.get(caseId);
    AsylumType asylumType = AsylumType.fromValue(applicationCase.getSubType());
    AsylumCaseProfile profile =
        new Gson().fromJson(applicationCase.getProfile(), AsylumCaseProfile.class);

    //    Boolean married =
    // !applicationCaseDto.getProfile().getFamily().getSpouse().isNotMarriedCheckbox();
    Boolean married = !profile.getApplicant().isNotMarriedCheckbox();
    Boolean hasChildren = !profile.getFamily().getChildren().isEmpty();

    List<GenerateDocumentResultDto> result = new ArrayList<>();

    if (asylumType == null) {
      throw new RuntimeException("fail to get asylum type!!!");
    } else if (asylumType == AsylumType.AFFIRMATIVE) {
      result.add(mergeByStrategy(caseId, MergeStrategy.MERGED_DOCUMENT_FOR_AFFIRMATIVE_ASYLUM));
    } else if (asylumType == AsylumType.DEFENSIVE) {
      result.add(mergeByStrategy(caseId, MergeStrategy.MERGED_I589_FOR_DEFENSIVE_ASYLUM));
      result.add(
          mergeByStrategy(caseId, MergeStrategy.MERGED_PERSONAL_STATEMENT_FOR_DEFENSIVE_ASYLUM));
      result.add(
          mergeByStrategy(caseId, MergeStrategy.MERGED_WRITTEN_PLEADING_FOR_DEFENSIVE_ASYLUM));
      result.add(
          mergeByStrategy(caseId, MergeStrategy.MERGED_SUPPORTING_DOCUMENTS_FOR_DEFENSIVE_ASYLUM));
    }

    result.add(mergeByStrategy(caseId, MergeStrategy.MERGED_PASSPORT_FOR_APPLICANT));

    if (married) {
      result.add(mergeByStrategy(caseId, MergeStrategy.MERGED_PASSPORT_FOR_SPOUSE));
    }

    if (hasChildren) {
      int childCount = profile.getFamily().getChildren().size();
      for (int i = 1; i <= childCount; i++) {
        result.add(
            mergeByStrategy(caseId, MergeStrategy.valueOf("MERGED_PASSPORT_FOR_CHILD_" + i)));
      }
    }

    return result;

    //    mergeByStrategy(caseId, MergeStrategy.AFFIRMATIVE);
    //    mergeByStrategy(caseId, MergeStrategy.DEFENSIVE_I589);
    //    mergeByStrategy(caseId, MergeStrategy.DEFENSIVE_PERSONAL_STATEMENT);
    //    mergeByStrategy(caseId, MergeStrategy.DEFENSIVE_WRITTEN_PLEADING);
    //    mergeByStrategy(caseId, MergeStrategy.DEFENSIVE_SUPPORTING_DOCUMENTS);
    //        mergeI589(caseId);
    //        mergePersonalStatement(caseId);
    //        mergeWrittenPleading(caseId);
    //        mergeSupportingDocuments(caseId);
  }

  public GenerateDocumentResultDto mergeByStrategy(Long caseId, MergeStrategy mergeStrategy) {
    List<Long> documentIds = new ArrayList<>();
    if (!mergeStrategy.getName().startsWith("merged_passport")) {
      for (String documentType : mergeStrategy.getDocumentTypes()) {
        if (documentType.equals(DocumentType.SUPPORTING_DOCUMENT.getName())) {
          Optional<List<Document>> supportingDocuments =
              documentService.getMultipleExistDocument(caseId, documentType, Identify.Applicant);
          if (supportingDocuments.isEmpty()) {
            System.out.println(
                "when merge, fail to get document for document Type: " + documentType);
          } else {
            for (Document document : supportingDocuments.get()) {
              if (document.getId() != null) {
                documentIds.add(document.getId());
              }
            }
          }
        } else {
          Optional<Document> documentOptional =
              documentService.getExistDocument(caseId, documentType, Identify.Applicant);
          Long documentId = null;
          if (documentOptional.isEmpty()) {
            System.out.println(
                "when merge, fail to get document for document Type: " + documentType);
            continue;
          } else {
            documentId = documentOptional.get().getId();
          }
          if (documentId == null) {
            continue;
          }
          documentIds.add(documentId);
        }
      }
    } else {
      Identify identify = Identify.fromValue(mergeStrategy.getDocumentTypes().get(0));
      Optional<Document> existingCoverLetter =
          documentService.getExistDocument(
              caseId,
              DocumentType.EOIR_COVERLETTER_FOR_SUPPORTING_DOCUMENTS.getName(),
              Identify.Applicant);
      Optional<Document> existingPassport =
          documentService.getExistDocument(caseId, DocumentType.PASSPORT_MAIN.getName(), identify);
      Optional<Document> existingPassportStampPage =
          documentService.getExistDocument(caseId, DocumentType.PASSPORT_MAIN.getName(), identify);
      Optional<Document> existingPOS =
          documentService.getExistDocument(
              caseId,
              DocumentType.EOIR_PROOFOFSERVICE_FOR_SUPPORTING_DOCUMENTS.getName(),
              Identify.Applicant);
      existingCoverLetter.ifPresent(document -> documentIds.add(document.getId()));
      existingPassport.ifPresent(document -> documentIds.add(document.getId()));
      existingPassportStampPage.ifPresent(document -> documentIds.add(document.getId()));
      existingPOS.ifPresent(document -> documentIds.add(document.getId()));
    }
    MergePdfsDto mergePdfsDto =
        MergePdfsDto.builder().documentIds(documentIds).name(mergeStrategy.getName()).build();
    List<GenerateDocumentResultDto> result = new ArrayList<>();
    Long taskId = mergePDFs(caseId, mergePdfsDto);
    return new GenerateDocumentResultDto(taskId, DocumentType.fromName(mergeStrategy.getName()));
  }

  public void mergeI589(Long caseId) {
    List<Long> documentIds = new ArrayList<>();
    Long coverLetterId =
        documentService
            .getExistDocument(caseId, "eoir_coverletter_i-589_form", Identify.Applicant)
            .get()
            .getId();
    Long i589Id =
        documentService
            .getExistDocument(caseId, FormType.I589.getName(), Identify.Applicant)
            .get()
            .getId();
    Long proofOfServiceId =
        documentService
            .getExistDocument(caseId, "eoir_proofofservice_i-589_form", Identify.Applicant)
            .get()
            .getId();
    documentIds.add(coverLetterId);
    documentIds.add(i589Id);
    documentIds.add(proofOfServiceId);
    System.out.println("documentIds: " + documentIds);
    MergePdfsDto mergePdfsDto =
        MergePdfsDto.builder().documentIds(documentIds).name("merged_i-589_form").build();
    mergePDFs(caseId, mergePdfsDto);
  }

  public void mergePersonalStatement(Long caseId) {
    List<Long> documentIds = new ArrayList<>();
    Long coverLetterId =
        documentService
            .getExistDocument(caseId, "eoir_coverletter_personal_statement", Identify.Applicant)
            .get()
            .getId();
    Long chinesePersonalStatementId =
        documentService
            .getExistDocument(caseId, "personal_statement_chinese_pdf", Identify.Applicant)
            .get()
            .getId();
    Long englishPersonalStatementId =
        documentService
            .getExistDocument(caseId, "personal_statement_english_pdf", Identify.Applicant)
            .get()
            .getId();
    Long cotId = documentService.getExistDocument(caseId, "cot", Identify.Applicant).get().getId();
    Long proofOfServiceId =
        documentService
            .getExistDocument(caseId, "eoir_proofofservice_personal_statement", Identify.Applicant)
            .get()
            .getId();
    documentIds.add(coverLetterId);
    documentIds.add(chinesePersonalStatementId);
    documentIds.add(englishPersonalStatementId);
    documentIds.add(cotId);
    documentIds.add(proofOfServiceId);
    MergePdfsDto mergePdfsDto = MergePdfsDto.builder().documentIds(documentIds).build();
    mergePDFs(caseId, mergePdfsDto);
  }
}
