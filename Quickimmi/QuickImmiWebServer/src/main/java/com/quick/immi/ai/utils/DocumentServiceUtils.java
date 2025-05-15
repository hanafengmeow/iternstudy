/* (C) 2024 */
package com.quick.immi.ai.utils;

import com.quick.immi.ai.dto.common.DocumentDto;
import com.quick.immi.ai.dto.request.GeneratePresignedUrlRequestDto;
import com.quick.immi.ai.service.DocumentMgtService;
import java.util.List;

public class DocumentServiceUtils {

  public static String getDocumentName(
      DocumentMgtService documentMgtService, GeneratePresignedUrlRequestDto requestDto) {
    List<DocumentDto> list =
        documentMgtService.list(
            requestDto.getCaseId(),
            requestDto.getType().getName(),
            requestDto.getIdentify().getValue(),
            false,
            null);
    int index = 0;
    for (DocumentDto documentDto : list) {
      if (requestDto.getDocumentName().equals(documentDto.getName())) {
        index++;
      }
    }

    return index == 0
        ? requestDto.getDocumentName()
        : requestDto.getDocumentName() + String.format("(%s)", index);
  }

  public static String getFileType(String documentName) {
    String documentNameLowerCase = documentName.toLowerCase();

    String fileType;
    if (documentNameLowerCase.endsWith(".pdf")) {
      fileType = "pdf";
    } else if (documentNameLowerCase.endsWith(".jpg")
        || documentNameLowerCase.endsWith(".jpeg")
        || documentNameLowerCase.endsWith(".png")
        || documentNameLowerCase.endsWith(".gif")) {
      fileType = "image";
    } else if (documentNameLowerCase.endsWith(".doc")
        || documentNameLowerCase.endsWith(".docx")
        || documentNameLowerCase.endsWith(".txt")
        || documentNameLowerCase.endsWith(".md")
        || documentNameLowerCase.endsWith(".pages")) {
      fileType = "text";
    } else if (documentNameLowerCase.endsWith(".mp4")
        || documentNameLowerCase.endsWith(".mkv")
        || documentNameLowerCase.endsWith(".avi")
        || documentNameLowerCase.endsWith(".mov")
        || documentNameLowerCase.endsWith(".wmv")) {
      fileType = "video";
    } else if (documentNameLowerCase.endsWith(".mp3")
        || documentNameLowerCase.endsWith(".wav")
        || documentNameLowerCase.endsWith(".aac")
        || documentNameLowerCase.endsWith(".flac")
        || documentNameLowerCase.endsWith(".ogg")) {
      fileType = "audio";
    } else {
      throw new RuntimeException("Unsupported file type");
    }
    return fileType;
  }
}
