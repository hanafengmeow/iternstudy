/* (C) 2024 */
package com.quick.immi.ai.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationCase {
  private Long id;
  private Integer userId;
  private String applicantName;
  private String caseName;
  private String type;
  private String subType;
  private CaseProgressStep currentStep;
  private String progress;
  private String email;
  private String status;
  // json string --- in original language
  private String profile;
  private String profileEnglish;
  private String uscisReceiptNumber;
  private Integer assignedLawyer;
  private Integer createdBy;
  private Boolean createdByLawyer;
  private Long createdAt;
  private Long updatedAt;
}
