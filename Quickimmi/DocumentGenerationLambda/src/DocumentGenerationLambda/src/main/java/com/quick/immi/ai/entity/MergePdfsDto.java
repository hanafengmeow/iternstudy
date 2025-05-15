package com.quick.immi.ai.entity;

import java.util.List;
import lombok.Data;

@Data
public class MergePdfsDto {
  private String name;
  private List<Long> documentIds;
  private List<String> s3Locations;
}
