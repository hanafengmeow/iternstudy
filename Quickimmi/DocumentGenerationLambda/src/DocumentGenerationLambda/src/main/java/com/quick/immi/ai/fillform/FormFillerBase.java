package com.quick.immi.ai.fillform;

import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.S3Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

@Slf4j
public abstract class FormFillerBase implements PDFFormFiller{
    protected PDDocument pdDocument;
    protected FormGenerationTask task;

    @Override
    public String saveResult() {
        try {
            String key = String.format("%s/%s/%s", task.getUserId(), task.getCaseId(), task.getFormName() + ".pdf");
            FormFillUtils.saveForm(this.pdDocument, key);
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            log.error("Error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveLocal() throws IOException {
        try {
            this.pdDocument.save(String.format("%s-local.pdf",  task.getFormName()));
        } catch (Exception e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
