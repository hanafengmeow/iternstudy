/* (C) 2024 */
package com.quick.immi.ai.service;

import static com.quick.immi.ai.constant.Constants.*;

import com.azure.core.util.BinaryData;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.*;
import com.quick.immi.ai.service.helper.EnvelopeHelpers;
import com.quick.immi.ai.service.helper.S3Service;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmbeddedSigningService {

  @Autowired private S3Service s3Service;

  public ViewUrl embeddedSigning(
      ApiClient apiClient, String accountId, String envelopeId, RecipientViewRequest viewRequest)
      throws ApiException {
    EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);

    return envelopesApi.createRecipientView(accountId, envelopeId, viewRequest);
  }

  public String createEnvelope(ApiClient apiClient, String accountId, EnvelopeDefinition envelope)
      throws ApiException {
    EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);
    EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope(accountId, envelope);

    return envelopeSummary.getEnvelopeId();
  }

  // ds-snippet-start:eSign1Step4
  public RecipientViewRequest makeRecipientViewRequest(
      String signerEmail,
      String signerName,
      String clientUserId,
      String dsReturnURL,
      String pingURL) {
    RecipientViewRequest viewRequest = new RecipientViewRequest();
    // Set the url where you want the recipient to go once they are done signing
    // should typically be a callback route somewhere in your app.
    // The query parameter is included as an example of how
    // to save/recover state information during the redirect to
    // the DocuSign signing. It's usually better to use
    // the session mechanism of your web framework. Query parameters
    // can be changed/spoofed very easily.
    viewRequest.setReturnUrl(dsReturnURL + STATE_VALUE_CASE_DETAIL);

    // How has your app authenticated the user? In addition to your app's
    // authentication, you can include authenticate steps from DocuSign.
    // Eg, SMS authentication
    viewRequest.setAuthenticationMethod(AUTHENTICATION_METHOD_NONE);

    // Recipient information must match embedded recipient info
    // we used to create the envelope.
    viewRequest.setEmail(signerEmail);
    viewRequest.setUserName(signerName);
    viewRequest.setClientUserId(clientUserId);

    // DocuSign recommends that you redirect to DocuSign for the
    // embedded signing. There are multiple ways to save state.
    // To maintain your application's session, use the pingUrl
    // parameter. It causes the DocuSign signing web page
    // (not the DocuSign server) to send pings via AJAX to your app.
    // NOTE: The pings will only be sent if the pingUrl is an https address
    viewRequest.setPingFrequency(PING_FREQUENCY_600_SECONDS); // seconds
    viewRequest.setPingUrl(pingURL);

    return viewRequest;
  }

  public EnvelopeDefinition makeEnvelope(
      String signerEmail,
      String signerName,
      String signerClientId,
      Integer anchorOffsetY,
      Integer anchorOffsetX,
      String s3BucketName,
      String s3Key,
      String documentName,
      String docId) {
    // Create a signer recipient to sign the document, identified by name and email
    // We set the clientUserId to enable embedded signing for the recipient
    Signer signer = new Signer();
    signer.setEmail(signerEmail);
    signer.setName(signerName);
    signer.clientUserId(signerClientId);
    signer.recipientId(signerClientId);
    signer.setTabs(
        EnvelopeHelpers.createSingleSignerTab(ANCHOR_TAG_SN1, anchorOffsetY, anchorOffsetX));

    // Add the recipient to the envelope object
    Recipients recipients = new Recipients();
    recipients.setSigners(Collections.singletonList(signer));

    EnvelopeDefinition envelopeDefinition = new EnvelopeDefinition();
    envelopeDefinition.setEmailSubject(EMAIL_SUBJECT_SIGN_DOCUMENT);
    envelopeDefinition.setRecipients(recipients);

    // Fetch the file from S3 as binary data
    BinaryData binaryData = s3Service.getS3BinaryData(s3BucketName, s3Key);
    byte[] buffer = binaryData.toBytes();

    Document doc = EnvelopeHelpers.createDocument(buffer, documentName, DOCUMENT_TYPE_PDF, docId);
    envelopeDefinition.setDocuments(Collections.singletonList(doc));

    // Request that the envelope be sent by setting |status| to "sent".
    // To request that the envelope be created as a draft, set to "created"
    envelopeDefinition.setStatus(EnvelopeHelpers.ENVELOPE_STATUS_SENT);

    return envelopeDefinition;
  }

  public byte[] getDocuSignPrivateKeyBytes(String s3BucketName, String s3Key) {
    BinaryData binaryData = s3Service.getS3BinaryData(s3BucketName, s3Key);
    return binaryData.toBytes();
  }
}
