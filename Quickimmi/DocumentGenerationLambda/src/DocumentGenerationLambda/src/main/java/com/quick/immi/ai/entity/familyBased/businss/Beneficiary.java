/* (C) 2024 */
package com.quick.immi.ai.entity.familyBased.businss;
import lombok.Data;

import java.util.List;

@Data
public class Beneficiary {
  //////basic information
  // 287
  private String alienNumber;
  // 288
  private String uSCISOnlineAccountNumber;
  // 289
  private String lastName;
  // 290
  private String firstName;
  // 291
  private String middleName;
  // 292 - 294
  private List<BeneficiaryOtherName> beneficiaryOtherNames;
  private List<String> beneficiaryNationalities;
  // Page2-Question18
  private String passportNum;

  // Page2-Question19
  private String travelDocNum;

  // Page2-Question20
  private String passportOrTravelDocExpDate;

  // Page2-Question21
  private String passportIssuingCountry;

  // Page3-Question27: Current immigration status
  private String currentStatus;
  // 295
  private String cityOfBirth;
  // 296
  private String countryOfBirth;
  // 325
  private String dateOfBirth;
  // Part 4-9-M
  private boolean sexMaleCheckbox;
  // Part 4-9-F
  private boolean sexFemaleCheckbox;

  //If the beneficiary lives outside the United States in a home
  //without a street number or name, leave Item Numbers 11.a.
  //and 11.b. blank.
  private Address physicalAddress;
  private Address mailingAddress;
  private List<Address> addressHistories;
  private String daytimePhoneNumber;
  //350
  private String mobilePhoneNumber;
  //351
  private String emailAddress;

  // Part 4-10
  // Has anyone else ever filed a petition for the beneficiary
  private boolean previousPetitionFiledYesCheckbox;
  private boolean previousPetitionFiledNoCheckbox;
  private boolean previousPetitionFiledUnknownCheckbox;

  //Provide the address in the United States where the beneficiary
  //intends to live, if different from Item Numbers 11.a. - 11.h. If
  //the address is the same, type or print "SAME" in Item Number
  private boolean intendToLiveUsAddressSameAsPhysicalAddressYesCheckbox;
  private boolean intendToLiveUsAddressSameAsPhysicalAddressNoCheckbox;
  private Address intendToLiveUsAddress;

  //Provide the beneficiary's address outside the United States, if
  //different from Item Numbers 11.a. - 11.h. If the address is the
  //same, type or print "SAME" in Item Number 13.a.
  private boolean outsideUsAddressSameAsPhysicalAddressYesCheckbox;
  private boolean outsideUsAddressSameAsPhysicalAddressNoCheckbox;
  private Address outsideUsAddress;

  private Address mostRecentOutSideUsAddressBefore5YearsAgo;

  private LastArrivalInformation lastArrivalInformation;
  private List<EmploymentHistory> employmentHistories;
  private EmploymentHistory currentEmployment;

  //Part 4-53
  private boolean beneficiaryInImmigrationProceedingsYesCheckbox;
  private boolean beneficiaryInImmigrationProceedingsNoCheckbox;

  //Part 4-54
  private boolean removalCheckbox;
  private boolean exclusionCheckbox;
  private boolean rescissionCheckbox;
  private boolean otherJudicialProceedingsCheckbox;

  //421
  private String cityOfProceedings;
  //424
  private String stateOfProceedings;
  //425
  private String dateOfProceedings;

  //If the beneficiary's native written language does not use
  //Roman letters, type or print his or her name and foreign
  //address in their native written language.
  //part 4:
  //427
  private String lastNameUsedNativeLanguage;
  //428
  private String firstNameUsedNativeLanguage;
  //426
  private String middleNameUsedNativeLanguage;
  private Address addressNativeLanguage;

  //442 ===== if filling for spouse
  private boolean neverLiveTogetherCheckbox;
  private Address lastAddressLivedTogether;
  //463
  private String adjustmentOfStatusCity;
  //464
  private String adjustmentOfStatusState;

  //451
  private String immigrantVisaCity;
  //452
  private String immigrantVisaProvince;
  // 453
  private String immigrantVisaCountry;

  // Page3-Question28: Names as appeared on I-94

  private boolean hasSocialSecurityCardNoCheckbox;
  private boolean hasSocialSecurityCardYesCheckbox;
  // 341
  private String ssn;

  // Page 4, Question 1 - Applied for Immigrant Visa
  private boolean appliedForImmigrantVisaNoCheckbox;
  private boolean appliedForImmigrantVisaYesCheckbox;

  private Biographic biographicInfo;

  private Family family;
  private MaritalInfo maritalInfo;
  private boolean isFluentEnglish;
  private String nativeLanguage;

  private DisabilityAccommodation disabilityAccommodation;

  private boolean appliedImmigrationVisaBefore;
  private List<UsEmbassy> usEmbassies;
  private boolean previouslyAppliedForPermanentResidence;
  private boolean heldPermanentResidentStatus;

  // todo new added
  private boolean otherdateOfBirthYes;
  private boolean otherdateOfBirthNo;
  private String otherdateOfBirth1;
  private String otherdateOfBirth2;

  private boolean alienNumberYes;
  private boolean alienNumberNo;
  private boolean otherAlienNumberYes;
  private boolean otherAlienNumberNo;
  private String otherAlienNumber1;
  private String otherAlienNumber2;

  private boolean otherGender;

  private String expirationDateCurrentStatus;
  private boolean issuedAlienCrewmanVisaYes;
  private boolean issuedAlienCrewmanVisaNo;
  private boolean arrivalToJoinVesselOrAircraftYes;
  private boolean arrivalToJoinVesselOrAircraftNo;
  private boolean isMailingAddressYes;
  private boolean isMailingAddressNo;
  private boolean hasSSNYes;
  private boolean hasSSNNo;
  private boolean requestSSNYes;
  private boolean requestSSNNo;
  private boolean disclosureYes;
  private boolean disclosureNo;
  private EmploymentHistory mostRecentOutSideUsEmploymentHistory5YearsAgo;

  private AffidavitExemption affidavitExemption;

}
