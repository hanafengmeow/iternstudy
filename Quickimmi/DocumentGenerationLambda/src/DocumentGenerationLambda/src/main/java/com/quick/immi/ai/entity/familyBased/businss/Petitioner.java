/* (C) 2024 */
package com.quick.immi.ai.entity.familyBased.businss;

import lombok.Data;

import java.util.List;

@Data
public class Petitioner {
  // 132
  private String alienNumber;
  // 133
  private String uSCISOnlineAccountNumber;
  // 112
  private String ssn;
  // 129
  private String lastName;
  // 130
  private String firstName;
  // 131
  private String middleName;
  // 178 - 180
  private List<PetitionerOtherName> petitionerOtherNames;

  // 177
  private String cityOfBirth;
  // 137
  private String countryOfBirth;
  // 134
  private String dateOfBirth;

  private String stateOrProvinceOfBirth;

  // Part 2-9-M
  private boolean sexMaleCheckbox;
  // Part 2-9-F
  private boolean sexFemaleCheckbox;
  // 151
//  private String inCareOf;
  // 140
//  private String streetNumberAndName;
//  // 144
//  private boolean aptCheckbox;
//  private boolean steCheckbox;
//  private boolean flrCheckbox;
//  private String aptSteFlrNumber;
//  // 145
//  private String cityOrTown;
//  // 149
//  private String state;
//  // 148
//  private String zipCode;
//  // 146
//  private String province;
//  // 147
//  private String postalCode;
//  // 150
//  private String country;

  private Address mailingAddress;

  private Address physicalAddress;

  // Part2-11 Yes
  private boolean mailingAddressSameAsPhysicalAddressYesCheckbox;
  // No
  private boolean mailingAddressSameAsPhysicalAddressNoCheckbox;

  private List<Address> addressHistory;

  // 181
  private String timesMarried;
  // Part2-17
  private boolean currentMartialStatusSingleCheckbox;
  private boolean currentMartialStatusMarriedCheckbox;
  private boolean currentMartialStatusDivorcedCheckbox;
  private boolean currentMartialStatusWidowedCheckbox;
  private boolean currentMartialStatusSeparatedCheckbox;
  private boolean currentMartialStatusAnnulledCheckbox;
  // 197
  private String dateOfCurrentMarriage;
  // 226
  private String cityOfCurrentMarriage;
  // 227
  private String stateOfCurrentMarriage;
  // 228
  private String provinceOfCurrentMarriage;
  // 229
  private String countryOfCurrentMarriage;

  private List<Spouses> spouses;
  private Parent parent1;
  private Parent parent2;

  // Part2-36
  private boolean dfUsCitizenCheckbox;
  private boolean dfLawfulPermanentResidentCheckbox;

  // Part2-37
  private boolean citizenshipAcquiredByBirthCheckbox;
  private boolean citizenshipAcquiredByNaturalizationCheckbox;
  private boolean citizenshipAcquiredThroughParentsCheckbox;

  // Part2-38
  private boolean obtainedNaturalizationCertificateYesCheckbox;
  private boolean obtainedNaturalizationCertificateNoCheckbox;

  // 217
  private String certificateNumber;
  // 221
  private String placeOfIssuance;
  // 220
  private String dateOfIssuance;

  // 230
  private String classOfAdmission;
  // 231
  private String dateOfAdmission;
  // 232
  private String placeOfAdmissionCity;
  // 286
  private String placeOfAdmissionState;
  // Part2-41
  private boolean gainedStatusThroughMarriageYesCheckbox;
  private boolean gainedStatusThroughMarriageNoCheckbox;

  private List<EmploymentHistory> employmentHistory;

  private Biographic biographic;

  //contact information
  private String daytimeTelephoneNumber;

  // Mobile Telephone Number (if any)
  private String mobileTelephoneNumber;

  //  Email Address (if any)
  private String emailAddress;

  // .................. New .............

  private boolean isFilingWithEOIRYes;
  private boolean isFilingWithEOIRNo;
  private String receiptNumber;
  private String priorityDate;
  private boolean isPrincipalApplicant;
  private boolean isDerivativeApplicant;
  private String principalApplicantFamilyName;
  private String principalApplicantGivenName;
  private String principalApplicantMiddleName;
  private String principalApplicantANumber;
  private String principalApplicantDateOfBirth;
  private boolean isSpouseOfUSCitizen; // Spouse of a U.S. Citizen.
  private boolean isUnmarriedChildUnder21OfUSCitizen; // Unmarried child under 21 years of age of a U.S. citizen.
  private boolean isParentOfUSCitizen; // Parent of a U.S. citizen (if the citizen is at least 21 years of age).
  private boolean isPersonAdmittedAsFianceOrChildOfFianceOfUSCitizen; // Person admitted to the United States as a fiancé(e) or child of a fiancé(e) of a U.S. citizen (K-1/K-2 Nonimmigrant).
  private boolean isWidowOrWidowerOfUSCitizen; // Widow or widower of a U.S. citizen.
  private boolean isSpouseChildParentOfDeceasedUSArmedForcesMember; // Spouse, child, or parent of a deceased U.S. active-duty service member in the armed forces under the National Defense Authorization Act (NDAA).
  private boolean isUnmarriedSonDaughterOver21OfUSCitizen; // Unmarried son or daughter of a U.S. citizen and I am 21 years of age or older.
  private boolean isMarriedSonDaughterOfUSCitizen; // Married son or daughter of a U.S. citizen.
  private boolean isBrotherOrSisterOfUSCitizen; // Brother or sister of a U.S. citizen (if the citizen is at least 21 years of age).
  private boolean isSpouseOfLawfulPermanentResident; // Spouse of a lawful permanent resident.
  private boolean isUnmarriedChildUnder21OfLawfulPermanentResident; // Unmarried child under 21 years of age of a lawful permanent resident.
  private boolean isUnmarriedSonDaughterOver21OfLawfulPermanentResident; // Unmarried son or daughter of a lawful permanent resident and I am 21 years of age or older.
  private boolean isVAWASelfPetitioningSpouse; // VAWA self-petitioning spouse of a U.S. citizen or lawful permanent resident.
  private boolean isVAWASelfPetitioningChild; // VAWA self-petitioning child of a U.S. citizen or lawful permanent resident.
  private boolean isVAWASelfPetitioningParent; // VAWA self-petitioning parent of a U.S. citizen (if the citizen is at least 21 years of age).
  private boolean isApplyingUnderINA245iYes;
  private boolean isApplyingUnderINA245iNo;
  private boolean isApplyingUnderCSPAYes;
  private boolean isApplyingUnderCSPANo;
}
