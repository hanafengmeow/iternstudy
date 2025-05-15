/* (C) 2024 */
package com.quick.immi.ai.api;

import static com.quick.immi.ai.exception.Constant.SERVER_SIDE_ERROR;

import com.quick.immi.ai.annotation.Login;
import com.quick.immi.ai.dto.response.ResponseDto;
import com.quick.immi.ai.service.helper.EntityCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cache")
@Slf4j
public class CacheMgtController {

  @Autowired private EntityCacheService entityCacheService;

  @GetMapping("/case/delete")
  @Login
  public ResponseEntity<ResponseDto<Boolean>> deleteCase(@RequestParam("caseId") Long caseId) {
    try {
      entityCacheService.deleteApplicationCase(caseId);
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (Exception exp) {
      log.error(String.format("fail to delete cache case for case id: %s", caseId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Failed to delete case from cache due to %s", exp.getMessage())));
    }
  }

  @GetMapping("/flushAll")
  @Login
  public ResponseEntity<ResponseDto<Boolean>> deleteDocument() {
    try {
      entityCacheService.flushAll();
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (Exception exp) {
      log.error(String.format("fail to flush cache"), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("fail to flush cache due to %s", exp.getMessage())));
    }
  }

  @GetMapping("/document/delete")
  @Login
  public ResponseEntity<ResponseDto<Boolean>> deleteDocument(
      @RequestParam("documentId") Long documentId) {
    try {
      entityCacheService.deleteDocument(documentId);
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (Exception exp) {
      log.error(String.format("fail to delete document for document id: %s", documentId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format(
                      "fail to delete document for document id due to %s", exp.getMessage())));
    }
  }
}
