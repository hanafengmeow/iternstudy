/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import com.quick.immi.ai.entity.ApplicationCaseProgress;
import com.quick.immi.ai.entity.CaseProgressStep;
import com.quick.immi.ai.entity.asylum.AsylumCaseProfile;
import com.quick.immi.ai.entity.asylum.AsylumType;
import lombok.*;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class AsylumApplicationCaseDto {
  private Long id;
  private Integer userId;
  private String type;
  private AsylumType asylumType;
  //  private CaseSummary summary;
  private String reason;
  private String email;
  private CaseProgressStep currentStep;
  private ApplicationCaseProgress progress;
  private String status;
  private String birthCountry;
  // json string
  private AsylumCaseProfile profile;
  private Long submittedAt;
  private boolean paid;
  private String uscisReceiptNumber;
  private Integer assignedLawyer;
  private Integer createdBy;
  private Long createdAt;
  private Long updatedAt;
}
