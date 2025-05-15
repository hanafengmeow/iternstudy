package com.quick.immi.ai.entity.i485;

import lombok.Data;
import java.util.List;

@Data
public class Eligibility {
    // Page 9 Question 1-13 - associated with organization
    private String associatedWithOrganizationYesCheckbox;
    private String associatedWithOrganizationNoCheckbox;
    private List<Organization> organizations;

    // Page 10 Question 14 - Denied admission
    private String deniedAdmissionYesCheckbox;
    private String deniedAdmissionNoCheckbox;

    // Page 10 Question 15 - Denied visa
    private String deniedVisaYesCheckbox;
    private String deniedVisaNoCheckbox;

    // Page 10 Question 16 - Worked without authorization
    private String workedWithoutAuthorizationYesCheckbox;
    private String workedWithoutAuthorizationNoCheckbox;

    // Page 10 Question 17 - Violated nonimmigrant status
    private String violatedNonimmigrantStatusYesCheckbox;
    private String violatedNonimmigrantStatusNoCheckbox;

    // Page 10 Question 18 - Removal proceedings
    private String removalProceedingsYesCheckbox;
    private String removalProceedingsNoCheckbox;

    // Page 10 Question 19 - Final order of removal
    private String finalOrderOfRemovalYesCheckbox;
    private String finalOrderOfRemovalNoCheckbox;
    
    // Page 10 Question 20 - Prior final order reinstated
    private String priorFinalOrderReinstatedYesCheckbox;
    private String priorFinalOrderReinstatedNoCheckbox;

    // Page 10 Question 21 - LPR status rescinded
    private String lprStatusRescindedYesCheckbox;
    private String lprStatusRescindedNoCheckbox;

    // Page 10 Question 22 - Voluntary departure failed
    private String voluntaryDepartureFailedYesCheckbox;
    private String voluntaryDepartureFailedNoCheckbox;

    // Page 10 Question 23 - Applied for relief from removal
    private String appliedForReliefFromRemovalYesCheckbox;
    private String appliedForReliefFromRemovalNoCheckbox;

    // Page 10 Question 24a-24c - Compliance with specific visa requirements (J visa)
    private String jVisaForeignResidenceRequirementYesCheckbox;
    private String jVisaForeignResidenceRequirementNoCheckbox;
    private String compliedWithForeignResidenceYesCheckbox;
    private String compliedWithForeignResidenceNoCheckbox;
    private String grantedWaiverOrRecommendationYesCheckbox;
    private String grantedWaiverOrRecommendationNoCheckbox;

    // Page 10 Question 25 - Arrested or Detained
    private String arrestedOrDetainedYesCheckbox;
    private String arrestedOrDetainedNoCheckbox;

    // Page 10 Question 26 - Committed Crime
    private String committedCrimeNoCheckbox;
    private String committedCrimeYesCheckbox;

    // Page 11 Question 27 - Pled Guilty or Convicted
    private String pledGuiltyOrConvictedYesCheckbox;
    private String pledGuiltyOrConvictedNoCheckbox;

    // Page 11 Question 28 - Ordered Punished by Judge
    private String orderedPunishedByJudgeNoCheckbox;
    private String orderedPunishedByJudgeYesCheckbox;

    // Page 11 Question 29 - Defendant in Criminal Proceeding
    private String defendantInCriminalProceedingYesCheckbox;
    private String defendantInCriminalProceedingNoCheckbox;

    // Page 11 Question 30 - Violated Controlled Substance Law
    private String violatedControlledSubstanceLawNoCheckbox;
    private String violatedControlledSubstanceLawYesCheckbox;

    // Page 11 Question 31 - Convicted of Multiple Offenses
    private String convictedOfMultipleOffensesNoCheckbox;
    private String convictedOfMultipleOffensesYesCheckbox;

    // Page 11 Question 32 - Illicitly Trafficked Controlled Substances
    private String illicitlyTraffickedControlledSubstancesYesCheckbox;
    private String illicitlyTraffickedControlledSubstancesNoCheckbox;

    // Page 11 Question 33 - Aided in Illicit Trafficking
    private String aidedInIllicitTraffickingNoCheckbox;
    private String aidedInIllicitTraffickingYesCheckbox;

    // Page 11 Question 34 - Related to Illicit Trafficker
    private String relatedToIllicitTraffickerNoCheckbox;
    private String relatedToIllicitTraffickerYesCheckbox;

    // Page 11 Question 35 - Engaged in Prostitution
    private String engagedInProstitutionNoCheckbox;
    private String engagedInProstitutionYesCheckbox;

    // Page 11 Question 36 - Procured Prostitutes
    private String procuredProstitutesYesCheckbox;
    private String procuredProstitutesNoCheckbox;

    // Page 11 Question 37 - Received Proceeds from Prostitution
    private String receivedProceedsFromProstitutionNoCheckbox;
    private String receivedProceedsFromProstitutionYesCheckbox;

    // Page 11 Question 38 - Intent to Engage in Illegal Gambling
    private String intentToEngageInIllegalGamblingYesCheckbox;
    private String intentToEngageInIllegalGamblingNoCheckbox;

    // Page 11 Question 39 - Exercised Immunity from Prosecution
    private String exercisedImmunityFromProsecutionNoCheckbox;
    private String exercisedImmunityFromProsecutionYesCheckbox;

    // Page 11 Question 40 - Responsible for Religious Violations
    private String responsibleForReligiousViolationsYesCheckbox;
    private String responsibleForReligiousViolationsNoCheckbox;

    // Page 11 Question 41 - Induced Trafficking for Sex
    private String inducedTraffickingForSexNoCheckbox;
    private String inducedTraffickingForSexYesCheckbox;

    // Page 11 Question 42 - Trafficked into Slavery
    private String traffickedIntoSlaveryNoCheckbox;
    private String traffickedIntoSlaveryYesCheckbox;

    // Page 11 Question 43 - Aided Trafficking for Sex or Slavery
    private String aidedTraffickingForSexOrSlaveryYesCheckbox;
    private String aidedTraffickingForSexOrSlaveryNoCheckbox;

    // Page 11 Question 44 - Related to Trafficker and Benefited
    private String relatedToTraffickerAndBenefitedNoCheckbox;
    private String relatedToTraffickerAndBenefitedYesCheckbox;

    // Page 11 Question 45 - Engaged in Money Laundering
    private String engagedInMoneyLaunderingYesCheckbox;
    private String engagedInMoneyLaunderingNoCheckbox;

    // Page 12, Question 46a - Engage in Espionage or Sabotage
    private String engageInEspionageOrSabotageYesCheckbox;
    private String engageInEspionageOrSabotageNoCheckbox;

    // Page 12, Question 46b - Engage in Illegal Export
    private String engageInIllegalExportYesCheckbox;
    private String engageInIllegalExportNoCheckbox;

    // Page 12, Question 46c - Engage in Overthrowing Government
    private String engageInOverthrowingGovernmentYesCheckbox;
    private String engageInOverthrowingGovernmentNoCheckbox;

    // Page 12, Question 46d - Engage in Endangering US
    private String engageInEndangeringUSYesCheckbox;
    private String engageInEndangeringUSNoCheckbox;

    // Page 12, Question 46e - Engage in Unlawful Activity
    private String engageInUnlawfulActivityYesCheckbox;
    private String engageInUnlawfulActivityNoCheckbox;

    // Page 12, Question 47 - Engage in Adverse Foreign Policy
    private String engageInAdverseForeignPolicyYesCheckbox;
    private String engageInAdverseForeignPolicyNoCheckbox;

    // Page 12, Question 48a - Committed or Planned Serious Crime
    private String committedOrPlannedSeriousCrimeYesCheckbox;
    private String committedOrPlannedSeriousCrimeNoCheckbox;

    // Page 12, Question 48b - Member of a Group Involved in Serious Crime
    private String memberOfGroupInvolvedInSeriousCrimeYesCheckbox;
    private String memberOfGroupInvolvedInSeriousCrimeNoCheckbox;

    // Page 12, Question 48c - Recruited for a Group Involved in Serious Crime
    private String recruitedForGroupInvolvedInSeriousCrimeYesCheckbox;
    private String recruitedForGroupInvolvedInSeriousCrimeNoCheckbox;

    // Page 12, Question 48d - Provided Support for a Group Involved in Serious Crime
    private String providedSupportForGroupInvolvedInSeriousCrimeYesCheckbox;
    private String providedSupportForGroupInvolvedInSeriousCrimeNoCheckbox;

    // Page 12, Question 48e - Provided Support for an Individual or Group
    private String providedSupportForIndividualOrGroupYesCheckbox;
    private String providedSupportForIndividualOrGroupNoCheckbox;

    // Page 12, Question 49 - Received Military or Weapons Training
    private String receivedMilitaryOrWeaponsTrainingYesCheckbox;
    private String receivedMilitaryOrWeaponsTrainingNoCheckbox;

    // Page 12, Question 50 - Intent to Engage in Serious Activities
    private String intendToEngageInSeriousActivitiesYesCheckbox;
    private String intendToEngageInSeriousActivitiesNoCheckbox;

    // Page 12, Question 51a - Spouse or Child Committed Serious Crime
    private String spouseOrChildCommittedSeriousCrimeYesCheckbox;
    private String spouseOrChildCommittedSeriousCrimeNoCheckbox;

    // Page 12, Question 51b - Spouse or Child Member of a Group
    private String spouseOrChildMemberOfGroupYesCheckbox;
    private String spouseOrChildMemberOfGroupNoCheckbox;

    // Page 12, Question 51c - Spouse or Child Recruited for a Group
    private String spouseOrChildRecruitedForGroupYesCheckbox;
    private String spouseOrChildRecruitedForGroupNoCheckbox;

    // Page 12, Question 51d - Spouse or Child Provided Support
    private String spouseOrChildProvidedSupportYesCheckbox;
    private String spouseOrChildProvidedSupportNoCheckbox;

    // Page 12, Question 51e - Spouse or Child Provided Support for Individual or Group
    private String spouseOrChildProvidedSupportForIndividualOrGroupYesCheckbox;
    private String spouseOrChildProvidedSupportForIndividualOrGroupNoCheckbox;

    // Page 12, Question 51f - Spouse or Child Received Training
    private String spouseOrChildReceivedTrainingYesCheckbox;
    private String spouseOrChildReceivedTrainingNoCheckbox;

    // Page 12, Question 52 - Assisted in Weapons Activities
    private String assistedInWeaponsActivitiesYesCheckbox;
    private String assistedInWeaponsActivitiesNoCheckbox;

    // Page 13, Question 53 - Worked or Volunteered in a Detention Facility
    private String workedOrVolunteeredInDetentionFacilityYesCheckbox;
    private String workedOrVolunteeredInDetentionFacilityNoCheckbox;

    // Page 13, Question 54 - Participated in a Group Using Weapons
    private String participatedInGroupUsingWeaponsYesCheckbox;
    private String participatedInGroupUsingWeaponsNoCheckbox;

    // Page 13, Question 55 - Served in Military or Armed Group
    private String servedInMilitaryOrArmedGroupYesCheckbox;
    private String servedInMilitaryOrArmedGroupNoCheckbox;

    // Page 13, Question 56 - Affiliated with Communist or Totalitarian Party
    private String affiliatedWithCommunistOrTotalitarianPartyYesCheckbox;
    private String affiliatedWithCommunistOrTotalitarianPartyNoCheckbox;

    // Page 13, Question 57 - Participated in Nazi Persecution
    private String participatedInNaziPersecutionYesCheckbox;
    private String participatedInNaziPersecutionNoCheckbox;

    // Page 13, Question 58a - Involved in Torture or Genocide
    private String involvedInTortureOrGenocideYesCheckbox;
    private String involvedInTortureOrGenocideNoCheckbox;

    // Page 13, Question 58b - Killed Any Person
    private String killedAnyPersonYesCheckbox;
    private String killedAnyPersonNoCheckbox;

    // Page 13, Question 58c - Severely Injured Any Person
    private String severelyInjuredAnyPersonYesCheckbox;
    private String severelyInjuredAnyPersonNoCheckbox;

    // Page 13, Question 58d - Engaged in Non-Consensual Sexual Contact
    private String engagedInNonConsensualSexualContactYesCheckbox;
    private String engagedInNonConsensualSexualContactNoCheckbox;

    // Page 13, Question 58e - Limited Religious Beliefs
    private String limitedReligiousBeliefsYesCheckbox;
    private String limitedReligiousBeliefsNoCheckbox;

    // Page 13, Question 59 - Recruited Child Soldiers
    private String recruitedChildSoldiersYesCheckbox;
    private String recruitedChildSoldiersNoCheckbox;

    // Page 13, Question 60 - Used Child Soldiers in Combat
    private String usedChildSoldiersInCombatYesCheckbox;
    private String usedChildSoldiersInCombatNoCheckbox;

    // Page 13, Question 61 - Subject to Public Charge
    private String subjectToPublicChargeYesCheckbox;
    private String subjectToPublicChargeNoCheckbox;

    // Page 13, Question 62 - Household Size
    private String householdSize;

    // Page 13, Question 63 - Household Income Range
    private String householdIncomeRange0to27kCheckbox;
    private String householdIncomeRange27kto52kCheckbox;
    private String householdIncomeRange52kto85kCheckbox;
    private String householdIncomeRange85kto141kCheckbox;
    private String householdIncomeRangeOver141kCheckbox;

    // Page 13, Question 64 - Household Assets Value
    private String householdAssetsValue0to18kCheckbox;
    private String householdAssetsValue18kto136kCheckbox;
    private String householdAssetsValue136kto321kCheckbox;
    private String householdAssetsValue321kto707kCheckbox;
    private String householdAssetsValueOver707kCheckbox;

    // Page 14, Question 65 - Liabilities Value
    private String liabilitiesValue0Checkbox;
    private String liabilitiesValue1kto10kCheckbox;
    private String liabilitiesValue10kto57kCheckbox;
    private String liabilitiesValue57kto186kCheckbox;
    private String liabilitiesValueOver186kCheckbox;

    // Page 14, Question 66 - Education Level
    private String educationGrades1to11Checkbox;
    private String education12thNoDiplomaCheckbox;
    private String educationHighSchoolDiplomaCheckbox;
    private String educationSomeCollegeNoDegreeCheckbox;
    private String educationAssociatesDegreeCheckbox;
    private String educationBachelorsDegreeCheckbox;
    private String educationMastersDegreeCheckbox;
    private String educationProfessionalDegreeCheckbox;
    private String educationDoctorateDegreeCheckbox;

    // Page 14, Question 67 - Certifications, Licenses, Skills
    private String certificationsLicensesSkills;

    // Page 14, Question 68a - Received SSI or TANF
    private String receivedSSIOrTANFYesCheckbox;
    private String receivedSSIOrTANFNoCheckbox;

    // Page 14, Question 68b - Received Institutionalization
    private String receivedInstitutionalizationYesCheckbox;
    private String receivedInstitutionalizationNoCheckbox;

    // Page 14, Question 68c - Detailed Benefits Information
    private List<Benefit> benefitRecords;

    // Page 14, Question 68d - Detailed Institutionalization Information
    private List<Institutionalization> institutionalizationRecords;

    // Page 15, Question 69a - Failed to Attend Removal Proceeding
    private String failedToAttendRemovalProceedingYesCheckbox;
    private String failedToAttendRemovalProceedingNoCheckbox;

    // Page 15, Question 69b - Believe Had Reasonable Cause
    private String believeHadReasonableCauseYesCheckbox;
    private String believeHadReasonableCauseNoCheckbox;

    // Page 15, Question 70 - Submitted Fraudulent Documentation
    private String submittedFraudulentDocumentationYesCheckbox;
    private String submittedFraudulentDocumentationNoCheckbox;

    // Page 15, Question 71 - Lied or Misrepresented for Immigration Benefit
    private String liedOrMisrepresentedForImmigrationBenefitYesCheckbox;
    private String liedOrMisrepresentedForImmigrationBenefitNoCheckbox;

    // Page 15, Question 72 - Falsely Claimed US Citizenship
    private String falselyClaimedUSCitizenshipYesCheckbox;
    private String falselyClaimedUSCitizenshipNoCheckbox;

    // Page 15, Question 73 - Been a Stowaway
    private String beenAStowawayYesCheckbox;
    private String beenAStowawayNoCheckbox;

    // Page 15, Question 74 - Knowingly Encouraged Illegal Entry
    private String knowinglyEncouragedIllegalEntryYesCheckbox;
    private String knowinglyEncouragedIllegalEntryNoCheckbox;

    // Page 15, Question 75 - Under Civil Penalty for Fraudulent Documents
    private String underCivilPenaltyForFraudulentDocumentsYesCheckbox;
    private String underCivilPenaltyForFraudulentDocumentsNoCheckbox;

    // Page 15, Question 76 - Excluded, Deported, or Removed
    private String excludedDeportedOrRemovedYesCheckbox;
    private String excludedDeportedOrRemovedNoCheckbox;

    // Page 15, Question 77 - Entered Without Inspection
    private String enteredWithoutInspectionYesCheckbox;
    private String enteredWithoutInspectionNoCheckbox;

    // Page 15, Question 78a - Unlawfully Present 180 Days to 1 Year
    private String unlawfullyPresent180DaysTo1YearYesCheckbox;
    private String unlawfullyPresent180DaysTo1YearNoCheckbox;

    // Page 15, Question 78b - Unlawfully Present 1 Year or More
    private String unlawfullyPresent1YearOrMoreYesCheckbox;
    private String unlawfullyPresent1YearOrMoreNoCheckbox;

    // Page 15, Question 79a - Reentered After Unlawfully Present
    private String reenteredAfterUnlawfullyPresentYesCheckbox;
    private String reenteredAfterUnlawfullyPresentNoCheckbox;

    // Page 15, Question 79b - Reentered After Deportation
    private String reenteredAfterDeportationYesCheckbox;
    private String reenteredAfterDeportationNoCheckbox;

    // Page 15, Question 80 - Plan to Practice Polygamy
    private String planToPracticePolygamyYesCheckbox;
    private String planToPracticePolygamyNoCheckbox;

    // Page 15, Question 81 - Accompanying Inadmissible Foreign National
    private String accompanyingInadmissibleForeignNationalYesCheckbox;
    private String accompanyingInadmissibleForeignNationalNoCheckbox;

    // Page 15, Question 82 - Assisted in Withholding Custody
    private String assistedInWithholdingCustodyYesCheckbox;
    private String assistedInWithholdingCustodyNoCheckbox;

    // Page 15, Question 83 - Voted in Violation of Law
    private String votedInViolationOfLawYesCheckbox;
    private String votedInViolationOfLawNoCheckbox;

    // Page 15, Question 84 - Renounced Citizenship to Avoid Tax
    private String renouncedCitizenshipToAvoidTaxYesCheckbox;
    private String renouncedCitizenshipToAvoidTaxNoCheckbox;

    // Page 16, Question 85a - Applied for Exemption from Military Service
    private String appliedForExemptionFromMilitaryServiceYesCheckbox;
    private String appliedForExemptionFromMilitaryServiceNoCheckbox;

    // Page 16, Question 85b - Relieved from Military Service
    private String relievedFromMilitaryServiceYesCheckbox;
    private String relievedFromMilitaryServiceNoCheckbox;

    // Page 16, Question 85c - Convicted of Desertion
    private String convictedOfDesertionYesCheckbox;
    private String convictedOfDesertionNoCheckbox;

    // Page 16, Question 86a - Evaded Military Service During War
    private String evadedMilitaryServiceDuringWarYesCheckbox;
    private String evadedMilitaryServiceDuringWarNoCheckbox;

    // Page 16, Question 86b - Nationality or Status Before Evading Military Service
    private String nationalityOrStatusBeforeEvadingMilitaryService;

    //.....................New..................................

    private String didKnowTraffickingBenefitYesCheckbox;
    private String didKnowTraffickingBenefitNoCheckbox;

    private String hasBeenGovernmentOfficialYesCheckbox;
    private String hasBeenGovernmentOfficialNoCheckbox;

    private String didKnowFamilyTraffickingBenefitYesCheckbox;
    private String didKnowFamilyTraffickingBenefitNoCheckbox;

    private String usedExplosivesYesCheckbox;
    private String usedExplosivesNoCheckbox;

    private String plannedViolenceYesCheckbox;
    private String plannedViolenceNoCheckbox;

    private String incitedViolenceYesCheckbox;
    private String incitedViolenceNoCheckbox;

    private String intendToEndangerYesCheckbox;
    private String intendToEndangerNoCheckbox;

    private String spouseOrChildEngagedInViolenceYesCheckbox;
    private String spouseOrChildEngagedInViolenceNoCheckbox;

    private String servedInMilitaryYesCheckbox;
    private String servedInMilitaryNoCheckbox;

    private String committedTortureYesCheckbox;
    private String committedTortureNoCheckbox;

    private String committedGenocideYesCheckbox;
    private String committedGenocideNoCheckbox;

    // Public Charge Page 18 Question 56
    private String isVAWASelfPetitioner; // VAWA Self-Petitioner (Form I-360)
    private String isSpecialImmigrantJuvenile; // Special Immigrant Juvenile (Form I-360)
    private String isAfghanOrIraqiNational; // Afghan or Iraqi National (Form I-360 or Form DS-157)
    private String isAsylee; // Asylee (Form I-589 or Form I-730)
    private String isRefugee; // Refugee (Form I-590 or Form I-730)
    private String isVictimOfQualifyingCriminalActivity; // Victim of Qualifying Criminal Activity (Form I-918, I-918A, or I-929)
    private String isHumanTraffickingVictim; // Human Trafficking Victim (T nonimmigrant under INA section 245(l))
    private String isCategoryOtherThan245MValidUNonimmigrant; // Any category other than INA section 245(m) but in valid U nonimmigrant status
    private String isCategoryOtherThan245MPendingTStatus; // Any category other than INA section 245(m) but pending T nonimmigrant status
    private String isCubanAdjustmentAct; // Cuban Adjustment Act
    private String isCubanAdjustmentActForBatteredSpousesAndChildren; // Cuban Adjustment Act for Battered Spouses and Children
    private String isDependentUnderHaitianRefugeeFairnessAct; // Dependent Status under Haitian Refugee Immigrant Fairness Act
    private String isDependentForBatteredSpousesUnderHaitianRefugeeFairnessAct; // Dependent Status for Battered Spouses under Haitian Refugee Immigrant Fairness Act
    private String isCubanAndHaitianEntrantsUnderIRCActOf1986; // Cuban and Haitian Entrants under Immigration Reform Control Act of 1986
    private String isLautenbergParolee; // A Lautenberg Parolee
    private String isVietnamCambodiaOrLaosNational; // National of Vietnam, Cambodia, or Laos under Foreign Operations Act
    private String isContinuousResidenceSince1972; // Continuous Residence in the U.S. since before January 1, 1972
    private String isAmerasianHomecomingAct; // Amerasian Homecoming Act
    private String isPolishOrHungarianParolee; // Polish or Hungarian Parolee
    private String isNicaraguanAdjustmentUnderNACARA; // Nicaraguan Adjustment under NACARA
    private String isAmericanIndianBornInCanada; // American Indian born in Canada or Texas Band of Kickapoo Indians
    private String isDefenseAuthorizationActLiberianRefugeeFairness; // Section 7611 of Defense Authorization Act (Liberian Refugee Immigration Fairness)
    private String isSyrianNationalAdjustingStatus; // Syrian National Adjusting Status under Public Law 106-378
    private String isSpouseChildOrParentOfMilitaryMember; // Spouse, Child, or Parent of a U.S. Active-Duty Service Member under NDAA (Form I-130 or Form I-360)
    private String doesNotFallUnderExemptCategories; // I do not fall under any of the exempt categories listed above
    
    // Page 19 Question 61
    private String educationNotHighSchoolDiplomaCheckbox;
    private String educationHighestGrade;

    // Page 19 Question 62
    private List<Certification> certifications;

    // Page 21 Question 76
    private String hasBeenUnlawfullyPresentSince1997YesCheckbox; // Yes option for being unlawfully present in the United States since April 1, 1997
    private String hasBeenUnlawfullyPresentSince1997NoCheckbox;  // No option for being unlawfully present in the United States since April 1, 1997

    // Page 21 Question 77
    private String hasSevereTraffickingReasonYesCheckbox; // Yes option for severe trafficking as the reason for unlawful presence
    private String hasSevereTraffickingReasonNoCheckbox;  // No option for severe trafficking as the reason for unlawful presence
}