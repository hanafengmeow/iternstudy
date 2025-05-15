package com.quick.immi.ai.handler.asylum;

import com.google.gson.Gson;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.cot.COTTable;
import com.quick.immi.ai.entity.cot.COTTableConverter;
import com.quick.immi.ai.fillform.COTFormFiller;
import com.quick.immi.ai.handler.FormHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class COTHandler extends FormHandler {

  private final DocumentGenerationManager documentGenerationManager;
  private final FormGenerationTask formGenerationTask;

  public COTHandler(DocumentGenerationManager documentGenerationManager,
                    FormGenerationTask formGenerationTask) {
    super(documentGenerationManager, formGenerationTask);
    this.documentGenerationManager = documentGenerationManager;
    this.formGenerationTask = formGenerationTask;
  }

  @Override
  public Document generateDocument() {
    Lawyer lawyer = documentGenerationManager.getLawyer(formGenerationTask.getLawyerId());
    String documentName = formGenerationTask.getDocumentName();
    LawyerProfile lawyerProfile = new Gson().fromJson(lawyer.getProfile(), LawyerProfile.class);

    COTTableConverter cotTableConverter = new COTTableConverter();
    COTTable cotTable = cotTableConverter.getCOTTable(lawyerProfile, documentName);

    COTFormFiller cotFormFiller = new COTFormFiller(cotTable, formGenerationTask);
    cotFormFiller.init();
    cotFormFiller.fillDocument();
    String s3Location = cotFormFiller.saveResult();
    Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
    document.setS3Location(s3Location);
    return document;
  }
}
