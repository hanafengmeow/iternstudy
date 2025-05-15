package com.quick.immi.ai.entity.asylum.i589;

import lombok.Data;

@Data
public class ApplicationDetails {
    //Y
    private String asylumBasedOnRaceCheckbox;
    private String asylumBasedOnReligionCheckbox;
    private String asylumBasedOnNationalityCheckbox;
    private String asylumBasedOnPoliticalOptionCheckbox;
    private String asylumBasedOnParticularMembershipCheckbox;
    private String asylumBasedOnTortureConventionCheckbox;

    //Yes or No. for below all checkbox
    private String haveBeenHarmedYesCheckbox;
    private String haveBeenHarmedNoCheckbox;
    private String explainHaveBeenHarmedYes;
    private String explainHaveBeenHarmedYesPart = "Part B";
    private String explainHaveBeenHarmedYesQuestion = "A";


    private String fearReturnYesCheckbox;
    private String fearReturnNoCheckbox;
    //320
    private String explainFearReturnYes;
    private String explainFearReturnYesPart = "Part B";
    private String explainFearReturnYesQuestion = "B";

    //Y
    private String familyMembersBeenChargedYesCheckbox;
    //N
    private String familyMembersBeenChargedNoCheckbox;
    //325
    private String explainFamilyMembersBeenChargedYes;
    private String explainFamilyMembersBeenChargedYesPart = "Part B";
    private String explainFamilyMembersBeenChargedYesQuestion = "2";

    private String youOrFamilyBelongAnyOrganizationYesCheckbox;
    private String youOrFamilyBelongAnyOrganizationNoCheckbox;
    //327
    private String explainYouOrFamilyBelongAnyOrganizationYes;
    private String explainYouOrFamilyBelongAnyOrganizationYesPart = "Part B";
    private String explainYouOrFamilyBelongAnyOrganizationYesQuestion = "3.A";

    private String youOrFamilyContinueBelongAnyOrganizationYesCheckbox;
    private String youOrFamilyContinueBelongAnyOrganizationNoCheckbox;
    //329
    private String explainYouOrFamilyContinueBelongAnyOrganizationYes;
    private String explainYouOrFamilyContinueBelongAnyOrganizationYesPart = "Part B";
    private String explainYouOrFamilyContinueBelongAnyOrganizationYesQuestion = "3.B";

    private String afraidOfReturnYesCheckbox;
    private String afraidOfReturnNoCheckbox;
    //331
    private String explainAfraidOfReturnYes;
    private String explainAfraidOfReturnYesPart = "Part B";
    private String explainAfraidOfReturnYesQuestion = "4";

    private String appliedBeforeYesCheckbox;
    private String appliedBeforeNoCheckbox;
    //337
    private String explainAppliedBeforeYes;
    private String explainAppliedBeforeYesPart = "Part C";
    private String explainAppliedBeforeYesQuestion = "1";


    private String stayInOtherCountryYesCheckbox;
    private String stayInOtherCountryNoCheckbox;

    private String anyLawfulStatusAnyCountryYesCheckbox;
    private String anyLawfulStatusAnyCountryNoCheckbox;
    //340
    private String explainAnyLawfulStatusAnyCountryYes;

    private String explainAnyLawfulStatusAnyCountryYesPart = "Part C";
    private String explainAnyLawfulStatusAnyCountryYesQuestion = "2.B";

    private String haveYouHarmOthersYesCheckbox;
    private String haveYouHarmOthersNoCheckbox;
    //342
    private String explainHaveYouHarmOthersYes;
    private String explainHaveYouHarmOthersYesPart = "Part C";
    private String explainHaveYouHarmOthersYesQuestion = "3";


    private String returnCountryYesCheckbox;
    private String returnCountryNoCheckbox;
    //348
    private String explainReturnCountryYes;
    private String explainReturnCountryYesPart = "Part C";
    private String explainReturnCountryYesQuestion = "4";



    private String moreThanOneYearAfterArrivalYesCheckbox;
    private String moreThanOneYearAfterArrivalNoCheckbox;
    //350
    private String explainMoreThanOneYearAfterArrivalYes;
    private String explainMoreThanOneYearAfterArrivalYesPart = "Part C";
    private String explainMoreThanOneYearAfterArrivalYesQuestion = "5";

    private String haveCommittedCrimeYesCheckbox;
    private String haveCommittedCrimeNoCheckbox;
    //354
    private String explainHaveCommittedCrimeYes;
    private String explainHaveCommittedCrimeYesPart = "Part C";
    private String explainHaveCommittedCrimeYesQuestion = "6";
}
