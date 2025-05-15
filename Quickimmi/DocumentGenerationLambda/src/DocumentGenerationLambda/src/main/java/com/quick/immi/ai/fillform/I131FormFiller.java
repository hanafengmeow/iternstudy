package com.quick.immi.ai.fillform;

import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.i131.I131Table;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.S3Utils;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class I131FormFiller implements PDFFormFiller {
    private static final Logger logger = Logger.getLogger(I131FormFiller.class.getName());

    private I131Table i131Table;
    private FormGenerationTask task;
    private PDDocument pdDocument;
    private Properties properties;

    public I131FormFiller(I131Table i131Table, FormGenerationTask task) {
        this.i131Table = i131Table;
        this.task = task;
    }

    @Override
    public void init() {
        try {
            this.pdDocument = FormFillUtils.getForm(FormMapping.I131);
            this.properties = FormFillUtils.getFormMapping(FormMapping.I131);
        } catch (Exception e) {
            logger.severe("fail to load" + e);
        }
    }

    @Override
    public void fillDocument() {
        try {
            FormFillUtils.fillAllField(pdDocument, properties, "", this.i131Table);
        } catch (Exception e) {
            logger.severe("fail to fill G131Form " + e);
        }
    }

    @Override
    public String saveResult() {
        try {
            String key = String.format("%s/%s/%s", task.getUserId(),
                    task.getCaseId(), FormMapping.I131.getName() + ".pdf");
            FormFillUtils.saveForm(this.pdDocument, key);
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveLocal() throws IOException {
        try {
            this.pdDocument.save("generated-i-131.pdf");
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage());
        }
    }
}

