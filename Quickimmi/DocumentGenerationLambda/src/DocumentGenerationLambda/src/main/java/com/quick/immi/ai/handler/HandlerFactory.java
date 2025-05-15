package com.quick.immi.ai.handler;

import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.CaseType;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.handler.asylum.*;
import com.quick.immi.ai.handler.asylum.G28Handler;
import com.quick.immi.ai.handler.common.I130aHandler;
import com.quick.immi.ai.handler.familybased.*;

public class HandlerFactory {

    public static FormHandler getHandler(DocumentGenerationManager documentGenerationManager, FormGenerationTask formGenerationTask, String formMapping) {
        String caseType = formGenerationTask.getCaseType();
        if (CaseType.Asylum.getValue().equals(formGenerationTask.getType())
                && formMapping.startsWith("g-28")) {
            return new com.quick.immi.ai.handler.asylum.G28Handler(documentGenerationManager, formGenerationTask);
        } else if (FormMapping.I589.getName().equals(formMapping)) {
            return new I589Handler(documentGenerationManager, formGenerationTask);

        } else if (formMapping.startsWith("certificate_of_translation_for")) {

            return new COTHandler(documentGenerationManager, formGenerationTask);
        } else if (FormMapping.EOIR28.getName().equals(formMapping)) {
            return new EOIR28FormHandler(documentGenerationManager, formGenerationTask);
        } else if (FormMapping.MERGE.getName().equals(formMapping)) {
            return new MergeHandler(documentGenerationManager, formGenerationTask);
        } else if (CaseType.FamilyBased.getValue().equals(formGenerationTask.getType()) && formMapping.startsWith("g-28")) {
            return new com.quick.immi.ai.handler.familybased.G28Handler(documentGenerationManager, formGenerationTask);
        } else if (CaseType.FamilyBased.getValue().equals(caseType) && FormMapping.I130.getName().equals(formMapping)) {
            return new I130Handler(documentGenerationManager, formGenerationTask);
        } else if (CaseType.FamilyBased.getValue().equals(caseType) && FormMapping.I864.getName().equals(formMapping)) {
            return new I864Handler(documentGenerationManager, formGenerationTask);
        } else if(CaseType.FamilyBased.getValue().equals(caseType) && FormMapping.I131.getName().equals(formMapping)){
            return new I131Handler(documentGenerationManager, formGenerationTask);
        } else if(CaseType.FamilyBased.getValue().equals(caseType) && FormMapping.I765.getName().equals(formMapping)){
            return new I765Handler(documentGenerationManager, formGenerationTask);
        } else if(CaseType.FamilyBased.getValue().equals(caseType) && FormMapping.I130a.getName().equals(formMapping)){
            return new I130aHandler(documentGenerationManager, formGenerationTask);
        } else if(CaseType.FamilyBased.getValue().equals(caseType) && FormMapping.I485.getName().equals(formMapping)) {
            return new I485Handler(documentGenerationManager, formGenerationTask);
        } else if(CaseType.FamilyBased.getValue().equals(caseType) && formMapping.startsWith("g-28")){
            return new com.quick.immi.ai.handler.familybased.G28Handler(documentGenerationManager, formGenerationTask);
        } else {
            throw new RuntimeException(String.format("not support form type %s", formMapping));
        }
    }

//
//    private Map<String, FormHandler> constructFormHandlerMap(String caseType,
//                                                             String documentType,
//                                                             DocumentGenerationManager documentGenerationManager,
//                                                             FormGenerationTask formGenerationTask){
//        HashMap<String, FormHandler> formHandlerHashMap = new HashMap<>();
//        formHandlerHashMap.put(getKey(CaseType.Asylum.getValue(), FormMapping.), new G28Handler(documentGenerationManager, formGenerationTask));
//
//        return formHandlerHashMap;
//    }
//
//    private String getKey(String caseType,
//                          String documentType) {
//        return String.format("%s_%s", caseType, documentType);
//    }
}

