package com.quick.immi.ai.handler.asylum;

import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.g28.G28Table;
import com.quick.immi.ai.converter.asylum.G28TableConverter;
import com.quick.immi.ai.fillform.G28FormFiller;
import com.quick.immi.ai.handler.FormHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class G28Handler extends FormHandler {

    private final DocumentGenerationManager documentGenerationManager;
    private final FormGenerationTask formGenerationTask;

    public G28Handler(DocumentGenerationManager documentGenerationManager, FormGenerationTask formGenerationTask) {
        super(documentGenerationManager, formGenerationTask);
        this.documentGenerationManager = documentGenerationManager;
        this.formGenerationTask = formGenerationTask;
    }

    @Override
    public Document generateDocument() {
        String formName = formGenerationTask.getFormName();
        String prefix = "g-28_for_";
        String identifyString = formName.substring(prefix.length());
        Identify identify = Identify.fromValue(identifyString);
        Long caseId = formGenerationTask.getCaseId();
        ApplicationCase applicationCase = documentGenerationManager.getApplicationCase(caseId);
        Lawyer lawyer = documentGenerationManager.getLawyer(formGenerationTask.getLawyerId());
        LawyerProfile lawyerProfile = new Gson().fromJson(lawyer.getProfile(), LawyerProfile.class);
        lawyerProfile.getBasicInfo().setEmailAddress(lawyer.getEmail());
        lawyerProfile.getEligibility().setNameofLawFirm(lawyer.getLawFirm());
        G28TableConverter g28TableConverter = new G28TableConverter();
        String metadata = this.formGenerationTask.getMetadata();
        G28FormMetadata g28FormMetadata = null;
        if(StringUtils.isNullOrEmpty(metadata)){
            g28FormMetadata = new Gson().fromJson(metadata, G28FormMetadata.class);
        }
        G28Table g28Table = g28TableConverter.getG28Table(lawyerProfile, applicationCase, g28FormMetadata, identify);
        G28FormFiller g28FormFiller = new G28FormFiller(g28Table, formGenerationTask);
        g28FormFiller.init();
        g28FormFiller.fillDocument();
        String s3Location = g28FormFiller.saveResultByIdentify(identify);
        Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
        document.setS3Location(s3Location);
        return document;
    }
}
