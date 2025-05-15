package com.quick.immi.ai.entity.employmentBased.i140;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I140Table {
  // PART 0 - Attorney or Representative Information
  private Attorney attorney;

  // PART 1: Information About the Person or Organization Filing This Petition
  private Petitioner petitioner;


  // PART 2: Petition Type
  private PetitionType petitionType;

  // PART 3: Information About the Person for Whom You Are Filing
  private Beneficiary beneficiary;

  // PART 4: Processing Information
  private ProcessingInfo processingInfo;

  // PART 5: Additional Information About the Petitioner
  private AdditionalPetitionerInfo additionalPetitionerInfo;

  // PART 6: Basic Information About the Proposed Employment
  private EmploymentInfo employmentInfo;

  // PART 7: Information About the Spouse and All Children of the Person for Whom You Are Filing
  private List<FamilyMember> familyMembers;

  // PART 8: Contact Information, Certification, and Signature of the Petitioner or Authorized Signatory
  private ContactInfo contactInfo;

  // PART 9: Interpreter's Contact Information, Certification, and Signature
  private InterpreterInfo interpreterInfo;

  // PART 10: Contact Information, Certification, and Signature of the Preparer
  private PreparerInfo preparerInfo;

  // PART 11: Additional Information
  private List<Supplement> supplement;
}
