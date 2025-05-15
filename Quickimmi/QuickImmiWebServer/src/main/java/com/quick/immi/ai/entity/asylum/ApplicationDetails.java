/* (C) 2024 */
package com.quick.immi.ai.entity.asylum;

import lombok.Data;

@Data
public class ApplicationDetails {
  private boolean asylumBasedOnRaceCheckbox;
  private boolean asylumBasedOnReligionCheckbox;
  private boolean asylumBasedOnNationalityCheckbox;
  private boolean asylumBasedOnPoliticalOptionCheckbox;
  private boolean asylumBasedOnParticularMembershipCheckbox;
  private boolean asylumBasedOnTortureConventionCheckbox;

  private boolean haveBeenHarmedYesCheckbox;
  private boolean haveBeenHarmedNoCheckbox;
  private String explainHaveBeenHarmedYes;
  private String explainHaveBeenHarmedYesPart = "Part B";
  private String explainHaveBeenHarmedYesQuestion = "A";

  private boolean fearReturnYesCheckbox;
  private boolean fearReturnNoCheckbox;
  // 320
  private String explainFearReturnYes;
  private String explainFearReturnYesPart = "Part B";
  private String explainFearReturnYesQuestion = "B";

  // Y
  private boolean familyMembersBeenChargedYesCheckbox;
  // N
  private boolean familyMembersBeenChargedNoCheckbox;
  // 325
  private String explainFamilyMembersBeenChargedYes;
  private String explainFamilyMembersBeenChargedYesPart = "Part B";
  private String explainFamilyMembersBeenChargedYesQuestion = "2";

  private boolean youOrFamilyBelongAnyOrganizationYesCheckbox;
  private boolean youOrFamilyBelongAnyOrganizationNoCheckbox;
  // 327
  private String explainYouOrFamilyBelongAnyOrganizationYes;
  private String explainYouOrFamilyBelongAnyOrganizationYesPart = "Part B";
  private String explainYouOrFamilyBelongAnyOrganizationYesQuestion = "3.A";

  private boolean youOrFamilyContinueBelongAnyOrganizationYesCheckbox;
  private boolean youOrFamilyContinueBelongAnyOrganizationNoCheckbox;
  // 329
  private String explainYouOrFamilyContinueBelongAnyOrganizationYes;
  private String explainYouOrFamilyContinueBelongAnyOrganizationYesPart = "Part B";
  private String explainYouOrFamilyContinueBelongAnyOrganizationYesQuestion = "3.B";

  private boolean afraidOfReturnYesCheckbox;
  private boolean afraidOfReturnNoCheckbox;
  // 331
  private String explainAfraidOfReturnYes;
  private String explainAfraidOfReturnYesPart = "Part B";
  private String explainAfraidOfReturnYesQuestion = "4";

  private boolean appliedBeforeYesCheckbox;
  private boolean appliedBeforeNoCheckbox;
  // 337
  private String explainAppliedBeforeYes;
  private String explainAppliedBeforeYesPart = "Part C";
  private String explainAppliedBeforeYesQuestion = "1";

  private boolean stayInOtherCountryYesCheckbox;
  private boolean stayInOtherCountryNoCheckbox;

  private boolean anyLawfulStatusAnyCountryYesCheckbox;
  private boolean anyLawfulStatusAnyCountryNoCheckbox;
  // 340
  private String explainAnyLawfulStatusAnyCountryYes;

  private String explainAnyLawfulStatusAnyCountryYesPart = "Part C";
  private String explainAnyLawfulStatusAnyCountryYesQuestion = "2.B";

  private boolean haveYouHarmOthersYesCheckbox;
  private boolean haveYouHarmOthersNoCheckbox;
  // 342
  private String explainHaveYouHarmOthersYes;
  private String explainHaveYouHarmOthersYesPart = "Part C";
  private String explainHaveYouHarmOthersYesQuestion = "3";

  private boolean returnCountryYesCheckbox;
  private boolean returnCountryNoCheckbox;
  // 348
  private String explainReturnCountryYes;
  private String explainReturnCountryYesPart = "Part C";
  private String explainReturnCountryYesQuestion = "4";

  private boolean moreThanOneYearAfterArrivalYesCheckbox;
  private boolean moreThanOneYearAfterArrivalNoCheckbox;
  // 350
  private String explainMoreThanOneYearAfterArrivalYes;
  private String explainMoreThanOneYearAfterArrivalYesPart = "Part C";
  private String explainMoreThanOneYearAfterArrivalYesQuestion = "5";

  private boolean haveCommittedCrimeYesCheckbox;
  private boolean haveCommittedCrimeNoCheckbox;
  // 354
  private String explainHaveCommittedCrimeYes;
  private String explainHaveCommittedCrimeYesPart = "Part C";
  private String explainHaveCommittedCrimeYesQuestion = "6";
}
