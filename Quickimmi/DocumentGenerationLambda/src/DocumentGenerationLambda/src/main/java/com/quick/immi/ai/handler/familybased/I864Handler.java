package com.quick.immi.ai.handler.familybased;

import com.google.gson.Gson;
import com.quick.immi.ai.converter.familybased.I864Converter;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.familyBased.i864.I864Metadata;
import com.quick.immi.ai.entity.familyBased.i864.I864Table;
import com.quick.immi.ai.fillform.familyBased.I864FormFiller;
import com.quick.immi.ai.handler.FormHandler;

public class I864Handler extends FormHandler {
    private final DocumentGenerationManager documentGenerationManager;
    private final FormGenerationTask formGenerationTask;

    private com.quick.immi.ai.fillform.familyBased.I864FormFiller i864FormFiller;

    public I864Handler(DocumentGenerationManager documentGenerationManager, FormGenerationTask formGenerationTask) {
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

        I864Converter i864Converter = new I864Converter();

        I864Metadata i864Metadata = new Gson().fromJson(formGenerationTask.getMetadata(), I864Metadata.class);
        I864Table i130Table = i864Converter.getI864Table(lawyerProfile, familyBasedCaseProfile, i864Metadata.getIndex());
        i864FormFiller = new I864FormFiller(i130Table, formGenerationTask);
        i864FormFiller.init();
        i864FormFiller.fillDocument();

        //save main i589 document
        String s3Location = i864FormFiller.saveResult();
        Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
        document.setS3Location(s3Location);
        return document;
    }
}
