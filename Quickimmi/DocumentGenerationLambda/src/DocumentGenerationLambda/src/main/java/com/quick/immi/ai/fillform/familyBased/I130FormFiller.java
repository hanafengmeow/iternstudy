package com.quick.immi.ai.fillform.familyBased;

import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.familyBased.i130.*;
import com.quick.immi.ai.fillform.PDFFormFiller;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.S3Utils;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;


public class I130FormFiller implements PDFFormFiller {
    private static final Logger logger = Logger.getLogger(I130FormFiller.class.getName());
    private I130Table i130Table;
    private FormGenerationTask task;
    private PDDocument pdDocument;
    private Properties properties;
    private List<PDDocument> supplementPDDocumentList;

    public I130FormFiller(I130Table i130Table, FormGenerationTask task) {
        this.i130Table = i130Table;
        this.task = task;
        this.supplementPDDocumentList = new ArrayList<>();
    }

    @Override
    public void init() {
        try {
            this.pdDocument = FormFillUtils.getForm(FormMapping.I130); 
            this.properties = FormFillUtils.getFormMapping(FormMapping.I130);
            loadSupplementDocuments(); 
        } catch (Exception e) {
            logger.severe("Fail to load I-130 form: " + e);
        }
        logger.info("Form i-130 filler initialized, is property null? " + String. valueOf(this.properties == null));
    }

    private void loadSupplementDocuments() {
        if (i130Table.getSupplements() != null && !i130Table.getSupplements().isEmpty()) {
            for (int i = 1; i < i130Table.getSupplements().size(); i++) {
                PDDocument temp = FormFillUtils.getForm(FormMapping.I130);
                supplementPDDocumentList.add(temp);
            }
        }
    }

    @Override
    public void fillDocument() {
        try {
            // Fill main part of the form
            FormFillUtils.fillAllField(pdDocument, properties, "", this.i130Table);
            // Fill Supplement 
            fillSupplementSection(this.i130Table.getSupplements());
        } catch (Exception e) {
            logger.severe("Failed to fill I-130 form: " + e);
        }
    }

    private void fillSupplementSection(List<Supplement> supplements) throws IOException, IllegalAccessException {
        if (supplements == null || supplements.isEmpty()) {
            return;
        }
        Supplement firstSupplement = supplements.get(0);

        FormFillUtils.fillAllField(pdDocument, properties, "supplement", firstSupplement);
        for (int i = 1; i < supplements.size(); i++) {
            Supplement supplement = supplements.get(i);
            PDDocument supplementDoc = supplementPDDocumentList.get(i - 1);
            FormFillUtils.fillAllField(supplementDoc, properties, "supplement", supplement);
        }
    }

    @Override
    public String saveResult() {
        try {
            String key = String.format("%s/%s/%s", task.getUserId(),
                    task.getCaseId(), FormMapping.I130.getName() + ".pdf");
            FormFillUtils.saveForm(this.pdDocument, key);
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            logger.severe("Error occurred while saving I-130 form: " + e.getMessage());
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
                    task.getCaseId(), FormMapping.I130_SUPPLEMENT.getName());

            FormFillUtils.saveForm(temp, key);

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

    public void saveLocal() {
        try {
            FormFillUtils.saveFormToLocal(this.pdDocument, "generated-i-130.pdf");
            this.pdDocument.close();
            saveLocalSupplementResult();
        } catch (Exception e) {
            logger.severe("Error occurred while saving local I-130 form: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveLocalSupplementResult() {
        if (supplementPDDocumentList.isEmpty()) {
            logger.info("130 No supplement document available to save.");
            return;
        }
    
        try {
            PDDocument temp = new PDDocument();
            for (PDDocument supplement : supplementPDDocumentList) {
                // FormFillUtils.setReadonly(supplement);
                FormFillUtils.setReadonly(supplement);
                temp.importPage(supplement.getPage(11));
            }

            String filename = "generated-i-130-supplement.pdf";
            FormFillUtils.saveFormToLocal(temp, filename);
            logger.info("Supplement document saved locally as: " + filename);

            for (PDDocument supplement : supplementPDDocumentList) {
                supplement.close();
            }
            temp.close();
        } catch (Exception e) {
            logger.severe("Error occurred while saving supplement: " + e.getMessage());
        }
    }
}
