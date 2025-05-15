/* (C) 2024 */
package com.quick.immi.ai.api;

import static com.quick.immi.ai.exception.Constant.CLIENT_SIDE_ERROR;
import static com.quick.immi.ai.exception.Constant.SERVER_SIDE_ERROR;

import com.quick.immi.ai.annotation.CaseVerifier;
import com.quick.immi.ai.annotation.Login;
import com.quick.immi.ai.dto.common.DocumentDto;
import com.quick.immi.ai.dto.request.GeneratePresignedUrlRequestDto;
import com.quick.immi.ai.dto.request.UpdateDocumentStatusDto;
import com.quick.immi.ai.dto.response.GeneratePresignedUrlResponseDto;
import com.quick.immi.ai.dto.response.ResponseDto;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.document.MarriageCertificate;
import com.quick.immi.ai.entity.document.Passport;
import com.quick.immi.ai.service.DocumentMgtService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/document")
@Slf4j
public class DocumentController {

  @Autowired private DocumentMgtService documentMgtService;

  // for uploading file
  @PostMapping("/generatePresignedUrl/put")
  @Login
  public ResponseEntity<ResponseDto<GeneratePresignedUrlResponseDto>> generatePresignedUrl(
      @RequestHeader("role") Role role, @RequestBody GeneratePresignedUrlRequestDto requestDto) {
    try {
      log.info(String.format("generatePresignedUrl api receive {%s}", requestDto));
      GeneratePresignedUrlResponseDto result =
          documentMgtService.generateUploadPresignedUrlForUpload(requestDto, role);
      log.info(String.format("generatePresignedUrl api return %s", result));
      return ResponseEntity.ok().body(ResponseDto.newInstance(result));
    } catch (Exception e) {
      log.error(String.format("fail to generatePresignedUrl for given request %s", requestDto), e);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Fail to generate upload s3 link due to %s", e.getMessage())));
    }
  }

  @PostMapping("/update")
  @Login
  public ResponseEntity<ResponseDto<Boolean>> update(
      @RequestHeader("role") Role role,
      @RequestBody UpdateDocumentStatusDto updateDocumentStatusDto) {
    try {
      log.info(String.format("update document api receive {%s}", updateDocumentStatusDto));
      documentMgtService.updateDocumentStatus(
          updateDocumentStatusDto.getDocumentId(),
          updateDocumentStatusDto.getDocumentStatus(),
          updateDocumentStatusDto.getManualOverride());
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (Exception e) {
      log.error(
          String.format("fail to update document %s", updateDocumentStatusDto.getDocumentId()), e);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format(
                      String.format(
                          "Fail to update document %s due to",
                          updateDocumentStatusDto.getDocumentId()),
                      e.getMessage())));
    }
  }

  @PostMapping("/delete")
  @Login
  public ResponseEntity<ResponseDto<Boolean>> delete(
      @RequestHeader("role") Role Role, @RequestParam("documentId") Long documentId) {
    try {
      log.info(String.format("delete document for {%s}", documentId));
      documentMgtService.delete(documentId);
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (Exception e) {
      log.error(String.format("fail to delete document %s", documentId), e);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format(
                      String.format("Fail to delete document %s due to", documentId),
                      e.getMessage())));
    }
  }

  @PostMapping("/parse/passport")
  @Login
  public ResponseEntity<ResponseDto<Passport>> parsePassport(
      @RequestHeader("role") Role Role, @RequestParam("documentId") Long documentId) {
    try {
      // Save the file to the file system or perform further processing
      // For demonstration purposes, we're just printing the file name
      long start = System.currentTimeMillis();
      Optional<Passport> passportOptional = documentMgtService.parsePassport(documentId);
      if (passportOptional.isEmpty()) {
        return ResponseEntity.status(400)
            .body(
                ResponseDto.newInstance(
                    CLIENT_SIDE_ERROR,
                    String.format(
                        String.format(
                            "Fail to parse the document %s due to no exist document",
                            documentId))));
      }

      log.info(
          String.format(
              "time cost for parse passport --- %s", (System.currentTimeMillis() - start) / 1000));

      return ResponseEntity.ok().body(ResponseDto.newInstance(passportOptional.get()));
    } catch (Exception e) {
      log.error(String.format("fail to parse %s", documentId), e);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Fail to parse parsePassport %s", e.getMessage())));
    }
  }

  @PostMapping("/parse/marriageCertificate")
  @Login
  public ResponseEntity<ResponseDto<MarriageCertificate>> parseMarriageCertificate(
      @RequestHeader("role") Role Role, @RequestParam("documentId") Long documentId) {
    try {
      // Save the file to the file system or perform further processing
      // For demonstration purposes, we're just printing the file name
      long start = System.currentTimeMillis();
      Optional<MarriageCertificate> marriageCertificateOptional =
          documentMgtService.parseMarriageLicense(documentId);
      if (marriageCertificateOptional.isEmpty()) {
        return ResponseEntity.status(400)
            .body(
                ResponseDto.newInstance(
                    CLIENT_SIDE_ERROR,
                    String.format(
                        String.format(
                            "Fail to parseMarriageCertificate %s due to no exist document",
                            documentId))));
      }

      log.info(
          String.format(
              "time cost for parseMarriageCertificate --- %s",
              (System.currentTimeMillis() - start) / 1000));

      return ResponseEntity.ok().body(ResponseDto.newInstance(marriageCertificateOptional.get()));
    } catch (Exception e) {
      log.error(String.format("fail to parse %s", documentId), e);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Fail to parseMarriageCertificate %s", e.getMessage())));
    }
  }

  @GetMapping("/get")
  @CaseVerifier
  @Login
  public ResponseEntity<ResponseDto<DocumentDto>> get(
      @RequestHeader("role") Role Role, @RequestParam("documentId") Long documentId) {
    try {
      Optional<DocumentDto> documentDto = documentMgtService.get(documentId);
      if (documentDto.isEmpty()) {
        return ResponseEntity.status(400)
            .body(
                ResponseDto.newInstance(
                    CLIENT_SIDE_ERROR,
                    String.format(String.format("Document  Doesn't exist  %s", documentId))));
      }
      return ResponseEntity.ok().body(ResponseDto.newInstance(documentDto.get()));
    } catch (Exception exp) {
      log.error(String.format("fail to get document for documentId  %s", documentId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("fail to get document for documentId  %s", exp.getMessage())));
    }
  }

  @Login
  @CaseVerifier
  @GetMapping("/downloadDocuments")
  public ResponseEntity<ResponseDto<String>> downloadDocuments(
      @RequestHeader("role") Role Role,
      @RequestParam("caseId") Long caseId,
      @RequestParam("generationType") GenerationType generationType) {
    try {
      String presigedUrlForDownload =
          documentMgtService.fetchDocumentsAndZipFiles(caseId, generationType);
      return ResponseEntity.ok().body(ResponseDto.newInstance(presigedUrlForDownload));
    } catch (Exception exp) {
      log.error(
          String.format(
              "fail to download documents for given caseId %s and generationType %s",
              caseId, generationType),
          exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("fail to download documents due to %s", exp.getMessage())));
    }
  }

  @GetMapping("/list")
  @Login
  @CaseVerifier
  public ResponseEntity<ResponseDto<List<DocumentDto>>> list(
      @RequestHeader("role") Role Role,
      @RequestParam Long caseId,
      @RequestParam(required = false) DocumentType documentType,
      @RequestParam(required = false) Identify identify,
      @RequestParam(required = false) Boolean autoGenerated,
      @RequestParam(required = false) GenerationType generationType) {
    try {
      List<DocumentDto> result =
          documentMgtService.list(
              caseId,
              documentType == null ? null : documentType.getName(),
              identify == null ? null : identify.getValue(),
              autoGenerated,
              generationType == null ? null : generationType.getValue());
      return ResponseEntity.ok().body(ResponseDto.newInstance(result));
    } catch (Exception exp) {
      log.error(String.format("fail to list documents for case %s", caseId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Fail to list documents for case %s", exp.getMessage())));
    }
  }

  @PostMapping("/generatePresignedUrlByDocumentId/put")
  @Login
  public ResponseEntity<ResponseDto<String>> generatePresignedUrlByDocumentId(
      @RequestHeader("role") Role role,
      @RequestBody
          GeneratePresignedUrlByDocumentIdRequestDto generatePresignedUrlByDocumentIdRequestDto) {
    try {
      String presignedUrl =
          documentMgtService.generateUploadPresignedUrlForUploadByDocumentId(
              generatePresignedUrlByDocumentIdRequestDto, role);
      return ResponseEntity.ok().body(ResponseDto.newInstance(presignedUrl));
    } catch (Exception exp) {
      log.error("Failed to generatePresignedUrlByDocumentId", exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format(
                      "Failed to generatePresignedUrlByDocumentId due to %s", exp.getMessage())));
    }
  }

  @GetMapping("/getSupportedDocumentTypes")
  @Login
  public ResponseEntity<ResponseDto<List<DocumentType>>> getSupportedDocumentTypes() {
    try {
      List<DocumentType> result = new ArrayList<>();
      DocumentType[] values = DocumentType.values();
      for (DocumentType documentType : values) {
        if (documentType.getGenerationType() != null
            && documentType.getGenerationType() == GenerationType.USER_UPLOADED) {
          result.add(documentType);
        }
      }
      return ResponseEntity.ok().body(ResponseDto.newInstance(result));
    } catch (Exception exp) {
      log.error("Failed to getSupportedDocumentTypes", exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format(
                      "Failed to getSupportedDocumentTypes due to %s", exp.getMessage())));
    }
  }
}
