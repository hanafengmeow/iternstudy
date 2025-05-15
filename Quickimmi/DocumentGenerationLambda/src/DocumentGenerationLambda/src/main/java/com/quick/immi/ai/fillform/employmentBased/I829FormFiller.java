package com.quick.immi.ai.fillform.employmentBased;

import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.employmentBased.i829.AdditionalInformation;
import com.quick.immi.ai.entity.employmentBased.i829.I829Table;
import com.quick.immi.ai.fillform.PDFFormFiller;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.S3Utils;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class I829FormFiller implements PDFFormFiller {
    private static final Logger logger = Logger.getLogger(I829FormFiller.class.getName());
    private PDDocument pdDocument;
    private I829Table i829Table;
    private Properties properties;
    private FormGenerationTask task;

    public I829FormFiller(I829Table i829Table, FormGenerationTask task) {
        this.i829Table = i829Table;
        this.task = task;
    }

    @Override
    public void init() {
        try {
            this.pdDocument = FormFillUtils.getForm(FormMapping.I829);
            this.properties = FormFillUtils.getFormMapping(FormMapping.I829);
        } catch (Exception e) {
            logger.severe("Failed to load form: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fillDocument() {
        try {
            FormFillUtils.fillAllTextField(pdDocument, properties, "", i829Table);
            FormFillUtils.fillAllTextField(pdDocument, properties, "attorneyOrRepresentativeInformation", i829Table.getAttorneyOrRepresentativeInformation());
            FormFillUtils.fillAllTextField(pdDocument, properties, "basisForPetition", i829Table.getBasisForPetition());
            FormFillUtils.fillAllTextField(pdDocument, properties, "biographicInformation", i829Table.getBiographicInformation());
            FormFillUtils.fillAllTextField(pdDocument, properties, "interpreterContactCertification", i829Table.getInterpreterContactCertification());
            FormFillUtils.fillAllTextField(pdDocument, properties, "jobCreatingEntityInformation", i829Table.getJobCreatingEntityInformation());
            FormFillUtils.fillAllTextField(pdDocument, properties, "jobCreationInformation", i829Table.getJobCreationInformation());
            FormFillUtils.fillAllTextField(pdDocument, properties, "personalInformation", i829Table.getPersonalInformation());
            FormFillUtils.fillAllTextField(pdDocument, properties, "petitionerContactCertification", i829Table.getPetitionerContactCertification());
            FormFillUtils.fillAllTextField(pdDocument, properties, "preparerContactCertification", i829Table.getPreparerContactCertification());
            FormFillUtils.fillAllTextField(pdDocument, properties, "regionalCenterNCEInformation", i829Table.getRegionalCenterNCEInformation());
            FormFillUtils.fillAllTextField(pdDocument, properties, "spouseInformation", i829Table.getSpouseInformation());
            FormFillUtils.fillAllTextFieldsInList(pdDocument, properties, "childrenInformation", i829Table.getChildrenInformation());
            fillAdditionalInformation(i829Table.getAdditionalInformation());
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to fill I829Form: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void fillAdditionalInformation(AdditionalInformation additionalInformation) throws IOException, IllegalAccessException {
        if (additionalInformation == null) {
            return;
        }
        FormFillUtils.fillAllTextField(pdDocument, properties, "additionalInformation", additionalInformation);
        FormFillUtils.fillAllTextFieldsInList(pdDocument, properties,
                "additionalInformation.addtionalInformation", additionalInformation.getAddtionalInformation());
    }

    @Override
    public String saveResult() {
        try {
            String key = String.format("%s/%s/%s.pdf", task.getUserId(),
                    task.getCaseId(), FormMapping.I829.getName() + ".pdf");
            FormFillUtils.saveForm(this.pdDocument, key);
            this.pdDocument.close();
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            logger.severe("Error occurred while saving result: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveLocal() throws IOException {
        try {
            FormFillUtils.saveFormToLocal(this.pdDocument, "generated-i-829.pdf");
            this.pdDocument.close();
        } catch (Exception e) {
            logger.severe("Error occurred while saving local result: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}