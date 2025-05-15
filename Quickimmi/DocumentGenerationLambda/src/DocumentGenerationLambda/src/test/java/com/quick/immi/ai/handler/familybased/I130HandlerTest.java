package com.quick.immi.ai.handler.familybased;

import com.google.gson.Gson;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class I130HandlerTest {
    @Mock
    private DocumentGenerationManager documentGenerationManager;

    private ApplicationCase applicationCase;

    private I130Handler i130Handler;
    private Lawyer lawyer;
    private Document document;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        FormGenerationTask formGenerationTask = getFormGenerationTask();

        i130Handler = new I130Handler(documentGenerationManager, formGenerationTask);

        // Mock ApplicationCase with a non-empty profile
        applicationCase = new ApplicationCase();
        FamilyBasedCaseProfile familyBasedCaseProfile = new FamilyBasedCaseProfile();
        applicationCase.setProfile(new Gson().toJson(familyBasedCaseProfile));

        when(documentGenerationManager.getApplicationCase(3001L)).thenReturn(applicationCase);

        // Mock Lawyer object with a non-empty profile
        lawyer = new Lawyer();
        lawyer.setProfile(new Gson().toJson(new LawyerProfile()));
        when(documentGenerationManager.getLawyer(201)).thenReturn(lawyer);

        // Mock Document object that will be returned
        Document mainDocument = new Document();
        mainDocument.setId(1L);
        when(documentGenerationManager.getDocument(1L)).thenReturn(mainDocument);

        Document supplementDocument = new Document();
        supplementDocument.setId(2L);
        when(documentGenerationManager.getDocument(2L)).thenReturn(supplementDocument);
    }


    @Test
    void testGenerateDocument() {
        Document document = i130Handler.generateDocument();
        assertEquals(1L, document.getId());
    }

    @Test
    void testGenerateSupplementDocument() {
        Document mainDocument = i130Handler.generateDocument();
        Document document = i130Handler.generateSupplementDocument();
        assertEquals(2L, document.getId());
    }

    private FormGenerationTask getFormGenerationTask(){
        FormGenerationTask formGenerationTaskSupplement = new FormGenerationTask();
        formGenerationTaskSupplement.setId(1002L);
        formGenerationTaskSupplement.setDocumentId(2L);
        formGenerationTaskSupplement.setCaseType("Family-Based");
        formGenerationTaskSupplement.setType("i-130");
        formGenerationTaskSupplement.setCaseId(3001L);
        formGenerationTaskSupplement.setUserId(101);
        formGenerationTaskSupplement.setLawyerId(201);
        formGenerationTaskSupplement.setFormName("I-131 Form");
        formGenerationTaskSupplement.setCreatedAt(System.currentTimeMillis());
        formGenerationTaskSupplement.setUpdatedAt(System.currentTimeMillis());

        FormGenerationTask formGenerationTask = new FormGenerationTask();
        formGenerationTask.setId(1001L);
        formGenerationTask.setDocumentId(1L);
        formGenerationTask.setCaseType("Family-Based");
        formGenerationTask.setType("i-130");
        formGenerationTask.setCaseId(3001L);
        formGenerationTask.setUserId(101);
        formGenerationTask.setLawyerId(201);
        formGenerationTask.setFormName("I-131 Form");
        formGenerationTask.setDocumentName("Travel Document Application");
        formGenerationTask.setMetadata(new Gson().toJson(formGenerationTaskSupplement));
        formGenerationTask.setCreatedAt(System.currentTimeMillis());
        formGenerationTask.setUpdatedAt(System.currentTimeMillis());
        return formGenerationTask;
    }
}