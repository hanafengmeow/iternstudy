package com.quick.immi.ai.handler.familybased;

import com.google.gson.Gson;
import com.quick.immi.ai.converter.familybased.I131Converter;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.i131.I131Table;
import com.quick.immi.ai.fillform.I131FormFiller;
import com.quick.immi.ai.handler.FormHandler;

import java.util.logging.Logger;

public class I131Handler extends FormHandler {
    private static final Logger log = Logger.getLogger(I131Handler.class.getName());

    private final DocumentGenerationManager documentGenerationManager;
    private final FormGenerationTask formGenerationTask;

    private I131FormFiller i131FormFiller;

    public I131Handler(DocumentGenerationManager documentGenerationManager, FormGenerationTask formGenerationTask) {
        super(documentGenerationManager, formGenerationTask);
        this.documentGenerationManager = documentGenerationManager;
        this.formGenerationTask = formGenerationTask;
    }

    @Override
    public Document generateDocument() {
        log.info("start generateDocument for I131...");
        Long caseId = formGenerationTask.getCaseId();
        ApplicationCase applicationCase = documentGenerationManager.getApplicationCase(caseId);
        String profile = applicationCase.getProfile();
        FamilyBasedCaseProfile familyBasedCaseProfile = new Gson().fromJson(profile, FamilyBasedCaseProfile.class);
        Lawyer lawyer = documentGenerationManager.getLawyer(formGenerationTask.getLawyerId());
        LawyerProfile lawyerProfile = new Gson().fromJson(lawyer.getProfile(), LawyerProfile.class);

        log.info("i131Converter for I131...");
        I131Converter i131Converter = new I131Converter();

        I131Table i131Table = i131Converter.getI131Table(lawyerProfile, familyBasedCaseProfile);
        i131FormFiller = new I131FormFiller(i131Table, formGenerationTask);
        i131FormFiller.init();
        i131FormFiller.fillDocument();

        String s3Location = i131FormFiller.saveResult();
        Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
        document.setS3Location(s3Location);
        return document;
    }
}
