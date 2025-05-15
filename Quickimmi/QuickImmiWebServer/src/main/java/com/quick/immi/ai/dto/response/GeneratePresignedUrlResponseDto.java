/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneratePresignedUrlResponseDto {
  private Long documentId;
  private String presignedUrl;
}
