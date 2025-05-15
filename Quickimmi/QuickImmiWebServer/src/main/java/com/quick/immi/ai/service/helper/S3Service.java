/* (C) 2024 */
package com.quick.immi.ai.service.helper;

import com.azure.core.util.BinaryData;
import com.quick.immi.ai.entity.Document;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Slf4j
@Service
public class S3Service {
  @Autowired private S3Client s3Client;

  @Autowired private S3Presigner s3Presigner;

  public void uploadFile(String bucketName, String key, File file) {
    PutObjectRequest request = PutObjectRequest.builder().bucket(bucketName).key(key).build();

    s3Client.putObject(request, file.toPath());
    log.info("File uploaded successfully to S3 bucket: " + bucketName);
  }

  // Generate a presigned URL for file upload
  public String generatePresignedUrlForUpload(String bucketName, String key) {
    log.info(
        String.format("generatePresignedUrlForUpload bucketname=%s, key=%s: ", bucketName, key));

    PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(bucketName).key(key).build();

    PutObjectPresignRequest presignRequest =
        PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(10)) // The URL expires in 10 minutes.
            .putObjectRequest(objectRequest)
            .build();

    PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
    return presignedRequest.url().toExternalForm();
  }

  // Generate a presigned URL for file upload
  public String generatePresignedUrlForDownload(String bucketName, String key) {
    log.info(
        String.format("generatePresignedUrlForDownload bucketName=%s, key=%s: ", bucketName, key));

    GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();

    GetObjectPresignRequest presignRequest =
        GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofDays(1))
            .getObjectRequest(objectRequest)
            .build();

    PresignedGetObjectRequest presignedGetObjectRequest =
        s3Presigner.presignGetObject(presignRequest);
    return presignedGetObjectRequest.url().toExternalForm();
  }

  public String generatePresignedUrlForDownload(String s3Location) {
    String s3Key = s3Location.substring(5);
    String[] split = s3Key.split("/", 2);
    return generatePresignedUrlForDownload(split[0], split[1]);
  }

  public BinaryData getS3BinaryData(String bucketName, String key) {
    GetObjectRequest request = GetObjectRequest.builder().bucket(bucketName).key(key).build();

    ResponseBytes<GetObjectResponse> response =
        s3Client.getObject(request, ResponseTransformer.toBytes());
    return BinaryData.fromBytes(response.asByteArray());
  }

  // Download file from S3 bucket
  public void downloadFile(String bucketName, String key, File destinationFile) {
    GetObjectRequest request = GetObjectRequest.builder().bucket(bucketName).key(key).build();

    s3Client.getObject(request, destinationFile.toPath());
    System.out.println("File downloaded successfully from S3 bucket: " + bucketName);
  }

  public String fetchDocumentsAndZipFiles(String bucketName, List<Document> documents) {

    File tempDir = createTempDirectory();
    if (tempDir == null || documents.isEmpty()) {
      log.info("tempDir is null or documents is empty");
      throw new RuntimeException("fail to create tempDir or no documents found!");
    }
    System.out.println("tempDir: " + tempDir);
    Document document = documents.get(0);
    File zipFile = new File(tempDir, document.getCaseId() + ".zip");
    try {
      downloadFilesFromS3(bucketName, documents, tempDir);
      zipDownloadedFiles(tempDir, zipFile, documents);
      return uploadZipToS3(bucketName, document.getUserId() + "/" + document.getCaseId(), zipFile);
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
        System.out.println("s3Key: " + s3Key);
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

  private String uploadZipToS3(String bucketName, String s3KeyPrefix, File zipFile) {
    try {
      log.info(String.format("start uploading zip file %s to S3: ", zipFile.getName()));
      String key = s3KeyPrefix + "/" + zipFile.getName();
      PutObjectRequest putRequest = PutObjectRequest.builder().bucket(bucketName).key(key).build();

      s3Client.putObject(putRequest, RequestBody.fromFile(zipFile));
      log.info("Successfully uploaded zip file to S3: " + zipFile.getName());

      return key;
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

  public String getS3BucketFromS3Location(String s3Url) {
    String s3Key = s3Url.substring(5);
    String[] split = s3Key.split("/", 2);

    return split[0];
  }
}
