package com.quick.immi.ai.fillform;

import com.quick.immi.ai.entity.Identify;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import com.quick.immi.ai.utils.S3Utils;
import org.apache.pdfbox.pdmodel.PDDocument;

import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.g28.G28Table;
import com.quick.immi.ai.utils.FormFillUtils;

public class G28FormFiller implements PDFFormFiller {
    private static final Logger logger = Logger.getLogger(G28FormFiller.class.getName());

    private G28Table g28Table;
    private FormGenerationTask task;
    private PDDocument pdDocument;
    private Properties properties;

    public G28FormFiller(G28Table g28Table, FormGenerationTask task) {
        this.g28Table = g28Table;
        this.task = task;
    }

    @Override
    public void init() {
        try {
            this.pdDocument = FormFillUtils.getForm(FormMapping.G28);
            this.properties = FormFillUtils.getFormMapping(FormMapping.G28);
        } catch (Exception e) {
            logger.severe("fail to load" + e);
        }
    }

    @Override
    public void fillDocument() {
        try {
            FormFillUtils.fillAllTextField(pdDocument, properties, "representative",
                    this.g28Table.getRepresentative());
            FormFillUtils.fillAllTextField(pdDocument, properties, "representativeEligibility",
                    this.g28Table.getRepresentativeEligibility());

            FormFillUtils.fillAllTextField(pdDocument, properties, "appearance",
                    this.g28Table.getAppearance());

            FormFillUtils.fillAllTextField(pdDocument, properties, "clientConsent",
                    this.g28Table.getClientConsent());
            FormFillUtils.fillAllTextField(pdDocument, properties, "representativeSignature",
                    this.g28Table.getRepresentativeSignature());

            FormFillUtils.fillAllTextField(pdDocument, properties, "additionalInfo",
                    this.g28Table.getAdditionalInfo());
        } catch (Exception e) {
            logger.severe("fail to fill G28Form " + e);
        }

    }

    @Override
    public String saveResult() {
        try {
            String key = String.format("%s/%s/%s", task.getUserId(),
                task.getCaseId(), FormMapping.G28.getName() + ".pdf");
            FormFillUtils.saveForm(this.pdDocument, key);
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String saveResultByIdentify(Identify identify) {
        try {
            String key = String.format("%s/%s/%s", task.getUserId(),
                    task.getCaseId(), "g-28_for_" + identify.getValue() + ".pdf");
            FormFillUtils.saveForm(this.pdDocument, key);
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveLocal() throws IOException {
        try {
            this.pdDocument.save("g28-test.pdf");
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage());
        }
    }
}
