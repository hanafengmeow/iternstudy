/* (C) 2024 */
package com.quick.immi.ai.utils;

import com.quick.immi.ai.entity.Document;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

// Test purpose. Don't delete
@Slf4j
public class TestUtils {
  private S3Client s3Client =
      S3Client.builder().credentialsProvider(DefaultCredentialsProvider.create()).build();

  public void fetchDocumentsAndZipFiles(String bucketName, List<Document> documents) {

    File tempDir = createTempDirectory();
    if (tempDir == null || documents.isEmpty()) {
      log.info("tempDir is null or documents is empty");
      return;
    }
    Document document = documents.get(0);
    File zipFile = new File(tempDir, document.getCaseId() + ".zip");
    try {
      downloadFilesFromS3(bucketName, documents, tempDir);
      zipDownloadedFiles(tempDir, zipFile, documents);
      uploadZipToS3(bucketName, document.getUserId() + "/" + document.getCaseId(), zipFile);
    } catch (IOException e) {
      log.error("fail to fetchAndZipFiles", e);
      throw new RuntimeException(e);
    } finally {
      cleanUpTempDirectory(tempDir);
    }
  }

  private File createTempDirectory() {
    File tempDir =
        new File(System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID());
    if (!tempDir.exists() && !tempDir.mkdirs()) {
      return null;
    }
    return tempDir;
  }

  private void downloadFilesFromS3(String bucketName, List<Document> documents, File tempDir)
      throws IOException {
    log.info("start downloading files....");
    try {
      for (Document document : documents) {
        File file = new File(tempDir, document.getCaseId() + "/" + document.getName());

        // Ensure the parent directory exists
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
          file.getParentFile().mkdirs();
        }

        String s3Key = getS3KeyFromS3Location(document.getS3Location());
        GetObjectRequest getObjectRequest =
            GetObjectRequest.builder().bucket(bucketName).key(s3Key).build();
        s3Client.getObject(getObjectRequest, Paths.get(file.getPath()));
      }
      log.info("finsh downloading files....");
    } catch (Exception exp) {
      log.error("fail to downloadFilesFromS3 ", exp);
      throw exp;
    }
  }

  private void zipDownloadedFiles(File tempDir, File zipFile, List<Document> documents)
      throws IOException {
    log.info("Start zip download files....");
    try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
      for (Document document : documents) {
        File file = new File(tempDir, document.getCaseId() + "/" + document.getName());
        try (FileInputStream fis = new FileInputStream(file)) {
          zos.putNextEntry(new ZipEntry(document.getName()));
          byte[] readBuf = new byte[1024];
          int readLen;
          while ((readLen = fis.read(readBuf)) > 0) {
            zos.write(readBuf, 0, readLen);
          }
          zos.closeEntry();
        }
      }
    }
    log.info("Finish zip download files....");
  }

  private void uploadZipToS3(String bucketName, String s3KeyPrefix, File zipFile) {
    try {
      log.info(String.format("start uploading zip file %s to S3: ", zipFile.getName()));
      PutObjectRequest putRequest =
          PutObjectRequest.builder()
              .bucket(bucketName)
              .key(s3KeyPrefix + "/" + zipFile.getName())
              .build();

      s3Client.putObject(putRequest, RequestBody.fromFile(zipFile));
      log.info("Successfully uploaded zip file to S3: " + zipFile.getName());
    } catch (Exception e) {
      log.error("fail to uploadZipToS3", e);
      throw e;
    }
  }

  private void cleanUpTempDirectory(File tempDir) {
    if (tempDir.exists()) {
      try {
        Files.walk(tempDir.toPath())
            .map(Path::toFile)
            .sorted((o1, o2) -> -o1.compareTo(o2))
            .forEach(File::delete);
      } catch (IOException e) {
        log.warn("fail to cleanUpTempDirectory", e);
      }
    }
  }

  public String getS3KeyFromS3Location(String s3Url) {
    String s3Key = s3Url.substring(5);
    String[] split = s3Key.split("/", 2);

    return split[1];
  }

  public static void main(String[] args) {
    TestUtils testUtils = new TestUtils();
    Document document =
        Document.builder()
            .name("Asylum_coverletter (1).pdf")
            .s3Location("s3://quick-immi-user-profile/78/208/Asylum_coverletter (1).pdf")
            .caseId(208l)
            .userId(78)
            .build();
    Document document1 =
        Document.builder()
            .name("g-28.pdf")
            .s3Location("s3://quick-immi-user-profile/78/208/g-28.pdf")
            .caseId(208l)
            .userId(78)
            .build();

    testUtils.fetchDocumentsAndZipFiles(
        "quick-immi-user-profile", Arrays.asList(document, document1));
  }
}
