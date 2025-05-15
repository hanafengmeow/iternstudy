package com.quick.immi.ai.fillform;

import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.eoir28.*;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.S3Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class EOIR28FormFiller implements PDFFormFiller{

    private EOIR28Table eoir28Table;
    private FormGenerationTask task;
    private PDDocument pdDocument;
    private Properties properties;

    public EOIR28FormFiller(EOIR28Table eoir28Table, FormGenerationTask task) {
        this.eoir28Table = eoir28Table;
        this.task = task;
    }

    @Override
    public void init() {
        try {
            this.pdDocument = FormFillUtils.getForm(FormMapping.EOIR28);
            this.properties = FormFillUtils.getFormMapping(FormMapping.EOIR28);
        } catch (Exception e) {
            log.error("fail to load EOIR28", e);
        }
    }

    @Override
    public void fillDocument() {
        try {
            FormFillUtils.fillAllTextField(pdDocument, properties, "partyInformation",
                    this.eoir28Table.getPartyInformation());
            FormFillUtils.fillAllTextField(pdDocument, properties, "representation",
                    this.eoir28Table.getRepresentation());
            FormFillUtils.fillAllTextField(pdDocument, properties, "attorneyInfo",
                    this.eoir28Table.getAttorneyInfo());
            FormFillUtils.fillAllTextField(pdDocument, properties, "typeOfAppearance",
                    this.eoir28Table.getTypeOfAppearance());
            FormFillUtils.fillAllTextField(pdDocument, properties, "proofOfService",
                    this.eoir28Table.getProofOfService());
        } catch (Exception e) {
            log.error("fail to fill EOIR28 document", e);
        }
    }

    @Override
    public String saveResult() {
        try {
            String key = String.format("%s/%s/%s", task.getUserId(),
                    task.getCaseId(), FormMapping.EOIR28.getName() + ".pdf");
            FormFillUtils.saveForm(this.pdDocument, key);
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            log.error("Error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveLocal() throws IOException {
        try {
            this.pdDocument.save("EOIR28-local.pdf");
        } catch (Exception e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
