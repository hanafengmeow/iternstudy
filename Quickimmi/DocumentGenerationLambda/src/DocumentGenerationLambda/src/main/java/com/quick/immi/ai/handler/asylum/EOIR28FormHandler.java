package com.quick.immi.ai.handler.asylum;

import com.google.gson.Gson;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.eoir28.EOIR28Table;
import com.quick.immi.ai.converter.asylum.EOIR28TableConverter;
import com.quick.immi.ai.fillform.EOIR28FormFiller;
import com.quick.immi.ai.handler.FormHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EOIR28FormHandler extends FormHandler {
    private final DocumentGenerationManager documentGenerationManager;
    private final FormGenerationTask formGenerationTask;

    public EOIR28FormHandler(DocumentGenerationManager documentGenerationManager, FormGenerationTask formGenerationTask) {
        super(documentGenerationManager, formGenerationTask);
        this.documentGenerationManager = documentGenerationManager;
        this.formGenerationTask = formGenerationTask;
    }

    @Override
    public Document generateDocument() {
        Long caseId = formGenerationTask.getCaseId();
        ApplicationCase applicationCase = documentGenerationManager.getApplicationCase(caseId);
        Lawyer lawyer = documentGenerationManager.getLawyer(formGenerationTask.getLawyerId());
        LawyerProfile lawyerProfile = new Gson().fromJson(lawyer.getProfile(), LawyerProfile.class);

        EOIR28TableConverter eoir28TableConverter = new EOIR28TableConverter();
        EOIR28Table eoir28Table = eoir28TableConverter.getEOIR28Table(lawyerProfile, applicationCase);
        EOIR28FormFiller eoir28FormFiller = new EOIR28FormFiller(eoir28Table, formGenerationTask);
        eoir28FormFiller.init();
        eoir28FormFiller.fillDocument();
        String s3Location = eoir28FormFiller.saveResult();
        Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
        document.setS3Location(s3Location);
        return document;
    }
}
