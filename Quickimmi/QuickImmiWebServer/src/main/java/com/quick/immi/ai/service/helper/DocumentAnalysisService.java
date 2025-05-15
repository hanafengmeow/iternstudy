/* (C) 2024 */
package com.quick.immi.ai.service.helper;

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient;
import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult;
import com.azure.ai.formrecognizer.documentanalysis.models.OperationResult;
import com.azure.core.util.polling.SyncPoller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DocumentAnalysisService {

  @Autowired private DocumentAnalysisClient documentAnalysisClient;

  @Autowired private S3Service s3Service;

  private OpenAIService openAIService;

  public String parse(String s3Bukcet, String key) {
    try {
      String url = s3Service.generatePresignedUrlForDownload(s3Bukcet, key);

      log.info(String.format("generate download url ===> %s", url));

      SyncPoller<OperationResult, AnalyzeResult> operationResultAnalyzeResultSyncPoller =
          documentAnalysisClient.beginAnalyzeDocumentFromUrl("prebuilt-document", url);

      AnalyzeResult finalResult = operationResultAnalyzeResultSyncPoller.getFinalResult();
      String content = finalResult.getContent();
      return content;
    } catch (Exception exp) {
      log.error(String.format("fail to parse S3bucket:%s, key:%s", s3Bukcet, key));
      throw new RuntimeException(exp);
    }
  }
}
