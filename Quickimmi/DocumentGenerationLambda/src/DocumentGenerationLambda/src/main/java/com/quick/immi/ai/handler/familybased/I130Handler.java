package com.quick.immi.ai.handler.familybased;

import com.google.gson.Gson;
import com.quick.immi.ai.converter.familybased.I130Converter;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.familyBased.i130.I130Table;
import com.quick.immi.ai.fillform.familyBased.I130FormFiller;
import com.quick.immi.ai.handler.FormHandler;

import java.util.logging.Logger;

public class I130Handler extends FormHandler {
    private static final Logger log = Logger.getLogger(I130Handler.class.getName());

    private final DocumentGenerationManager documentGenerationManager;
    private final FormGenerationTask formGenerationTask;

    private I130FormFiller i130FormFiller;
    private final FormGenerationTask formGenerationTaskSupplement;

    public I130Handler(DocumentGenerationManager documentGenerationManager, FormGenerationTask formGenerationTask) {
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
            log.info(String.format("successfully generate i130 document for taskId %s", formGenerationTask.getId()));
            supplementDocument = generateSupplementDocument();
            TaskStatus taskStatus = supplementDocument.getS3Location() == null ? TaskStatus.SKIPPED : TaskStatus.SUCCESS;
            updateDocument(supplementDocument, taskStatus);
            log.info(String.format("successfully generate i130 supplement with status %s for i130 taskId %s", taskStatus.getValue(),
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
        log.info(String.format("start generateDocument %s", this.formGenerationTask.getId()));
        Long caseId = formGenerationTask.getCaseId();
        ApplicationCase applicationCase = documentGenerationManager.getApplicationCase(caseId);

        FamilyBasedCaseProfile familyBasedCaseProfile = new Gson().fromJson(applicationCase.getProfile(), FamilyBasedCaseProfile.class);
        Lawyer lawyer = documentGenerationManager.getLawyer(formGenerationTask.getLawyerId());
        LawyerProfile lawyerProfile = new Gson().fromJson(lawyer.getProfile(), LawyerProfile.class);

        log.info(String.format("start i130Converter %s", this.formGenerationTask.getId()));
        I130Converter i130Converter = new I130Converter();
        I130Table i130Table = i130Converter.getI130Table(lawyerProfile, familyBasedCaseProfile);
        log.info(String.format("i130Converter getI130Table successfully %s", this.formGenerationTask.getId()));
        i130FormFiller = new I130FormFiller(i130Table, formGenerationTask);
        i130FormFiller.init();
        i130FormFiller.fillDocument();
        log.info(String.format("fill I130 successfully %s", this.formGenerationTask.getId()));

        //save main i589 document
        String s3Location = i130FormFiller.saveResult();
        log.info(String.format("I130 saveResult successfully %s", this.formGenerationTask.getId()));

        Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
        document.setS3Location(s3Location);
        log.info(String.format("get document successfully %s", this.formGenerationTask.getId()));
        return document;
    }

    //make sure generateSupplementDocument is after generateDocument()
    public Document generateSupplementDocument() {
        String s3SupplementLocation = i130FormFiller.saveSupplementResult();
        Document document = documentGenerationManager.getDocument(formGenerationTaskSupplement.getDocumentId());
        document.setS3Location(s3SupplementLocation);
        return document;
    }
}
