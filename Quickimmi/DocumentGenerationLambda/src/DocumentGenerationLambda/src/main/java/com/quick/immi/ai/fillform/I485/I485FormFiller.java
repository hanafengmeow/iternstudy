package com.quick.immi.ai.fillform.I485;

import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.i485.AdditionalRecord;
import com.quick.immi.ai.entity.i485.I485Table;
import com.quick.immi.ai.fillform.PDFFormFiller;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.S3Utils;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class I485FormFiller implements PDFFormFiller {
    private static final Logger logger = Logger.getLogger(I485FormFiller.class.getName());
    private PDDocument pdDocument;
    private I485Table i485Table;
    private Properties properties;
    private FormGenerationTask task;
    private List<PDDocument> supplementPDDocumentList;

    public I485FormFiller(I485Table i485Table, FormGenerationTask task) {
        this.i485Table = i485Table;
        this.task = task;
        this.supplementPDDocumentList = new ArrayList<>();
    }

    @Override
    public void init() {
        try {
            this.pdDocument = FormFillUtils.getForm(FormMapping.I485);
            this.properties = FormFillUtils.getFormMapping(FormMapping.I485);
            loadSupplementDocuments();
        } catch (Exception e) {
            logger.severe("Failed to load I-485 Form: " + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("Form i-485 filler initialized, is property null? " + String. valueOf(this.properties == null));
    }

    private void loadSupplementDocuments() {
        if (i485Table.getSupplements() != null && !i485Table.getSupplements().getAdditionalRecords().isEmpty()) {
            for (int i = 1; i < i485Table.getSupplements().getAdditionalRecords().size(); i++) {
                PDDocument temp = FormFillUtils.getForm(FormMapping.I485);
                supplementPDDocumentList.add(temp);
            }
        }
    }

    @Override
    public void fillDocument() {
        try {
            FormFillUtils.fillAllField(pdDocument, properties, "", i485Table);
            fillSupplementSection(this.i485Table.getSupplements().getAdditionalRecords());
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to fill I-485 Form: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void fillSupplementSection(List<AdditionalRecord> additionalRecords) throws IOException, IllegalAccessException {
        if (additionalRecords == null || additionalRecords.isEmpty()) {
            return;
        }
        AdditionalRecord firstSupplement = additionalRecords.get(0);

        FormFillUtils.fillAllField(pdDocument, properties, "supplement", firstSupplement);
        for (int i = 1; i < additionalRecords.size(); i++) {
            AdditionalRecord supplement = additionalRecords.get(i);
            PDDocument supplementDoc = supplementPDDocumentList.get(i - 1);
            FormFillUtils.fillAllField(supplementDoc, properties, "supplement", supplement);
        }
    }

    @Override
    public String saveResult() {
        try {
            String key = String.format("%s/%s/%s", task.getUserId(),
                    task.getCaseId(), FormMapping.I485.getName() + ".pdf");
            FormFillUtils.saveForm(this.pdDocument, key);
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            logger.severe("Error occurred while saving I-485 form: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String saveSupplementResult() {
        if (supplementPDDocumentList.isEmpty()) {
            logger.info("485 No supplement document available to save.");
            return null;
        }

        try {
            PDDocument temp = new PDDocument();
            for (PDDocument supplement : supplementPDDocumentList) {
                // FormFillUtils.setReadonly(supplement);
                FormFillUtils.setReadonly(supplement);
                temp.importPage(supplement.getPage(23));
            }

            String key = String.format("%s/%s/%s", task.getUserId(),
                    task.getCaseId(), FormMapping.I485_SUPPLEMENT.getName());
            logger.info("Successfully import supplement with: " + key);
            FormFillUtils.saveForm(temp, key);
            logger.info("Successfully saved the form");
            for (PDDocument supplement : supplementPDDocumentList) {
                logger.info("Closing supplement pddocument:" + supplement.getDocumentId());
                supplement.close();
            }
            temp.close();
            String s3location = S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
            logger.info("Returning S3 location: " + s3location);
            return s3location;
        } catch (Exception e) {
            logger.severe("Error occurred while saving supplement: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveLocal() throws IOException {
        try {
            FormFillUtils.saveFormToLocal(this.pdDocument, "generated-i485.pdf");
            this.pdDocument.close();
            saveLocalSupplementResult();
        } catch (Exception e) {
            logger.severe("Error occurred while saving I-485 Form: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveLocalSupplementResult() {
        if (supplementPDDocumentList.isEmpty()) {
            logger.info("485 No supplement document available to save.");
            return;
        }
    
        try {
            PDDocument temp = new PDDocument();
            for (PDDocument supplement : supplementPDDocumentList) {
                // FormFillUtils.setReadonly(supplement);
                FormFillUtils.setReadonly(supplement);
                temp.importPage(supplement.getPage(23));
            }

            String filename = "generated-i-485-supplement.pdf";
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