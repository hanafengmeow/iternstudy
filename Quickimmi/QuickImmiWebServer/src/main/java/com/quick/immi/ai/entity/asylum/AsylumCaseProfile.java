/* (C) 2024 */
package com.quick.immi.ai.entity.asylum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsylumCaseProfile {
  private String applyForWithholdingYesCheckbox;
  private Applicant applicant;
  private Family family;
  private Background background;
  // Part B. Information About Your Application
  private ApplicationDetails applicationDetails;
  private YourSignature signature;
  private Declaration declaration;
  private SupplementDocument supplementDocument;
}
