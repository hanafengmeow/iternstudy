/* (C) 2024 */
package com.quick.immi.ai.service.helper;

import com.google.gson.Gson;
import com.quick.immi.ai.dto.response.AsylumApplicationCaseDto;
import com.quick.immi.ai.entity.Document;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EntityCacheService {
  @Autowired private RedisService redisService;

  public void deleteApplicationCase(Long caseId) {
    redisService.del(getApplicationCaseKey(caseId));
  }

  private boolean enableCache = false;

  public void saveApplicationCase(Long caseId, AsylumApplicationCaseDto applicationCase) {
    if (!enableCache) {
      return;
    }
    String value = new Gson().toJson(applicationCase);
    boolean result = redisService.set(getApplicationCaseKey(caseId), value, 86400 * 30);

    if (!result) {
      log.warn(String.format(String.format("fail to save application case %s", caseId)));
    }
  }

  public AsylumApplicationCaseDto getApplicationCase(Long caseId) {
    if (!enableCache) {
      return null;
    }
    String key = getApplicationCaseKey(caseId);
    String value = redisService.get(key);
    if (value == null) {
      return null;
    }
    AsylumApplicationCaseDto asylumApplicationCaseDto =
        new Gson().fromJson(value, AsylumApplicationCaseDto.class);
    return asylumApplicationCaseDto;
  }

  public void saveAuthCase(String role, Long caseId, String cognitoUserName) {
    boolean result = redisService.set(getAuthCaseKey(role, caseId), cognitoUserName, 12 * 60 * 60);

    if (!result) {
      log.warn(String.format(String.format("fail to save auth case %s", caseId)));
    }
  }

  public String getAuthCase(String role, Long caseId) {
    String key = getAuthCaseKey(role, caseId);
    return redisService.get(key);
  }

  public void saveAuth(String role, String token, String cognitoName) {
    boolean result = redisService.set(getAuthKey(role, token), cognitoName, 12 * 60 * 60);

    if (!result) {
      log.warn(String.format(String.format("fail to save auth token %s", token)));
    }
  }

  public String getAuth(String role, String token) {
    if (!enableCache) {
      return null;
    }
    String key = getAuthKey(role, token);
    String value = redisService.get(key);
    if (value == null) {
      return null;
    }
    return value;
  }

  public void saveDocument(Long documentId, Document document) {
    if (!enableCache) {
      return;
    }
    String value = new Gson().toJson(document);
    boolean result = redisService.set(getDocumentKey(documentId), value, 86400 * 30);

    if (!result) {
      log.warn(String.format(String.format("fail to save Document case %s", documentId)));
    }
  }

  public Document getDocument(Long documentId) {
    if (!enableCache) {
      return null;
    }
    String key = getDocumentKey(documentId);
    String value = redisService.get(key);
    if (value == null) {
      return null;
    }
    Document document = new Gson().fromJson(value, Document.class);
    return document;
  }

  public void deleteDocument(Long documentId) {
    redisService.del(getDocumentKey(documentId));
  }

  public void flushAll() {
    redisService.flushAll();
  }

  public String getApplicationCaseKey(Long caseId) {
    return "case:" + caseId;
  }

  public String getDocumentKey(Long documentId) {
    return "document:" + documentId;
  }

  public String getAuthCaseKey(String role, Long caseId) {
    return String.format("%s:%s", role, caseId);
  }

  public String getAuthKey(String role, String token) {
    return String.format("%s:%s", role, token);
  }
}
