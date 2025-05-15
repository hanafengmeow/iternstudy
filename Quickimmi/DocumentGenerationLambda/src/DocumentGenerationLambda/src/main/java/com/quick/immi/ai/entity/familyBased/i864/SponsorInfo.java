package com.quick.immi.ai.entity.familyBased.i864;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Part 1: Basis for Filing Affidavit of Support
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SponsorInfo {
  // 118 - Sponsor's Full Name
  private String fullName;

  // 1.a - I am the petitioner (Checkbox)
  private String isPetitionerCheckbox;

  // 1.b - I filed an alien worker petition on behalf of the intending immigrant (Checkbox)
  private String filedAlienWorkerPetitionCheckbox;

  // 119 - Relationship to the intending immigrant
  private String relationshipToImmigrant;

  // 1.c - I have an ownership interest of at least 5 percent in a business (Checkbox)
  private String ownershipInterest5PercentCheckbox;

  // 120 - Business ownership interest description
  private String ownershipInterestDescription;

  // 121 - Relationship to immigrant for ownership
  private String ownershipRelationshipToImmigrant;

  // 1.d - I am the only joint sponsor (Checkbox)
  private String onlyJointSponsorCheckbox;

  // 1.e - I am the first or second joint sponsor (Checkbox)
  private String notOnlyJointSponsorCheckbox;
  private String firstJointSponsorCheckbox;
  private String secondJointSponsorCheckbox;

  // 1.f - I am the substitute sponsor (Checkbox)
  private String substituteSponsorCheckbox;

  // 122 - Relationship as substitute sponsor
  private String substituteSponsorRelationship;
}
