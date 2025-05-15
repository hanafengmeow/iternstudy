package com.quick.immi.ai.fillform.employmentBased;

import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.employmentBased.eta9089.ETA9089Table;
import com.quick.immi.ai.fillform.PDFFormFiller;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.S3Utils;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class eta9089FormFiller implements PDFFormFiller {
    private static final Logger logger = Logger.getLogger(eta9089FormFiller.class.getName());
    private PDDocument pdDocument;
    private ETA9089Table eta9089Table;
    private Properties properties;
    private FormGenerationTask task;

    public eta9089FormFiller(ETA9089Table eta9089Table, FormGenerationTask task) {
        this.eta9089Table = eta9089Table;
        this.task = task;
    }

    @Override
    public void init() {
        try {
            this.pdDocument = FormFillUtils.getForm(FormMapping.ETA9089);
            this.properties = FormFillUtils.getFormMapping(FormMapping.ETA9089);
        } catch (Exception e) {
            logger.severe("Failed to load form: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fillDocument() {
        try {
            FormFillUtils.fillAllTextField(pdDocument, properties, "", eta9089Table);
            FormFillUtils.fillAllTextField(pdDocument, properties, "refilingInstructions", eta9089Table.getRefilingInstructions());
            FormFillUtils.fillAllTextField(pdDocument, properties, "scheduleASheepherder", eta9089Table.getScheduleASheepherder());
            FormFillUtils.fillAllTextField(pdDocument, properties, "employer", eta9089Table.getEmployer());
            FormFillUtils.fillAllTextField(pdDocument, properties, "employerContact", eta9089Table.getEmployerContact());
            FormFillUtils.fillAllTextField(pdDocument, properties, "agentAttorney", eta9089Table.getAgentAttorney());
            FormFillUtils.fillAllTextField(pdDocument, properties, "prevailingWage", eta9089Table.getPrevailingWage());
            FormFillUtils.fillAllTextField(pdDocument, properties, "wageOffer", eta9089Table.getWageOffer());
            FormFillUtils.fillAllTextField(pdDocument, properties, "jobOpportunity", eta9089Table.getJobOpportunity());
            FormFillUtils.fillAllTextField(pdDocument, properties, "recruitment", eta9089Table.getRecruitment());
            FormFillUtils.fillAllTextField(pdDocument, properties, "alienInformation", eta9089Table.getAlienInformation());
            FormFillUtils.fillAllTextField(pdDocument, properties, "alienWorkExperience", eta9089Table.getAlienWorkExperience());
            FormFillUtils.fillAllTextField(pdDocument, properties, "alienDeclaration", eta9089Table.getAlienDeclaration());
            FormFillUtils.fillAllTextField(pdDocument, properties, "preparerDeclaration", eta9089Table.getPreparerDeclaration());
            FormFillUtils.fillAllTextField(pdDocument, properties, "employerDeclaration", eta9089Table.getEmployerDeclaration());
            FormFillUtils.fillAllTextField(pdDocument, properties, "addendum", eta9089Table.getAddendum());
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Failed to fill ETA9089Form: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String saveResult() {
        try {
            String key = String.format("%s/%s/%s.pdf", task.getUserId(),
                    task.getCaseId(), FormMapping.ETA9089.getName() + ".pdf");
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
            FormFillUtils.saveFormToLocal(this.pdDocument, "generated-eta-9089.pdf");
            this.pdDocument.close();
        } catch (Exception e) {
            logger.severe("Error occurred while saving local result: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}