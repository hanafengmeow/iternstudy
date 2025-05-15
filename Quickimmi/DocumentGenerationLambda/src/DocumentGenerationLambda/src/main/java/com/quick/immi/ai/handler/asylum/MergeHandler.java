package com.quick.immi.ai.handler.asylum;

import com.google.gson.Gson;
import com.quick.immi.ai.database.DocumentGenerationManager;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.handler.FormHandler;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.S3Utils;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

@Slf4j
public class MergeHandler extends FormHandler {

  private final DocumentGenerationManager documentGenerationManager;
  private final FormGenerationTask formGenerationTask;

  public MergeHandler(DocumentGenerationManager documentGenerationManager,
                      FormGenerationTask formGenerationTask) {
    super(documentGenerationManager, formGenerationTask);
    this.documentGenerationManager = documentGenerationManager;
    this.formGenerationTask = formGenerationTask;
  }

  @Override
  public Document generateDocument() {
    String metadata = formGenerationTask.getMetadata();
    MergePdfsDto mergePdfsDto = new Gson().fromJson(metadata, MergePdfsDto.class);

    List<PDDocument> pdfs = FormFillUtils.getPDFs(mergePdfsDto.getS3Locations());

    PDFMergerUtility pdfMerger = new PDFMergerUtility();
    PDDocument mergedPdf = new PDDocument();
    for (PDDocument pdf : pdfs) {
      try {
        pdfMerger.appendDocument(mergedPdf, pdf);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    String key = String.format("%s/%s/%s", formGenerationTask.getUserId(),
            formGenerationTask.getCaseId(), mergePdfsDto.getName() + ".pdf");
    FormFillUtils.saveForm(mergedPdf, key);

    String s3Location = S3Utils.getS3Location(S3Utils.getCurrentGeneratedDocumentBucket(), key);
    Document document = documentGenerationManager.getDocument(formGenerationTask.getDocumentId());
    log.info("create merged pdf successfully!!!");
    document.setS3Location(s3Location);
    return document;
  }
}
