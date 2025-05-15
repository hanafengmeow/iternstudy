/* (C) 2024 */
package com.quick.immi.ai.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
  private Long id;
  private Long caseId;
  private Integer userId;
  private Long lawyerId;
  private Long documentId;
  private String formName;
  private String status;
  private String description;
  private Long dueAt;
  private Long completedAt;
  private Long createdAt;
  private Long updatedAt;
  private String metadata;
  private String s3Location;
}
