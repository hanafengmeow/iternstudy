/* (C) 2024 */
package com.quick.immi.ai.service.helper;

import com.azure.core.util.BinaryData;
import com.docusign.esign.model.Document;
import com.docusign.esign.model.SignHere;
import com.docusign.esign.model.Tabs;
import java.util.Arrays;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

@Slf4j
public class EnvelopeHelpers {
  public static final String ENVELOPE_STATUS_SENT = "sent";

  /**
   * Create SignHere (see {@link SignHere}) field (also known as tabs) on the documents using anchor
   * (autoPlace) positioning.
   *
   * @param anchorString the anchor string; the DocuSign platform searches throughout your
   *     envelope's documents for matching anchor strings
   * @param yOffsetPixels the y offset of anchor in pixels
   * @param xOffsetPixels the x offset of anchor in pixels
   * @return the {@link SignHere} object
   */
  public static SignHere createSignHere(String anchorString, int yOffsetPixels, int xOffsetPixels) {
    SignHere signHere = new SignHere();
    signHere.setAnchorString(anchorString);
    signHere.setAnchorUnits("pixels");
    signHere.setAnchorYOffset(String.valueOf(yOffsetPixels));
    signHere.setAnchorXOffset(String.valueOf(xOffsetPixels));
    return signHere;
  }

  /**
   * Creates {@link SignHere} fields (also known as tabs) on the document.
   *
   * @param signs the array of SignHere (see {@link SignHere})
   * @return the {@link Tabs} object containing passed SignHere objects
   */
  public static Tabs createSignerTabs(SignHere... signs) {
    Tabs signerTabs = new Tabs();
    signerTabs.setSignHereTabs(Arrays.asList(signs));
    return signerTabs;
  }

  /**
   * Create Tabs object containing a single SignHere (see {@link SignHere}) field (also known as
   * tabs) on the documents using anchor (autoPlace) positioning.
   *
   * @param anchorString the anchor string; the DocuSign platform searches throughout your
   *     envelope's documents for matching anchor strings
   * @param yOffsetPixels the y offset of anchor in pixels
   * @param xOffsetPixels the x offset of anchor in pixels
   * @return the {@link Tabs} object containing single SignHere object
   */
  public static Tabs createSingleSignerTab(
      String anchorString, int yOffsetPixels, int xOffsetPixels) {
    SignHere signHere = createSignHere(anchorString, yOffsetPixels, xOffsetPixels);
    return createSignerTabs(signHere);
  }

  /**
   * Creates a document object from the raw data.
   *
   * @param data the raw data
   * @param documentName the name of the document; it may be differ from the file
   * @param fileExtention the extension of the creating file
   * @param documentId identifier of the created document
   * @return the {@link Document} object
   */
  public static Document createDocument(
      byte[] data, String documentName, String fileExtention, String documentId) {
    Document document = new Document();
    document.setDocumentBase64(Base64.getEncoder().encodeToString(data));
    document.setName(documentName);
    document.setFileExtension(fileExtention);
    document.setDocumentId(documentId);
    return document;
  }

  public static Document createDocumentFromFile(
      S3Service s3Service, String bucketName, String key, String docName, String docId) {
    // Fetch the file from S3 as binary data
    BinaryData binaryData = s3Service.getS3BinaryData(bucketName, key);
    byte[] buffer = binaryData.toBytes();

    // Extract the file extension
    String extension = FilenameUtils.getExtension(key);

    // Create and return the document
    return createDocument(buffer, docName, extension, docId);
  }
}
