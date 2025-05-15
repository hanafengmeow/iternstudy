package com.quick.immi.ai.handler.common;

import com.google.gson.Gson;
import com.quick.immi.ai.converter.familybased.I130aConverter;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.familyBased.i130a.I130aTable;
import com.quick.immi.ai.fillform.familyBased.I130aFormFiller;
import com.quick.immi.ai.handler.FormHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I130aHandler extends FormHandler {
    private static Logger log = LoggerFactory.getLogger(I130aHandler.class);

    private final DocumentGenerationManager documentGenerationManager;
    private final FormGenerationTask formGenerationTask;
    private final FormGenerationTask formGenerationTaskSupplement;

    private I130aFormFiller i130aFormFiller;
    public I130aHandler(DocumentGenerationManager documentGenerationManager, FormGenerationTask formGenerationTask) {
        super(documentGenerationManager, formGenerationTask);
        this.documentGenerationManager = documentGenerationManager;
        this.formGenerationTask = formGenerationTask;
        this.formGenerationTaskSupplement = new Gson().fromJson(formGenerationTask.getMetadata(), FormGenerationTask.class);
    }

    @Override
    public void handle() {
        Document document = null;
        Document supplementDocument = null;

        try {
            document = generateDocument();
            updateDocument(document, TaskStatus.SUCCESS);
            updateFormGenerationTaskStatus(formGenerationTask, document.getS3Location(), TaskStatus.SUCCESS);
            //must generate the SupplementDocument after the i130a main form
            log.info(String.format("successfully generate i130a document for taskId %s", formGenerationTask.getId()));
            supplementDocument = generateSupplementDocument();
            TaskStatus taskStatus = supplementDocument.getS3Location() == null ? TaskStatus.SKIPPED : TaskStatus.SUCCESS;
            updateDocument(supplementDocument, taskStatus);
            log.info(String.format("successfully generate i130a supplement with status %s for i130a taskId %s", taskStatus.getValue(),
                    formGenerationTask.getId()));
        } catch (Exception exp){
            log.error(String.format("fail to generate document for task %s", formGenerationTask), exp);
            updateDocument(document, TaskStatus.FAILED);
            updateDocument(supplementDocument, TaskStatus.FAILED);
            updateFormGenerationTaskStatus(formGenerationTask, document.getS3Location(), TaskStatus.FAILED);
            updateFormGenerationTaskStatus(formGenerationTaskSupplement, document.getS3Location(), TaskStatus.FAILED);
            throw new RuntimeException(exp);
        }
    }

    @Override
    public Document generateDocument() {
        Long caseId = formGenerationTask.getCaseId();
        ApplicationCase applicationCase = documentGenerationManager.getApplicationCase(caseId);
        FamilyBasedCaseProfile familyBasedCaseProfile = new Gson().fromJson(applicationCase.getProfile(), FamilyBasedCaseProfile.class);
        Lawyer lawyer = documentGenerationManager.getLawyer(formGenerationTask.getLawyerId());
        LawyerProfile lawyerProfile = new Gson().fromJson(lawyer.getProfile(), LawyerProfile.class);

        I130aConverter i130aTableConverter = new I130aConverter();

        I130aTable i130aTable = i130aTableConverter.getI130aTable(lawyerProfile, familyBasedCaseProfile);

        i130aFormFiller  = new I130aFormFiller(i130aTable, formGenerationTask);
        i130aFormFiller.init();
        i130aFormFiller.fillDocument();

        //save main i130a document
        String s3Location = i130aFormFiller.saveResult();
        Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
        document.setS3Location(s3Location);
        return document;
    }

    //make sure generateSupplementDocument is after generateDocument()
    public Document generateSupplementDocument() {
        String metadata = formGenerationTask.getMetadata();
        FormGenerationTask supplementFormGenerationTask = new Gson().fromJson(metadata, FormGenerationTask.class);

        String s3SupplementLocation = i130aFormFiller.saveSupplementResult();
        Document document = documentGenerationManager.getDocument(supplementFormGenerationTask.getDocumentId());
        document.setS3Location(s3SupplementLocation);
        return document;
    }
}

