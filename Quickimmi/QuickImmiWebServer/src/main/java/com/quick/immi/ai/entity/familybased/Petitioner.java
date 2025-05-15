/* (C) 2024 */
package com.quick.immi.ai.entity.familybased;

import java.util.List;
import lombok.Data;

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
  private String inCareOf;
  // 140
  private String streetNumberAndName;
  // 144
  private boolean aptCheckbox;
  private boolean steCheckbox;
  private boolean flrCheckbox;
  private String aptSteFlrNumber;
  // 145
  private String cityOrTown;
  // 149
  private String state;
  // 148
  private String zipCode;
  // 146
  private String province;
  // 147
  private String postalCode;
  // 150
  private String country;
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
  private Parent mother;
  private Parent father;

  // Part2-36
  private boolean iAmUsCitizenCheckbox;
  private boolean iAmLawfulPermanentResidentCheckbox;

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
}
