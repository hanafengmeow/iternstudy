/* (C) 2024 */
package com.quick.immi.ai.entity.familybased;

import java.util.List;
import lombok.Data;

@Data
public class FamilyBasedCaseProfile {
  private Relationship relationship;
  private Petitioner petitioner;
  private Beneficiary beneficiary;
  private Eligibility beneficiaryEligibility;
  private PreviousPetition previousPetition;
  private PetitionerStatement petitionerStatement;
  private List<Sponsor> sponsorList;
  private Interpreter interpreter;
  private SupplementDocument supplementDocument;
}
