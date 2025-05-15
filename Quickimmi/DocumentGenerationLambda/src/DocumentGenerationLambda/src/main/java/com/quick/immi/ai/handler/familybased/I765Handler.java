

package com.quick.immi.ai.handler.familybased;

import com.google.gson.Gson;
import com.quick.immi.ai.converter.familybased.I765Converter;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.i765.I765Table;
import com.quick.immi.ai.fillform.I765FormFiller;
import com.quick.immi.ai.handler.FormHandler;
import java.util.Arrays;
import java.util.logging.Logger;

public class I765Handler extends FormHandler {
  private static final Logger log = Logger.getLogger(I765Handler.class.getName());

  private final DocumentGenerationManager documentGenerationManager;
  private final FormGenerationTask formGenerationTask;

  private I765FormFiller i765FormFiller;

  public I765Handler(DocumentGenerationManager documentGenerationManager, FormGenerationTask formGenerationTask) {
    super(documentGenerationManager, formGenerationTask);
    this.documentGenerationManager = documentGenerationManager;
    this.formGenerationTask = formGenerationTask;
  }
  @Override
  public void handle() {
    Document document = null;

    try {
      document = generateDocument();
      updateDocument(document, TaskStatus.SUCCESS);
      updateFormGenerationTaskStatus(formGenerationTask, document.getS3Location(), TaskStatus.SUCCESS);
      log.info(String.format("successfully generate i765 document for taskId %s", formGenerationTask.getId()));
    } catch (Exception exp){
      log.severe(String.format("fail to generate document for task %s", formGenerationTask) + exp.getCause() + " " + Arrays.toString(
          exp.getStackTrace()));
      updateDocument(document, TaskStatus.FAILED);
      updateFormGenerationTaskStatus(formGenerationTask, document.getS3Location(), TaskStatus.FAILED);
      throw new RuntimeException(exp);
    }
  }

  @Override
  public Document generateDocument() {
    Long caseId = formGenerationTask.getCaseId();
    ApplicationCase applicationCase = documentGenerationManager.getApplicationCase(caseId);
    String profile = applicationCase.getProfile();
    FamilyBasedCaseProfile
        familyBasedCaseProfile = new Gson().fromJson(profile, FamilyBasedCaseProfile.class);
    Lawyer lawyer = documentGenerationManager.getLawyer(formGenerationTask.getLawyerId());
    LawyerProfile lawyerProfile = new Gson().fromJson(lawyer.getProfile(), LawyerProfile.class);
    log.info("Lawyer Profile" + lawyerProfile.toString());
    log.info("familyBasedCaseProfile " + familyBasedCaseProfile.toString());
    I765Converter i765Converter= new I765Converter();

    I765Table i765Table = i765Converter.getTable(lawyerProfile, familyBasedCaseProfile);
    log.info(i765Table.toString());
    i765FormFiller = new I765FormFiller(i765Table, formGenerationTask);
    i765FormFiller.init();
    i765FormFiller.fillDocument();

    //save main i589 document
    String s3Location = i765FormFiller.saveResult();
    Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
    document.setS3Location(s3Location);
    return document;
  }
}
