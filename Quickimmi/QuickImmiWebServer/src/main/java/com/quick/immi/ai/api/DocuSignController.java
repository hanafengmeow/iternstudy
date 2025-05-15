/* (C) 2024 */
package com.quick.immi.ai.api;

import static com.quick.immi.ai.constant.Constants.*;
import static com.quick.immi.ai.exception.Constant.SERVER_SIDE_ERROR;

import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.client.auth.OAuth;
import com.docusign.esign.model.EnvelopeDefinition;
import com.docusign.esign.model.RecipientViewRequest;
import com.docusign.esign.model.ViewUrl;
import com.quick.immi.ai.annotation.Login;
import com.quick.immi.ai.dto.response.ResponseDto;
import com.quick.immi.ai.service.EmbeddedSigningService;
import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/docusign")
@Slf4j
public class DocuSignController {

  @Autowired private ResourceLoader resourceLoader;

  @Autowired private EmbeddedSigningService embeddedSigningService;

  private ApiClient apiClient;
  private byte[] privateKeyBytes;

  @Value("${docusign.clientId}")
  private String clientId;

  @Value("${docusign.userId}")
  private String userId;

  @Value("${docusign.oauthBasePath}")
  private String oauthBasePath;

  @Value("${docusign.restApiUrl}")
  private String restApiUrl;

  @Value("${docusign.rsaKeyFile}")
  private String rsaKeyFile;

  @Value("${docusign.accountId}")
  private String accountId;

  @Value("${docusign.s3BucketName}")
  private String docuSignPrivateKeyS3BucketName;

  @Value("${docusign.s3Key}")
  private String docuSignPrivateKeyS3Key;

  @PostConstruct
  public void init() {
    apiClient = new ApiClient(restApiUrl);
    apiClient.setOAuthBasePath(oauthBasePath);
    privateKeyBytes =
        embeddedSigningService.getDocuSignPrivateKeyBytes(
            docuSignPrivateKeyS3BucketName, docuSignPrivateKeyS3Key);
  }

  @GetMapping("/startSigning")
  @Login
  public ResponseEntity<ResponseDto<RedirectView>> startSigning(
      @RequestParam("documentId") String documentId,
      @RequestParam("signerEmail") String signerEmail,
      @RequestParam("signerName") String signerName,
      @RequestParam("signerClientId") String signerClientId,
      @RequestParam("anchorOffsetX") int anchorOffsetX,
      @RequestParam("anchorOffsetY") int anchorOffsetY,
      @RequestParam("s3BucketName") String s3BucketName,
      @RequestParam("s3Key") String s3Key,
      @RequestParam("documentName") String documentName,
      @RequestParam("returnUrl") String returnUrl,
      @RequestParam("pingUrl") String pingUrl) {
    try {
      setTokenForApiClient();

      // Create the envelope definition
      EnvelopeDefinition envelope =
          embeddedSigningService.makeEnvelope(
              signerEmail,
              signerName,
              signerClientId,
              anchorOffsetY,
              anchorOffsetX,
              s3BucketName,
              s3Key,
              documentName,
              documentId);

      // Call DocuSign to create the envelope
      String envelopeId = embeddedSigningService.createEnvelope(apiClient, accountId, envelope);

      // Create the recipient view, the embedded signing
      RecipientViewRequest viewRequest =
          embeddedSigningService.makeRecipientViewRequest(
              signerEmail, signerName, signerClientId, returnUrl, pingUrl);

      ViewUrl viewUrl =
          embeddedSigningService.embeddedSigning(apiClient, accountId, envelopeId, viewRequest);

      return ResponseEntity.ok().body(ResponseDto.newInstance(new RedirectView(viewUrl.getUrl())));
    } catch (Exception exp) {
      log.error(String.format("Fail to start signing process for documentId  %s", documentId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Fail to start signing process %s", exp.getMessage())));
    }
  }

  private void setTokenForApiClient() throws ApiException, IOException {
    ArrayList<String> scopes = new ArrayList<>();
    scopes.add(SIGNATURE_SCOPE);
    scopes.add(IMPERSONATION_SCOPE);
    try {
      OAuth.OAuthToken oAuthToken =
          apiClient.requestJWTUserToken(
              clientId, userId, scopes, privateKeyBytes, TOKEN_EXPIRATION_SECONDS);
      String accessToken = oAuthToken.getAccessToken();
      apiClient.addDefaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    } catch (Exception e) {
      log.error("Failed to get security token for docuSign client", e);
      throw e;
    }
  }
}
