/* (C) 2024 */
package com.quick.immi.ai.dto.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MergePdfsDto {
  private String name;
  private List<Long> documentIds;
  private List<String> s3Locations;
}
