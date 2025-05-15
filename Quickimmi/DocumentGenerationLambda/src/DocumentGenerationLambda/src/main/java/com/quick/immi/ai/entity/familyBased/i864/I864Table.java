package com.quick.immi.ai.entity.familyBased.i864;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO 1 HOW to implement the top Box "To be completed by an attorney or accredited representative"
//TODO 2 How to add additional supplement page
//TODO 3 Where to implement to logic some cell. e.g. The total amount = sum of the amount of some other cells
//TODO 4 The use of the check box json? where to use. seems not used yet
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I864Table {
  // PART 0 - Attorney or Representative Information
  private Attorney attorney;

  // Part 1: Basis for Filing Affidavit of Support
  private SponsorInfo sponsorInfo;

  // Part 2: Information About Principal Immigrant
  private PrincipalImmigrant principalImmigrant;

  // Part 3: Information About the Immigrants You Are Sponsoring
  private SponsoredImmigrants sponsoredImmigrants;

  // Part 4: Information About You (Sponsor)
  private SponsorDetails sponsorDetails;

  // Part 5: Sponsor's Household Size
  private HouseholdSize householdSize;

  // Part 6: Sponsor's Employment and Income
  private EmploymentAndIncome employmentAndIncome;

  // Part 7: Use of Assets to Supplement Income (Optional)
  private AssetsSupplementIncome assetsSupplementIncome;

  // Part 8: Sponsor's Contract, Declaration, Certification, and Signature
  private SponsorContract sponsorContract;

  // Part 9: Interpreter's Information (if applicable)
  private InterpreterInfo interpreterInfo;

  // Part 10: Preparer's Information (if applicable)
  private PreparerInfo preparerInfo;

  // Part 11: Additional Information
  private List<Supplement> supplement;
}

