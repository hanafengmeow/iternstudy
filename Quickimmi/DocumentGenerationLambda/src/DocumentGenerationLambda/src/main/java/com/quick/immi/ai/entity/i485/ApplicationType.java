package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class ApplicationType {

    // Question 1 - Adjustment of status with the Executive Office for Immigration Review (EOIR)
    private String isFilingWithEOIRYes;
    private String isFilingWithEOIRNo;

    // Question 2 - Receipt Number and Priority Date
    private String receiptNumber;
    private String priorityDate;

    // Applicant Type
    private String isPrincipalApplicant;
    private String isDerivativeApplicant;

    // Principal Applicant's Information
    private String principalApplicantFamilyName;
    private String principalApplicantGivenName;
    private String principalApplicantMiddleName;
    private String principalApplicantANumber;
    private String principalApplicantDateOfBirth;

    // Family-Based Categories
    private String isSpouseOfUSCitizen; // Spouse of a U.S. Citizen.
    private String isUnmarriedChildUnder21OfUSCitizen; // Unmarried child under 21 years of age of a U.S. citizen.
    private String isParentOfUSCitizen; // Parent of a U.S. citizen (if the citizen is at least 21 years of age).
    private String isPersonAdmittedAsFianceOrChildOfFianceOfUSCitizen; // Person admitted to the United States as a fiancé(e) or child of a fiancé(e) of a U.S. citizen (K-1/K-2 Nonimmigrant).
    private String isWidowOrWidowerOfUSCitizen; // Widow or widower of a U.S. citizen.
    private String isSpouseChildParentOfDeceasedUSArmedForcesMember; // Spouse, child, or parent of a deceased U.S. active-duty service member in the armed forces under the National Defense Authorization Act (NDAA).
    private String isUnmarriedSonDaughterOver21OfUSCitizen; // Unmarried son or daughter of a U.S. citizen and I am 21 years of age or older.
    private String isMarriedSonDaughterOfUSCitizen; // Married son or daughter of a U.S. citizen.
    private String isBrotherOrSisterOfUSCitizen; // Brother or sister of a U.S. citizen (if the citizen is at least 21 years of age).
    private String isSpouseOfLawfulPermanentResident; // Spouse of a lawful permanent resident.
    private String isUnmarriedChildUnder21OfLawfulPermanentResident; // Unmarried child under 21 years of age of a lawful permanent resident.
    private String isUnmarriedSonDaughterOver21OfLawfulPermanentResident; // Unmarried son or daughter of a lawful permanent resident and I am 21 years of age or older.
    private String isVAWASelfPetitioningSpouse; // VAWA self-petitioning spouse of a U.S. citizen or lawful permanent resident.
    private String isVAWASelfPetitioningChild; // VAWA self-petitioning child of a U.S. citizen or lawful permanent resident.
    private String isVAWASelfPetitioningParent; // VAWA self-petitioning parent of a U.S. citizen (if the citizen is at least 21 years of age).

    // Employment-Based Categories
    private String isAlienInvestor; // Alien Investor, Form I-526 or Form I-526E
    private String isAlienOfExtraordinaryAbility; // Alien of Extraordinary Ability
    private String isOutstandingProfessorOrResearcher; // Outstanding Professor or Researcher
    private String isMultinationalExecutiveOrManager; // Multinational Executive or Manager
    private String isAdvancedDegreeOrExceptionalAbility; // Member of the Professions Holding an Advanced Degree or Alien of Exceptional Ability
    private String isProfessionalWithBachelorDegree; // A Professional (requiring a bachelor's degree or foreign equivalent)
    private String isSkilledWorker; // A Skilled Worker (requiring at least 2 years of specialized training or experience)
    private String isOtherWorker; // Any Other Worker (requiring less than 2 years of training or experience)
    private String isNationalInterestWaiverApplicant; // An Alien Applying For a National Interest Waiver
    private String isAdjustingBasedOnI140SelfPetition; // N/A (I am adjusting on the basis of a Form I-140 self-petition)
    private String isI140FiledByRelativeYes; // Yes
    private String isI140FiledByRelativeNo; // No
    private String relativeIsFather; // Father
    private String relativeIsMother; // Mother
    private String relativeIsChild; // Child
    private String relativeIsAdultSon; // Adult Son
    private String relativeIsAdultDaughter; // Adult Daughter
    private String relativeIsBrother; // Brother
    private String relativeIsSister; // Sister
    private String relativeIsNoneOfThese1; // None of These
    private String relativeIsUSCitizen; // U.S. Citizen
    private String relativeIsUSNational; // U.S. National
    private String relativeIsLawfulPermanentResident; // Lawful Permanent Resident
    private String relativeIsNoneOfThese2; // None of These

    // Special Immigrant Categories
    private String isSpecialImmigrantJuvenile; // Special Immigrant Juvenile, Form I-360
    private String isCertainAfghanOrIraqiNational; // Certain Afghan or Iraqi National, Form I-360 or Form DS-157
    private String isCertainInternationalBroadcaster; // Certain International Broadcaster, Form I-360
    private String isCertainG4OrNATO6Employee; // Certain G-4 International Organization or Family Member or NATO-6 Employee or Family Member, Form I-360
    private String isCertainUSArmedForcesMember; // Certain U.S. Armed Forces Members (also known as the Six and Six program), Form I-360
    private String isPanamaCanalZoneEmployee; // Panama Canal Zone Employees, Form I-360
    private String isCertainPhysician; // Certain Physicians, Form I-360
    private String isCertainEmployeeOrFormerEmployeeUSGovernment; // Certain Employee or Former Employee of the U.S. Government Abroad, DS-1884
    private String isMinisterOfReligion; // Minister of Religion
    private String isOtherReligiousWorker; // Other Religious Worker

    // Asylee or Refugee Categories
    private String isAsylumStatus;
    private String dateAsylumGranted;
    private String isRefugeeStatus;
    private String dateRefugeeGranted;

    // Human Trafficking or Crime Victim Categories
    private String isHumanTraffickingVictim;
    private String isQualifyingCriminalActivityVictim;

    // Special Programs Based on Public Laws
    private String isCubanAdjustmentAct; // The Cuban Adjustment Act
    private String isVictimOfBatteryUnderCubanAdjustmentAct; // A Victim of Battery or Extreme Cruelty as a Spouse or Child Under the Cuban Adjustment Act
    private String isAdjustingBasedOnHaitianRefugeeFairnessAct; // Applicant Adjusting Based on Dependent Status Under the Haitian Refugee Immigrant Fairness Act
    private String isVictimOfBatteryUnderHaitianRefugeeFairnessAct; // A Victim of Battery or Extreme Cruelty as a Spouse or Child Applying Based on Dependent Status Under the Haitian Refugee Immigrant Fairness Act
    private String isLautenbergParolee; // Lautenberg Parolees
    private String isDiplomatUnableToReturnHome; // Diplomats or High-Ranking Officials Unable to Return Home (Section 13 of the Act of September 11, 1957)
    private String isNationalOfVietnamCambodiaLaos; // Nationals of Vietnam, Cambodia, and Laos Applying for Adjustment of Status Under section 586 of Public Law 106-429
    private String isAdjustingUnderAmerasianAct; // Applicant Adjusting Under the Amerasian Act (October 22, 1982), Form I-360    

    // Additional Options
    private String isDiversityVisaProgram;
    private String diversityVisaRankNumber;
    private String isContinuousResidenceSinceBefore1972;
    private String isBornUnderDiplomaticStatus;
    private String isSNonimmigrant;
    private String isOtherEligibility;
    private String otherEligibility;

    // INA Section 245(i) and Child Status Protection Act (CSPA)
    private String isApplyingUnderINA245iYes;
    private String isApplyingUnderINA245iNo;
    private String isApplyingUnderCSPAYes;
    private String isApplyingUnderCSPANo;
}
