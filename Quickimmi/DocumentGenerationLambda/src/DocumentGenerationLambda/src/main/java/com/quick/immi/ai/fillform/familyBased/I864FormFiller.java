package com.quick.immi.ai.fillform.familyBased;

import com.google.gson.Gson;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.familyBased.i864.I864Metadata;
import com.quick.immi.ai.entity.familyBased.i864.I864Table;
import com.quick.immi.ai.entity.familyBased.i864.Supplement;
import com.quick.immi.ai.fillform.PDFFormFiller;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.S3Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;

public class I864FormFiller implements PDFFormFiller {
    private static final Logger logger = Logger.getLogger(I864FormFiller.class.getName());

    private I864Table i864Table;
    private FormGenerationTask task;
    private PDDocument pdDocument;
    private List<PDDocument> supplementPDDocumentList;

    private Properties properties;

    public I864FormFiller(I864Table i864Table, FormGenerationTask task) {
        this.i864Table = i864Table;
        this.task = task;
        this.supplementPDDocumentList = new ArrayList<>();
    }
    @Override
    public void init() {
        try {
            this.pdDocument = FormFillUtils.getForm(FormMapping.I864);  // I864 mapping
            this.properties = FormFillUtils.getFormMapping(FormMapping.I864);
            loadSupplementDocuments();
        } catch (Exception e) {
            logger.severe("Failed to load I-864 form: " + e.getMessage());
        }
    }

    private void loadSupplementDocuments() {
        if (i864Table.getSupplement() != null && !i864Table.getSupplement().isEmpty()) {
            for (int i = 1; i < i864Table.getSupplement().size(); i++) {
                PDDocument temp = FormFillUtils.getForm(FormMapping.I864);
                supplementPDDocumentList.add(temp);
            }
        }
    }



    @Override
    public void fillDocument() {
        try {
            FormFillUtils.fillAllField(pdDocument, properties, "", this.i864Table);
            fillSupplementSection(this.i864Table.getSupplement());

        } catch (Exception e) {
            logger.severe("Failed to fill I-864 form: " + e.getMessage());
        }
    }

    private void fillSupplementSection(List<Supplement> supplements) throws IOException, IllegalAccessException {
        if (supplements == null || supplements.isEmpty()) {
            return;
        }
        // Handle the first supplement attached to the main document
        Supplement firstSupplement = supplements.get(0);
        FormFillUtils.fillAllTextField(pdDocument, properties, "supplement", firstSupplement);
        FormFillUtils.fillAllTextFieldsInList(pdDocument, properties,
            "supplement.information", firstSupplement.getInformation());

        // Handle additional Supplement forms, each in their own document
        for (int i = 1; i < supplements.size(); i++) {
            Supplement supplement = supplements.get(i);
            PDDocument supplementDoc = supplementPDDocumentList.get(i - 1);
            FormFillUtils.fillAllTextField(supplementDoc, properties, "supplement", supplement);
            FormFillUtils.fillAllTextFieldsInList(supplementDoc, properties,
                "supplement.information", supplement.getInformation());
        }
    }
    @Override
    public String saveResult() {
        try {
            String metadata = this.task.getMetadata();
            I864Metadata i864Metadata = new Gson().fromJson(metadata, I864Metadata.class);

            String key = String.format("%s/%s/%s", task.getUserId(),
                task.getCaseId(), FormMapping.I864.getName() + String.format("_%s.pdf", i864Metadata.getSponsorFirstName()));

            FormFillUtils.saveForm(this.pdDocument, key);
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            logger.severe("Error occurred while saving I-864 form: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveLocal() throws IOException {
        try {
            // Use the system's temp directory or project build folder
            // String filePath = System.getProperty("user.dir") + "/target/i864-test.pdf"; // For Maven
            this.pdDocument.save("i864-test.pdf");
            this.pdDocument.close();
            saveLocalSupplementResult();
        } catch (Exception e) {
            logger.severe("Error occurred while saving I-864 form locally: " + e.getMessage());
        }
    }


    public void saveLocalSupplementResult() throws IOException {
        if (supplementPDDocumentList.isEmpty()) {
            logger.info("i864 No supplement document available to save.");
            return;
        }

        try {
            PDDocument temp = new PDDocument();
            for (PDDocument supplement : supplementPDDocumentList) {
                // FormFillUtils.setReadonly(supplement);
                temp.importPage(supplement.getPage(9));
            }

            String filename = "i864-supplement.pdf";
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
