package com.quick.immi.ai.fillform.asylum;

import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.asylum.i589.*;
import com.quick.immi.ai.fillform.PDFFormFiller;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.S3Utils;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class I589FormFiller implements PDFFormFiller {
    private static final Logger logger = Logger.getLogger(I589FormFiller.class.getName());
    private PDDocument pdDocument;
    private I589Table i589Table;
    private Properties properties;
    private FormGenerationTask task;
    private List<PDDocument> supplementPDDocumentAList;
    private List<PDDocument> supplementPDDocumentBList;

    public I589FormFiller(I589Table i589Table, FormGenerationTask task) {
        this.i589Table = i589Table;
        this.task = task;
    }

    @Override
    public void init() {
        try {
            this.pdDocument = FormFillUtils.getForm(FormMapping.I589);
            this.properties = FormFillUtils.getFormMapping(FormMapping.I589);
            this.supplementPDDocumentAList = new ArrayList<>();
            this.supplementPDDocumentBList = new ArrayList<>();

            if (i589Table.getSupplementAs() != null) {
                for (int i = 1; i < i589Table.getSupplementAs().size(); i++) {
                    PDDocument temp = FormFillUtils.getForm(FormMapping.I589);
                    supplementPDDocumentAList.add(temp);
                }
            }

            if (i589Table.getSupplementBs() != null) {
                for (int i = 1; i < i589Table.getSupplementBs().size(); i++) {
                    PDDocument temp = FormFillUtils.getForm(FormMapping.I589);
                    supplementPDDocumentBList.add(temp);
                }
            }
        } catch (Exception e) {
            logger.severe("fail to load" + e);
        }
    }

    @Override
    public void fillDocument() {
        try {
            FormFillUtils.fillAllTextField(pdDocument, properties, "", i589Table);
            FormFillUtils.fillAllTextField(pdDocument, properties, "applicant", this.i589Table.getApplicant());
            fillApplicantEntryRecord(this.i589Table.getApplicant().getEntryRecords());

            FormFillUtils.fillAllTextField(pdDocument, properties, "spouse", this.i589Table.getSpouse());

            fillChildren(this.i589Table.getChildren());

            I589BackgroundFormFiller i589BackgroundFormFiller = new I589BackgroundFormFiller(pdDocument, properties);
            i589BackgroundFormFiller.fillBackground(this.i589Table.getBackground());

            FormFillUtils.fillAllTextField(pdDocument, properties, "applicationDetails",
                    this.i589Table.getApplicationDetails());

            fillSignature(this.i589Table.getSignature());
            FormFillUtils.fillAllTextField(pdDocument, properties, "declaration", this.i589Table.getDeclaration());

            fillSupplementA(this.i589Table.getSupplementAs());
            fillSupplementB(this.i589Table.getSupplementBs());
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("fail to fill I589Form" + e);
            throw new RuntimeException(e);
        }
    }

    private void fillSignature(YourSignature signature) throws IOException, IllegalAccessException {
        FormFillUtils.fillAllTextField(pdDocument, properties, "signature", signature);
        fillMembers(signature.getMembers());
    }

    private void fillMembers(List<Relationship> members) throws IOException, IllegalAccessException {
        if (members == null || members.isEmpty()) {
            return;
        }

        for (int i = 0; i < members.size(); i++) {
            Relationship relationship = members.get(i);
            String prefix = String.format("signature.members.%s", i + 1);
            FormFillUtils.fillAllTextField(pdDocument, properties, prefix, relationship);
        }
    }

    private void fillChildren(List<Child> children) throws IllegalAccessException, IOException {
        if (children == null) {
            return;
        }

        for (int i = 0; i < children.size(); i++) {
            Child child = children.get(i);
            String prefix = String.format("children.%s", i + 1);
            FormFillUtils.fillAllTextField(pdDocument, properties, prefix, child);
        }
    }

    private void fillApplicantEntryRecord(List<EntryRecord> entryRecords) throws IOException, IllegalAccessException {
        if (entryRecords == null) {
            return;
        }

        for (int i = 0; i < entryRecords.size(); i++) {
            EntryRecord entryRecord = entryRecords.get(i);
            String prefix = "applicant.entryRecords." + (i + 1);
            FormFillUtils.fillAllTextField(pdDocument, properties, prefix, entryRecord);
        }
    }

    private void fillSupplementA(List<SupplementA> supplementAS) throws IOException, IllegalAccessException {
        if (supplementAS == null || supplementAS.isEmpty()) {
            return;
        }
        SupplementA supplementA = supplementAS.get(0);
        FormFillUtils.fillAllTextField(pdDocument, properties, "supplement.a", supplementA);
        fillSupplementAChildren(pdDocument, supplementA.getChildren());

        for (int i = 1; i < supplementAS.size(); i++) {
            SupplementA tempSupplementA = supplementAS.get(i);
            FormFillUtils.fillAllTextField(supplementPDDocumentAList.get(i - 1), properties, "supplement.a", tempSupplementA);
            fillSupplementAChildren(supplementPDDocumentAList.get(i - 1), tempSupplementA.getChildren());
        }
    }

    private void fillSupplementAChildren(PDDocument pdDocumentTemp, List<Child> children)
            throws IOException, IllegalAccessException {
        if (children == null || children.isEmpty()) {
            return;
        }

        for (int i = 0; i < children.size(); i++) {
            Child child = children.get(i);
            FormFillUtils.fillAllTextField(pdDocumentTemp, properties, String.format("supplement.a.children.%s", i + 1), child);
        }
    }

    private void fillSupplementB(List<SupplementB> supplementBS) throws IOException, IllegalAccessException {
        if (supplementBS == null || supplementBS.isEmpty()) {
            return;
        }
        SupplementB supplementB = supplementBS.get(0);
        FormFillUtils.fillAllTextField(this.pdDocument, properties, "supplement.b", supplementB);

        for (int i = 1; i < supplementBS.size(); i++) {
            SupplementB tempSupplementB = supplementBS.get(i);
            FormFillUtils.fillAllTextField(supplementPDDocumentBList.get(i - 1), properties, "supplement.b", tempSupplementB);
        }
    }

    @Override
    public String saveResult() {
        try {
            String key = String.format("%s/%s/%s.pdf", task.getUserId(),
                    task.getCaseId(), FormMapping.I589.getName());
            FormFillUtils.saveForm(this.pdDocument, key);

            this.pdDocument.close();
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String saveSupplementResult() {
        if (supplementPDDocumentAList.isEmpty() && supplementPDDocumentBList.isEmpty()) {
            logger.info("No supplementPDDocument found...skip generating supplement pdf");
            return null;
        }
        try {
            PDDocument temp = new PDDocument();
            for (PDDocument a : supplementPDDocumentAList) {
                temp.importPage(a.getPage(10));
            }
            for (PDDocument b : supplementPDDocumentBList) {
                FormFillUtils.setReadonly(b);
                temp.importPage(b.getPage(11));
            }

            String key = String.format("%s/%s/%s.pdf", task.getUserId(),
                    task.getCaseId(), FormMapping.I589_SUPPLEMENT.getName());
            FormFillUtils.saveForm(temp, key);

            for (PDDocument a : supplementPDDocumentAList) {
                a.close();
            }
            for (PDDocument b : supplementPDDocumentBList) {
                b.close();
            }
            temp.close();
            return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveLocal() throws IOException {
        try {
            FormFillUtils.saveFormToLocal(this.pdDocument, "generated-i-589.pdf");
            this.pdDocument.close();
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage());
        }
    }

    public void saveLocalSupplementResult() throws IOException {
        if (supplementPDDocumentAList.isEmpty() && supplementPDDocumentBList.isEmpty()) {
            logger.info("No Supplement result");
            return;
        }
        try {
            PDDocument temp = new PDDocument();
            for (PDDocument a : supplementPDDocumentAList) {
                temp.importPage(a.getPage(10));
            }
            for (PDDocument b : supplementPDDocumentBList) {
                FormFillUtils.setReadonly(b);
                temp.importPage(b.getPage(11));
            }

            String key = String.format("%s/%s/%s.pdf", task.getUserId(),
                    task.getCaseId(), FormMapping.I589_SUPPLEMENT.getName());

            FormFillUtils.saveFormToLocal(temp, "generated-i-589-supplement.pdf");

            for (PDDocument a : supplementPDDocumentAList) {
                a.close();
            }
            for (PDDocument b : supplementPDDocumentBList) {
                b.close();
            }
            temp.close();
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
