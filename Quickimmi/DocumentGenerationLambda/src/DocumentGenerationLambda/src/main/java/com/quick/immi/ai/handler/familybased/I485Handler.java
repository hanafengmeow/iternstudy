package com.quick.immi.ai.handler.familybased;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.quick.immi.ai.converter.familybased.I485Converter;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.i485.I485Table;
import com.quick.immi.ai.fillform.I485.I485FormFiller;
import com.quick.immi.ai.handler.FormHandler;

public class I485Handler extends FormHandler {

    private static final Logger log = Logger.getLogger(I130Handler.class.getName());

    private final DocumentGenerationManager documentGenerationManager;
    private final FormGenerationTask formGenerationTask;

    private I485FormFiller i485FormFiller;
    private final FormGenerationTask formGenerationTaskSupplement;

    public I485Handler(DocumentGenerationManager documentGenerationManager, FormGenerationTask formGenerationTask) {
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
            //must generate the SupplementDocument after the 589 main form
            log.info(String.format("successfully generate i485 document for taskId %s", formGenerationTask.getId()));
            supplementDocument = generateSupplementDocument();
            TaskStatus taskStatus = supplementDocument.getS3Location() == null ? TaskStatus.SKIPPED : TaskStatus.SUCCESS;
            updateDocument(supplementDocument, taskStatus);
            log.info(String.format("successfully generate i485 supplement with status %s for i485 taskId %s", taskStatus.getValue(),
                    formGenerationTask.getId()));
        } catch (Exception exp){
            log.severe(String.format("fail to generate document for task %s", formGenerationTask) + exp.getCause());
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
        String profile = applicationCase.getProfile();
        FamilyBasedCaseProfile familyBasedCaseProfile = new Gson().fromJson(profile, FamilyBasedCaseProfile.class);
        Lawyer lawyer = documentGenerationManager.getLawyer(formGenerationTask.getLawyerId());
        LawyerProfile lawyerProfile = new Gson().fromJson(lawyer.getProfile(), LawyerProfile.class);

        I485Converter i485Converter = new I485Converter();
        I485Table i485Table = i485Converter.getI485Table(familyBasedCaseProfile, lawyerProfile);
        i485FormFiller = new I485FormFiller(i485Table, formGenerationTask);
        i485FormFiller.init();
        i485FormFiller.fillDocument();

        String s3Location = i485FormFiller.saveResult();
        Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
        document.setS3Location(s3Location);
        return document;
    }

    public Document generateSupplementDocument() {
        String s3SupplementLocation = i485FormFiller.saveSupplementResult();
        Document document = documentGenerationManager.getDocument(formGenerationTaskSupplement.getDocumentId());
        document.setS3Location(s3SupplementLocation);
        return document;
    }
}
