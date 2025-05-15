package com.quick.immi.ai.handler.familybased;

import com.google.gson.Gson;
import com.quick.immi.ai.converter.familybased.G28TableConverter;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.g28.G28Table;
import com.quick.immi.ai.fillform.G28FormFiller;
import com.quick.immi.ai.handler.FormHandler;

public class G28Handler extends FormHandler {

    private final DocumentGenerationManager documentGenerationManager;
    private final FormGenerationTask formGenerationTask;

    private G28FormFiller g28FormFiller;

    public G28Handler(DocumentGenerationManager documentGenerationManager, FormGenerationTask formGenerationTask) {
        super(documentGenerationManager, formGenerationTask);
        this.documentGenerationManager = documentGenerationManager;
        this.formGenerationTask = formGenerationTask;
    }

    @Override
    public Document generateDocument() {
        Long caseId = formGenerationTask.getCaseId();
        ApplicationCase applicationCase = documentGenerationManager.getApplicationCase(caseId);
        String profile = applicationCase.getProfile();
        FamilyBasedCaseProfile familyBasedCaseProfile = new Gson().fromJson(profile, FamilyBasedCaseProfile.class);
        Lawyer lawyer = documentGenerationManager.getLawyer(formGenerationTask.getLawyerId());
        LawyerProfile lawyerProfile = new Gson().fromJson(lawyer.getProfile(), LawyerProfile.class);

        G28TableConverter g28TableConverter = new G28TableConverter();
        G28FormMetadata g28FormMetadata = new Gson().fromJson(formGenerationTask.getMetadata(), G28FormMetadata.class);
        G28Table g28Table = g28TableConverter.getG28Table(lawyerProfile, familyBasedCaseProfile, g28FormMetadata);

        g28FormFiller = new G28FormFiller(g28Table, formGenerationTask);
        g28FormFiller.init();
        g28FormFiller.fillDocument();

        //save main i589 document
        String s3Location = g28FormFiller.saveResultByIdentify(g28FormMetadata.getIdentify());
        Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
        document.setS3Location(s3Location);
        return document;
    }
}
