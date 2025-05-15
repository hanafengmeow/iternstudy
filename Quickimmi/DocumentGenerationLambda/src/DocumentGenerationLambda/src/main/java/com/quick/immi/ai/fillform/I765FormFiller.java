package com.quick.immi.ai.fillform;

import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.i765.I765Table;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.S3Utils;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class I765FormFiller implements PDFFormFiller {
    private static final Logger logger = Logger.getLogger(I765FormFiller.class.getName());

    private I765Table i765Table;
    private FormGenerationTask task;
    private PDDocument pdDocument;
    private Properties properties;

    public I765FormFiller(I765Table i765Table, FormGenerationTask task) {
        this.i765Table = i765Table;
        this.task = task;
    }

    @Override
    public void init() {
        try {
            this.pdDocument = FormFillUtils.getForm(FormMapping.I765);
            this.properties = FormFillUtils.getFormMapping(FormMapping.I765);
        } catch (Exception e) {
            logger.severe("fail to load" + e);
        }
    }

    @Override
    public void fillDocument() {
        try {
            FormFillUtils.fillAllField(pdDocument, properties, "", this.i765Table);
        } catch (Exception e) {
            logger.severe("fail to fill G28Form " + e);
        }
    }

    @Override
    public String saveResult() {
        try {
            String key = String.format("%s/%s/%s", task.getUserId(),
                    task.getCaseId(), FormMapping.I765.getName() + ".pdf");
            FormFillUtils.saveForm(this.pdDocument, key);
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveLocal() throws IOException {
        try {
            this.pdDocument.save("i765-test.pdf");
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage());
        }
    }
}
