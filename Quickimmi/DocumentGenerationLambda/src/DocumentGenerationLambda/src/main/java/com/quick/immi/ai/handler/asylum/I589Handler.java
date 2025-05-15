package com.quick.immi.ai.handler.asylum;

import com.google.gson.Gson;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.asylum.business.AsylumCaseProfile;
import com.quick.immi.ai.entity.asylum.i589.I589Table;
import com.quick.immi.ai.converter.asylum.I589TableConverter;
import com.quick.immi.ai.fillform.asylum.I589FormFiller;
import com.quick.immi.ai.handler.FormHandler;
import com.quick.immi.ai.handler.familybased.I131Handler;
import crawlercommons.utils.Strings;

import java.util.logging.Logger;


public class I589Handler extends FormHandler {
    private static final Logger log = Logger.getLogger(I589Handler.class.getName());

    private final DocumentGenerationManager documentGenerationManager;
    private final FormGenerationTask formGenerationTask;
    private final FormGenerationTask formGenerationTaskSupplement;

    private I589FormFiller i589FormFiller;
    public I589Handler(DocumentGenerationManager documentGenerationManager, FormGenerationTask formGenerationTask) {
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
            log.info(String.format("successfully generate i589 document for taskId %s", formGenerationTask.getId()));
            supplementDocument = generateSupplementDocument();
            TaskStatus taskStatus = supplementDocument.getS3Location() == null ? TaskStatus.SKIPPED : TaskStatus.SUCCESS;
            updateDocument(supplementDocument, taskStatus);
            log.info(String.format("successfully generate i589 supplement with status %s for 589 taskId %s", taskStatus.getValue(),
                    formGenerationTask.getId()));
        } catch (Exception exp){
            log.severe(String.format("fail to generate document for task %s", formGenerationTask) +  exp);
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
        String profile = applicationCase.getTranslatedProfile();
        if (Strings.isBlank(profile)) {
            profile = applicationCase.getProfile();
        }
        if(Strings.isBlank(profile)){
            throw new RuntimeException(String.format("Empty application profile for %s", caseId));
        }
        AsylumCaseProfile asylumCaseProfile = new Gson().fromJson(profile, AsylumCaseProfile.class);
        Lawyer lawyer = documentGenerationManager.getLawyer(formGenerationTask.getLawyerId());
        LawyerProfile lawyerProfile = new Gson().fromJson(lawyer.getProfile(), LawyerProfile.class);

        I589TableConverter i589TableConverter = new I589TableConverter();

        I589Table i589Table = i589TableConverter.getI589Table(applicationCase.getSubType(), lawyerProfile, asylumCaseProfile);

        i589FormFiller  = new I589FormFiller(i589Table, formGenerationTask);
        i589FormFiller.init();
        i589FormFiller.fillDocument();

        //save main i589 document
        String s3Location = i589FormFiller.saveResult();
        Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
        document.setS3Location(s3Location);
        return document;
    }

    //make sure generateSupplementDocument is after generateDocument()
    public Document generateSupplementDocument() {
        String metadata = formGenerationTask.getMetadata();
        FormGenerationTask supplementFormGenerationTask = new Gson().fromJson(metadata, FormGenerationTask.class);

        String s3SupplementLocation = i589FormFiller.saveSupplementResult();
        Document document = documentGenerationManager.getDocument(supplementFormGenerationTask.getDocumentId());
        document.setS3Location(s3SupplementLocation);
        return document;
    }
}
