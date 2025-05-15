package com.quick.immi.ai.fillform;

import com.quick.immi.ai.entity.cot.COTTable;
import java.io.IOException;

import com.quick.immi.ai.utils.S3Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;

import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.utils.FormFillUtils;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

@Slf4j
public class COTFormFiller implements PDFFormFiller {

  private COTTable cotTable;
  private FormGenerationTask task;
  private PDDocument pdDocument;

  public COTFormFiller(COTTable cotTable, FormGenerationTask task) {
    this.cotTable = cotTable;
    this.task = task;
  }

  @Override
  public void init() {
    try {
      this.pdDocument = FormFillUtils.getForm(FormMapping.COT);
    } catch (Exception e) {
      log.error("fail to get cot form", e);
    }
  }

  @Override
  public void fillDocument() {
    try {
      PDAcroForm acroForm = pdDocument.getDocumentCatalog().getAcroForm();

      // Ensure the default resources are set in the AcroForm
      if (acroForm.getDefaultResources() == null) {
        PDResources resources = new PDResources();
        resources.put(COSName.getPDFName("Helv"), PDType1Font.HELVETICA);
        acroForm.setDefaultResources(resources);
      } else if (acroForm.getDefaultResources().getFont(COSName.getPDFName("Helv")) == null) {
        acroForm.getDefaultResources().put(COSName.getPDFName("Helv"), PDType1Font.HELVETICA);
      }

      log.info("Filling COTForm .....................");
      fillField(acroForm, "name", cotTable.getFullName());
      fillField(acroForm, "languageFrom", "Chinese");
      fillField(acroForm, "languageTo", "English");
      fillField(acroForm, "fileName", cotTable.getDocumentName());
//      fillField(acroForm, "date", "09/13/2023");
//      fillField(acroForm, "signature", "Joanne Yuan");
      fillField(acroForm, "address", cotTable.getAddress());
      fillField(acroForm, "phone", cotTable.getPhone());
      fillField(acroForm, "email", cotTable.getEmail());
      log.info("Filling COTForm success");
    } catch (Exception e) {
      log.error("fail to fill COTForm", e);
    }
  }

  private static void fillField(PDAcroForm acroForm, String fieldName, String value) throws IOException {
    PDField field = acroForm.getField(fieldName);
    if (field != null) {
      field.setValue(value);
    }
  }

  @Override
  public String saveResult() {
    try {
      String key = String.format("%s/%s/%s", task.getUserId(),
          task.getCaseId(), task.getFormName() + ".pdf");
      FormFillUtils.saveForm(this.pdDocument, key);
      return S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
    } catch (Exception e) {
      log.error("fail to save result",  e);
      throw new RuntimeException(e);
    }
  }

  public void saveLocal() throws IOException {
    try {
      this.pdDocument.save("cot-test.pdf");
    } catch (Exception e) {
      log.error("fail to save result",  e);
    }
  }
}
