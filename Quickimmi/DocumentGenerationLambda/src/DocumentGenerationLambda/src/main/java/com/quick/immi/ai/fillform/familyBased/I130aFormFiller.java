package com.quick.immi.ai.fillform.familyBased;

import com.quick.immi.ai.utils.S3Utils;
import org.apache.pdfbox.pdmodel.PDDocument;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.familyBased.i130a.*; 
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.fillform.PDFFormFiller;
import com.quick.immi.ai.entity.familyBased.i130a.Supplement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class I130aFormFiller implements PDFFormFiller {
    private static final Logger logger = Logger.getLogger(I130FormFiller.class.getName());

    private I130aTable i130aTable;
    private FormGenerationTask task;
    private PDDocument pdDocument;
    private Properties properties;
    private List<PDDocument> supplementPDDocumentList;

    public I130aFormFiller(I130aTable i130aTable, FormGenerationTask task) {
        this.i130aTable = i130aTable;
        this.task = task;
        this.supplementPDDocumentList = new ArrayList<>();
    }

    @Override
    public void init() {
        try {
            this.pdDocument = FormFillUtils.getForm(FormMapping.I130a); 
            this.properties = FormFillUtils.getFormMapping(FormMapping.I130a); 
            loadSupplementDocuments();
        } catch (Exception e) {
            logger.severe("Fail to load I-130a form: " + e);
        }
    }

    private void loadSupplementDocuments() {
        if (i130aTable.getSupplements() != null && !i130aTable.getSupplements().isEmpty()) {
            for (int i = 1; i < i130aTable.getSupplements().size(); i++) {
                PDDocument temp = FormFillUtils.getForm(FormMapping.I130a);
                supplementPDDocumentList.add(temp);
            }
        }
    }

    @Override
    public void fillDocument() {
        try {
            FormFillUtils.fillAllField(pdDocument, properties, "", this.i130aTable);
            fillSupplements(this.i130aTable.getSupplements());
        } catch (Exception e) {
            logger.severe("Failed to fill I-130a form: " + e);
            throw new RuntimeException(e);
        }
    }

    private void fillSupplements(List<Supplement> supplements) throws IOException, IllegalAccessException {
        if (supplements == null || supplements.isEmpty()) {
            return;
        }
        logger.info("filling supplement section called");
        
        // Handle the first supplement attached to the main document
        Supplement firstSupplement = supplements.get(0);
        FormFillUtils.fillAllTextField(pdDocument, properties, "supplement", firstSupplement);
        FormFillUtils.fillAllTextFieldsInList(pdDocument, properties, 
        "supplement.additionalRecords", firstSupplement.getAdditionalRecords());

        // Handle additional Supplement forms, each in their own document
        for (int i = 1; i < i130aTable.getSupplements().size(); i++) {
            Supplement supplement = supplements.get(i);
            PDDocument supplementDoc = supplementPDDocumentList.get(i - 1);
            FormFillUtils.fillAllTextField(supplementDoc, properties, "supplement", supplement);
            FormFillUtils.fillAllTextFieldsInList(supplementDoc, properties, 
                "supplement.additionalRecords", supplement.getAdditionalRecords());
        }
    }

    @Override
    public String saveResult() {
        try {
            String key = String.format("%s/%s/%s", task.getUserId(),
                    task.getCaseId(), FormMapping.I130a.getName() + ".pdf");
            FormFillUtils.saveForm(this.pdDocument, key);
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "fail to saveResult", e);
            throw new RuntimeException(e);
        }
    }

    public String saveSupplementResult() {
        if (supplementPDDocumentList.isEmpty()) {
            logger.info("130 No supplement document available to save.");
            return null;
        }

        try {
            PDDocument temp = new PDDocument();
            for (PDDocument supplement : supplementPDDocumentList) {
                // FormFillUtils.setReadonly(supplement);
                FormFillUtils.setReadonly(supplement);
                temp.importPage(supplement.getPage(11));
            }
            String key = String.format("%s/%s/%s", task.getUserId(),
                    task.getCaseId(), FormMapping.I130a_SUPPLEMENT.getName());

            FormFillUtils.saveForm(this.pdDocument, key);

            for (PDDocument supplement : supplementPDDocumentList) {
                supplement.close();
            }
            temp.close();
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            logger.severe("Error occurred while saving supplement: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveLocal() throws IOException {
        try {
            logger.info("saving document i130a");
            FormFillUtils.saveFormToLocal(this.pdDocument, "generated-i-130a.pdf");
            saveLocalSupplementResult();
        } catch (Exception e) {
            logger.severe("Error occurred while saving local I-130a form: " + e.getMessage());
        }
    }

    public void saveLocalSupplementResult() throws IOException {
        if (supplementPDDocumentList.isEmpty()) {
            logger.info("130a No supplement document available to save.");
            return;
        }
    
        try {
            PDDocument temp = new PDDocument();
            for (PDDocument supplement : supplementPDDocumentList) {
                // FormFillUtils.setReadonly(supplement);
                temp.importPage(supplement.getPage(5));
            }

            String filename = "generated-i-130a-supplement.pdf";
            FormFillUtils.saveFormToLocal(temp, filename);
            logger.info("Supplement document saved locally as: " + filename);

            for (PDDocument supplement : supplementPDDocumentList) {
                supplement.close();
            }
            temp.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "fail to saveLocalSupplementResult", e);
        }
    }
}
