/* (C) 2024 */
package com.quick.immi.ai.entity.familyBased.businss;

import lombok.Data;

import java.util.List;

import com.quick.immi.ai.entity.i485.Certification;

@Data
public class Eligibility {
    // Page 9 Question 1-13 - associated with organization
    private boolean associatedWithOrganizationYesCheckbox;
    private boolean associatedWithOrganizationNoCheckbox;
    private List<Organization> organizations;

    // Page 10 Question 14 - Denied admission
    private boolean deniedAdmissionYesCheckbox;
    private boolean deniedAdmissionNoCheckbox;

    // Page 10 Question 15 - Denied visa
    private boolean deniedVisaYesCheckbox;
    private boolean deniedVisaNoCheckbox;

    // Page 10 Question 16 - Worked without authorization
    private boolean workedWithoutAuthorizationYesCheckbox;
    private boolean workedWithoutAuthorizationNoCheckbox;

    // Page 10 Question 17 - Violated nonimmigrant status
    private boolean violatedNonimmigrantStatusYesCheckbox;
    private boolean violatedNonimmigrantStatusNoCheckbox;

    // Page 10 Question 18 - Removal proceedings
    private boolean removalProceedingsYesCheckbox;
    private boolean removalProceedingsNoCheckbox;

    // Page 10 Question 19 - Final order of removal
    private boolean finalOrderOfRemovalYesCheckbox;
    private boolean finalOrderOfRemovalNoCheckbox;

    // Page 10 Question 20 - Prior final order reinstated
    private boolean priorFinalOrderReinstatedYesCheckbox;
    private boolean priorFinalOrderReinstatedNoCheckbox;

    // Page 10 Question 21 - LPR status rescinded
    private boolean lprStatusRescindedYesCheckbox;
    private boolean lprStatusRescindedNoCheckbox;

    // Page 10 Question 22 - Voluntary departure failed
    private boolean voluntaryDepartureFailedYesCheckbox;
    private boolean voluntaryDepartureFailedNoCheckbox;

    // Page 10 Question 23 - Applied for relief from removal
    private boolean appliedForReliefFromRemovalYesCheckbox;
    private boolean appliedForReliefFromRemovalNoCheckbox;

    // Page 10 Question 24a-24c - Compliance with specific visa requirements (J visa)
    private boolean jVisaForeignResidenceRequirementYesCheckbox;
    private boolean jVisaForeignResidenceRequirementNoCheckbox;
    private boolean compliedWithForeignResidenceYesCheckbox;
    private boolean compliedWithForeignResidenceNoCheckbox;
    private boolean grantedWaiverOrRecommendationYesCheckbox;
    private boolean grantedWaiverOrRecommendationNoCheckbox;

    // Page 10 Question 25 - Arrested or Detained
    private boolean arrestedOrDetainedYesCheckbox;
    private boolean arrestedOrDetainedNoCheckbox;

    // Page 10 Question 26 - Committed Crime
    private boolean committedCrimeNoCheckbox;
    private boolean committedCrimeYesCheckbox;

    // Page 11 Question 27 - Pled Guilty or Convicted
    private boolean pledGuiltyOrConvictedYesCheckbox;
    private boolean pledGuiltyOrConvictedNoCheckbox;

    // Page 11 Question 28 - Ordered Punished by Judge
    private boolean orderedPunishedByJudgeNoCheckbox;
    private boolean orderedPunishedByJudgeYesCheckbox;

    // Page 11 Question 29 - Defendant in Criminal Proceeding
    private boolean defendantInCriminalProceedingYesCheckbox;
    private boolean defendantInCriminalProceedingNoCheckbox;

    // Page 11 Question 30 - Violated Controlled Substance Law
    private boolean violatedControlledSubstanceLawNoCheckbox;
    private boolean violatedControlledSubstanceLawYesCheckbox;

    // Page 11 Question 31 - Convicted of Multiple Offenses
    private boolean convictedOfMultipleOffensesNoCheckbox;
    private boolean convictedOfMultipleOffensesYesCheckbox;

    // Page 11 Question 32 - Illicitly Trafficked Controlled Substances
    private boolean illicitlyTraffickedControlledSubstancesYesCheckbox;
    private boolean illicitlyTraffickedControlledSubstancesNoCheckbox;

    // Page 11 Question 33 - Aided in Illicit Trafficking
    private boolean aidedInIllicitTraffickingNoCheckbox;
    private boolean aidedInIllicitTraffickingYesCheckbox;

    // Page 11 Question 34 - Related to Illicit Trafficker
    private boolean relatedToIllicitTraffickerNoCheckbox;
    private boolean relatedToIllicitTraffickerYesCheckbox;

    // Page 11 Question 35 - Engaged in Prostitution
    private boolean engagedInProstitutionNoCheckbox;
    private boolean engagedInProstitutionYesCheckbox;

    // Page 11 Question 36 - Procured Prostitutes
    private boolean procuredProstitutesYesCheckbox;
    private boolean procuredProstitutesNoCheckbox;

    // Page 11 Question 37 - Received Proceeds from Prostitution
    private boolean receivedProceedsFromProstitutionNoCheckbox;
    private boolean receivedProceedsFromProstitutionYesCheckbox;

    // Page 11 Question 38 - Intent to Engage in Illegal Gambling
    private boolean intentToEngageInIllegalGamblingYesCheckbox;
    private boolean intentToEngageInIllegalGamblingNoCheckbox;

    // Page 11 Question 39 - Exercised Immunity from Prosecution
    private boolean exercisedImmunityFromProsecutionNoCheckbox;
    private boolean exercisedImmunityFromProsecutionYesCheckbox;

    // Page 11 Question 40 - Responsible for Religious Violations
    private boolean responsibleForReligiousViolationsYesCheckbox;
    private boolean responsibleForReligiousViolationsNoCheckbox;

    // Page 11 Question 41 - Induced Trafficking for Sex
    private boolean inducedTraffickingForSexNoCheckbox;
    private boolean inducedTraffickingForSexYesCheckbox;

    // Page 11 Question 42 - Trafficked into Slavery
    private boolean traffickedIntoSlaveryNoCheckbox;
    private boolean traffickedIntoSlaveryYesCheckbox;

    // Page 11 Question 43 - Aided Trafficking for Sex or Slavery
    private boolean aidedTraffickingForSexOrSlaveryYesCheckbox;
    private boolean aidedTraffickingForSexOrSlaveryNoCheckbox;

    // Page 11 Question 44 - Related to Trafficker and Benefited
    private boolean relatedToTraffickerAndBenefitedNoCheckbox;
    private boolean relatedToTraffickerAndBenefitedYesCheckbox;

    // Page 11 Question 45 - Engaged in Money Laundering
    private boolean engagedInMoneyLaunderingYesCheckbox;
    private boolean engagedInMoneyLaunderingNoCheckbox;

    // Page 12, Question 46a - Engage in Espionage or Sabotage
    private boolean engageInEspionageOrSabotageYesCheckbox;
    private boolean engageInEspionageOrSabotageNoCheckbox;

    // Page 12, Question 46b - Engage in Illegal Export
    private boolean engageInIllegalExportYesCheckbox;
    private boolean engageInIllegalExportNoCheckbox;

    // Page 12, Question 46c - Engage in Overthrowing Government
    private boolean engageInOverthrowingGovernmentYesCheckbox;
    private boolean engageInOverthrowingGovernmentNoCheckbox;

    // Page 12, Question 46d - Engage in Endangering US
    private boolean engageInEndangeringUSYesCheckbox;
    private boolean engageInEndangeringUSNoCheckbox;

    // Page 12, Question 46e - Engage in Unlawful Activity
    private boolean engageInUnlawfulActivityYesCheckbox;
    private boolean engageInUnlawfulActivityNoCheckbox;

    // Page 12, Question 47 - Engage in Adverse Foreign Policy
    private boolean engageInAdverseForeignPolicyYesCheckbox;
    private boolean engageInAdverseForeignPolicyNoCheckbox;

    // Page 12, Question 48a - Committed or Planned Serious Crime
    private boolean committedOrPlannedSeriousCrimeYesCheckbox;
    private boolean committedOrPlannedSeriousCrimeNoCheckbox;

    // Page 12, Question 48b - Member of a Group Involved in Serious Crime
    private boolean memberOfGroupInvolvedInSeriousCrimeYesCheckbox;
    private boolean memberOfGroupInvolvedInSeriousCrimeNoCheckbox;

    // Page 12, Question 48c - Recruited for a Group Involved in Serious Crime
    private boolean recruitedForGroupInvolvedInSeriousCrimeYesCheckbox;
    private boolean recruitedForGroupInvolvedInSeriousCrimeNoCheckbox;

    // Page 12, Question 48d - Provided Support for a Group Involved in Serious Crime
    private boolean providedSupportForGroupInvolvedInSeriousCrimeYesCheckbox;
    private boolean providedSupportForGroupInvolvedInSeriousCrimeNoCheckbox;

    // Page 12, Question 48e - Provided Support for an Individual or Group
    private boolean providedSupportForIndividualOrGroupYesCheckbox;
    private boolean providedSupportForIndividualOrGroupNoCheckbox;

    // Page 12, Question 49 - Received Military or Weapons Training
    private boolean receivedMilitaryOrWeaponsTrainingYesCheckbox;
    private boolean receivedMilitaryOrWeaponsTrainingNoCheckbox;

    // Page 12, Question 50 - Intent to Engage in Serious Activities
    private boolean intendToEngageInSeriousActivitiesYesCheckbox;
    private boolean intendToEngageInSeriousActivitiesNoCheckbox;

    // Page 12, Question 51a - Spouse or Child Committed Serious Crime
    private boolean spouseOrChildCommittedSeriousCrimeYesCheckbox;
    private boolean spouseOrChildCommittedSeriousCrimeNoCheckbox;

    // Page 12, Question 51b - Spouse or Child Member of a Group
    private boolean spouseOrChildMemberOfGroupYesCheckbox;
    private boolean spouseOrChildMemberOfGroupNoCheckbox;

    // Page 12, Question 51c - Spouse or Child Recruited for a Group
    private boolean spouseOrChildRecruitedForGroupYesCheckbox;
    private boolean spouseOrChildRecruitedForGroupNoCheckbox;

    // Page 12, Question 51d - Spouse or Child Provided Support
    private boolean spouseOrChildProvidedSupportYesCheckbox;
    private boolean spouseOrChildProvidedSupportNoCheckbox;

    // Page 12, Question 51e - Spouse or Child Provided Support for Individual or Group
    private boolean spouseOrChildProvidedSupportForIndividualOrGroupYesCheckbox;
    private boolean spouseOrChildProvidedSupportForIndividualOrGroupNoCheckbox;

    // Page 12, Question 51f - Spouse or Child Received Training
    private boolean spouseOrChildReceivedTrainingYesCheckbox;
    private boolean spouseOrChildReceivedTrainingNoCheckbox;

    // Page 12, Question 52 - Assisted in Weapons Activities
    private boolean assistedInWeaponsActivitiesYesCheckbox;
    private boolean assistedInWeaponsActivitiesNoCheckbox;

    // Page 13, Question 53 - Worked or Volunteered in a Detention Facility
    private boolean workedOrVolunteeredInDetentionFacilityYesCheckbox;
    private boolean workedOrVolunteeredInDetentionFacilityNoCheckbox;

    // Page 13, Question 54 - Participated in a Group Using Weapons
    private boolean participatedInGroupUsingWeaponsYesCheckbox;
    private boolean participatedInGroupUsingWeaponsNoCheckbox;

    // Page 13, Question 55 - Served in Military or Armed Group
    private boolean servedInMilitaryOrArmedGroupYesCheckbox;
    private boolean servedInMilitaryOrArmedGroupNoCheckbox;

    // Page 13, Question 56 - Affiliated with Communist or Totalitarian Party
    private boolean affiliatedWithCommunistOrTotalitarianPartyYesCheckbox;
    private boolean affiliatedWithCommunistOrTotalitarianPartyNoCheckbox;

    // Page 13, Question 57 - Participated in Nazi Persecution
    private boolean participatedInNaziPersecutionYesCheckbox;
    private boolean participatedInNaziPersecutionNoCheckbox;

    // Page 13, Question 58a - Involved in Torture or Genocide
    private boolean involvedInTortureOrGenocideYesCheckbox;
    private boolean involvedInTortureOrGenocideNoCheckbox;

    // Page 13, Question 58b - Killed Any Person
    private boolean killedAnyPersonYesCheckbox;
    private boolean killedAnyPersonNoCheckbox;

    // Page 13, Question 58c - Severely Injured Any Person
    private boolean severelyInjuredAnyPersonYesCheckbox;
    private boolean severelyInjuredAnyPersonNoCheckbox;

    // Page 13, Question 58d - Engaged in Non-Consensual Sexual Contact
    private boolean engagedInNonConsensualSexualContactYesCheckbox;
    private boolean engagedInNonConsensualSexualContactNoCheckbox;

    // Page 13, Question 58e - Limited Religious Beliefs
    private boolean limitedReligiousBeliefsYesCheckbox;
    private boolean limitedReligiousBeliefsNoCheckbox;

    // Page 13, Question 59 - Recruited Child Soldiers
    private boolean recruitedChildSoldiersYesCheckbox;
    private boolean recruitedChildSoldiersNoCheckbox;

    // Page 13, Question 60 - Used Child Soldiers in Combat
    private boolean usedChildSoldiersInCombatYesCheckbox;
    private boolean usedChildSoldiersInCombatNoCheckbox;

    // Page 13, Question 61 - Subject to Public Charge
    private boolean subjectToPublicChargeYesCheckbox;
    private boolean subjectToPublicChargeNoCheckbox;

    // Page 13, Question 62 - Household Size
    private String householdSize;

    // Page 13, Question 63 - Household Income Range
    private boolean householdIncomeRange0to27kCheckbox;
    private boolean householdIncomeRange27kto52kCheckbox;
    private boolean householdIncomeRange52kto85kCheckbox;
    private boolean householdIncomeRange85kto141kCheckbox;
    private boolean householdIncomeRangeOver141kCheckbox;

    // Page 13, Question 64 - Household Assets Value
    private boolean householdAssetsValue0to18kCheckbox;
    private boolean householdAssetsValue18kto136kCheckbox;
    private boolean householdAssetsValue136kto321kCheckbox;
    private boolean householdAssetsValue321kto707kCheckbox;
    private boolean householdAssetsValueOver707kCheckbox;

    // Page 14, Question 65 - Liabilities Value
    private boolean liabilitiesValue0Checkbox;
    private boolean liabilitiesValue1kto10kCheckbox;
    private boolean liabilitiesValue10kto57kCheckbox;
    private boolean liabilitiesValue57kto186kCheckbox;
    private boolean liabilitiesValueOver186kCheckbox;

    // Page 14, Question 66 - Education Level
    private boolean educationGrades1to11Checkbox;
    private boolean education12thNoDiplomaCheckbox;
    private boolean educationHighSchoolDiplomaCheckbox;
    private boolean educationSomeCollegeNoDegreeCheckbox;
    private boolean educationAssociatesDegreeCheckbox;
    private boolean educationBachelorsDegreeCheckbox;
    private boolean educationMastersDegreeCheckbox;
    private boolean educationProfessionalDegreeCheckbox;
    private boolean educationDoctorateDegreeCheckbox;

    // Page 14, Question 67 - Certifications, Licenses, Skills
    private String certificationsLicensesSkills;

    // Page 14, Question 68a - Received SSI or TANF
    private boolean receivedSSIOrTANFYesCheckbox;
    private boolean receivedSSIOrTANFNoCheckbox;

    // Page 14, Question 68b - Received Institutionalization
    private boolean receivedInstitutionalizationYesCheckbox;
    private boolean receivedInstitutionalizationNoCheckbox;

    // Page 14, Question 68c - Detailed Benefits Information
    private List<Benefit> benefitRecords;

    // Page 14, Question 68d - Detailed Institutionalization Information
    private List<Institutionalization> institutionalizationRecords;

    // Page 15, Question 69a - Failed to Attend Removal Proceeding
    private boolean failedToAttendRemovalProceedingYesCheckbox;
    private boolean failedToAttendRemovalProceedingNoCheckbox;

    // Page 15, Question 69b - Believe Had Reasonable Cause
    private boolean believeHadReasonableCauseYesCheckbox;
    private boolean believeHadReasonableCauseNoCheckbox;

    // Page 15, Question 70 - Submitted Fraudulent Documentation
    private boolean submittedFraudulentDocumentationYesCheckbox;
    private boolean submittedFraudulentDocumentationNoCheckbox;

    // Page 15, Question 71 - Lied or Misrepresented for Immigration Benefit
    private boolean liedOrMisrepresentedForImmigrationBenefitYesCheckbox;
    private boolean liedOrMisrepresentedForImmigrationBenefitNoCheckbox;

    // Page 15, Question 72 - Falsely Claimed US Citizenship
    private boolean falselyClaimedUSCitizenshipYesCheckbox;
    private boolean falselyClaimedUSCitizenshipNoCheckbox;

    // Page 15, Question 73 - Been a Stowaway
    private boolean beenAStowawayYesCheckbox;
    private boolean beenAStowawayNoCheckbox;

    // Page 15, Question 74 - Knowingly Encouraged Illegal Entry
    private boolean knowinglyEncouragedIllegalEntryYesCheckbox;
    private boolean knowinglyEncouragedIllegalEntryNoCheckbox;

    // Page 15, Question 75 - Under Civil Penalty for Fraudulent Documents
    private boolean underCivilPenaltyForFraudulentDocumentsYesCheckbox;
    private boolean underCivilPenaltyForFraudulentDocumentsNoCheckbox;

    // Page 15, Question 76 - Excluded, Deported, or Removed
    private boolean excludedDeportedOrRemovedYesCheckbox;
    private boolean excludedDeportedOrRemovedNoCheckbox;

    // Page 15, Question 77 - Entered Without Inspection
    private boolean enteredWithoutInspectionYesCheckbox;
    private boolean enteredWithoutInspectionNoCheckbox;

    // Page 15, Question 78a - Unlawfully Present 180 Days to 1 Year
    private boolean unlawfullyPresent180DaysTo1YearYesCheckbox;
    private boolean unlawfullyPresent180DaysTo1YearNoCheckbox;

    // Page 15, Question 78b - Unlawfully Present 1 Year or More
    private boolean unlawfullyPresent1YearOrMoreYesCheckbox;
    private boolean unlawfullyPresent1YearOrMoreNoCheckbox;

    // Page 15, Question 79a - Reentered After Unlawfully Present
    private boolean reenteredAfterUnlawfullyPresentYesCheckbox;
    private boolean reenteredAfterUnlawfullyPresentNoCheckbox;

    // Page 15, Question 79b - Reentered After Deportation
    private boolean reenteredAfterDeportationYesCheckbox;
    private boolean reenteredAfterDeportationNoCheckbox;

    // Page 15, Question 80 - Plan to Practice Polygamy
    private boolean planToPracticePolygamyYesCheckbox;
    private boolean planToPracticePolygamyNoCheckbox;

    // Page 15, Question 81 - Accompanying Inadmissible Foreign National
    private boolean accompanyingInadmissibleForeignNationalYesCheckbox;
    private boolean accompanyingInadmissibleForeignNationalNoCheckbox;

    // Page 15, Question 82 - Assisted in Withholding Custody
    private boolean assistedInWithholdingCustodyYesCheckbox;
    private boolean assistedInWithholdingCustodyNoCheckbox;

    // Page 15, Question 83 - Voted in Violation of Law
    private boolean votedInViolationOfLawYesCheckbox;
    private boolean votedInViolationOfLawNoCheckbox;

    // Page 15, Question 84 - Renounced Citizenship to Avoid Tax
    private boolean renouncedCitizenshipToAvoidTaxYesCheckbox;
    private boolean renouncedCitizenshipToAvoidTaxNoCheckbox;

    // Page 16, Question 85a - Applied for Exemption from Military Service
    private boolean appliedForExemptionFromMilitaryServiceYesCheckbox;
    private boolean appliedForExemptionFromMilitaryServiceNoCheckbox;

    // Page 16, Question 85b - Relieved from Military Service
    private boolean relievedFromMilitaryServiceYesCheckbox;
    private boolean relievedFromMilitaryServiceNoCheckbox;

    // Page 16, Question 85c - Convicted of Desertion
    private boolean convictedOfDesertionYesCheckbox;
    private boolean convictedOfDesertionNoCheckbox;

    // Page 16, Question 86a - Evaded Military Service During War
    private boolean evadedMilitaryServiceDuringWarYesCheckbox;
    private boolean evadedMilitaryServiceDuringWarNoCheckbox;

    // Page 16, Question 86b - Nationality or Status Before Evading Military Service
    private String nationalityOrStatusBeforeEvadingMilitaryService;

    //.....................New..................................

    private boolean didKnowTraffickingBenefitYesCheckbox;
    private boolean didKnowTraffickingBenefitNoCheckbox;

    private boolean hasBeenGovernmentOfficialYesCheckbox;
    private boolean hasBeenGovernmentOfficialNoCheckbox;

    private boolean didKnowFamilyTraffickingBenefitYesCheckbox;
    private boolean didKnowFamilyTraffickingBenefitNoCheckbox;

    private boolean usedExplosivesYesCheckbox;
    private boolean usedExplosivesNoCheckbox;

    private boolean plannedViolenceYesCheckbox;
    private boolean plannedViolenceNoCheckbox;

    private boolean incitedViolenceYesCheckbox;
    private boolean incitedViolenceNoCheckbox;

    private boolean intendToEndangerYesCheckbox;
    private boolean intendToEndangerNoCheckbox;

    private boolean spouseOrChildEngagedInViolenceYesCheckbox;
    private boolean spouseOrChildEngagedInViolenceNoCheckbox;

    private boolean servedInMilitaryYesCheckbox;
    private boolean servedInMilitaryNoCheckbox;

    private boolean committedTortureYesCheckbox;
    private boolean committedTortureNoCheckbox;

    private boolean committedGenocideYesCheckbox;
    private boolean committedGenocideNoCheckbox;

    // Public Charge Page 18 Question 56
    private boolean isVAWASelfPetitioner; // VAWA Self-Petitioner (Form I-360)
    private boolean isSpecialImmigrantJuvenile; // Special Immigrant Juvenile (Form I-360)
    private boolean isAfghanOrIraqiNational; // Afghan or Iraqi National (Form I-360 or Form DS-157)
    private boolean isAsylee; // Asylee (Form I-589 or Form I-730)
    private boolean isRefugee; // Refugee (Form I-590 or Form I-730)
    private boolean isVictimOfQualifyingCriminalActivity; // Victim of Qualifying Criminal Activity (Form I-918, I-918A, or I-929)
    private boolean isHumanTraffickingVictim; // Human Trafficking Victim (T nonimmigrant under INA section 245(l))
    private boolean isCategoryOtherThan245MValidUNonimmigrant; // Any category other than INA section 245(m) but in valid U nonimmigrant status
    private boolean isCategoryOtherThan245MPendingTStatus; // Any category other than INA section 245(m) but pending T nonimmigrant status
    private boolean isCubanAdjustmentAct; // Cuban Adjustment Act
    private boolean isCubanAdjustmentActForBatteredSpousesAndChildren; // Cuban Adjustment Act for Battered Spouses and Children
    private boolean isDependentUnderHaitianRefugeeFairnessAct; // Dependent Status under Haitian Refugee Immigrant Fairness Act
    private boolean isDependentForBatteredSpousesUnderHaitianRefugeeFairnessAct; // Dependent Status for Battered Spouses under Haitian Refugee Immigrant Fairness Act
    private boolean isCubanAndHaitianEntrantsUnderIRCActOf1986; // Cuban and Haitian Entrants under Immigration Reform Control Act of 1986
    private boolean isLautenbergParolee; // A Lautenberg Parolee
    private boolean isVietnamCambodiaOrLaosNational; // National of Vietnam, Cambodia, or Laos under Foreign Operations Act
    private boolean isContinuousResidenceSince1972; // Continuous Residence in the U.S. since before January 1, 1972
    private boolean isAmerasianHomecomingAct; // Amerasian Homecoming Act
    private boolean isPolishOrHungarianParolee; // Polish or Hungarian Parolee
    private boolean isNicaraguanAdjustmentUnderNACARA; // Nicaraguan Adjustment under NACARA
    private boolean isAmericanIndianBornInCanada; // American Indian born in Canada or Texas Band of Kickapoo Indians
    private boolean isDefenseAuthorizationActLiberianRefugeeFairness; // Section 7611 of Defense Authorization Act (Liberian Refugee Immigration Fairness)
    private boolean isSyrianNationalAdjustingStatus; // Syrian National Adjusting Status under Public Law 106-378
    private boolean isSpouseChildOrParentOfMilitaryMember; // Spouse, Child, or Parent of a U.S. Active-Duty Service Member under NDAA (Form I-130 or Form I-360)
    private boolean doesNotFallUnderExemptCategories; // I do not fall under any of the exempt categories listed above
    
    // Page 19 Question 61
    private boolean educationNotHighSchoolDiplomaCheckbox;
    private String educationHighestGrade;

    // Page 19 Question 62
    private List<Certification> certifications;

    // Page 21 Question 76
    private boolean hasBeenUnlawfullyPresentSince1997YesCheckbox; // Yes option for being unlawfully present in the United States since April 1, 1997
    private boolean hasBeenUnlawfullyPresentSince1997NoCheckbox;  // No option for being unlawfully present in the United States since April 1, 1997

    // Page 21 Question 77
    private boolean hasSevereTraffickingReasonYesCheckbox; // Yes option for severe trafficking as the reason for unlawful presence
    private boolean hasSevereTraffickingReasonNoCheckbox;  // No option for severe trafficking as the reason for unlawful presence
}