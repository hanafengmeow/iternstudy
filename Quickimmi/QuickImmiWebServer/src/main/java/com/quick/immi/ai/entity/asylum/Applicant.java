/* (C) 2024 */
package com.quick.immi.ai.entity.asylum;

import com.quick.immi.ai.entity.NameEntity;
import java.util.List;
import lombok.Data;

@Data
public class Applicant {
  private Long passportDocumentId;

  private List<Long> passportStampPageDocumentIds;
  // 13
  private String alienNumber;
  // 14
  private String ssn;
  // 70
  private String uscisOnlineAccountNumber;
  // 15
  private String lastName;
  // 21
  private String firstName;
  // 22
  private String middleName;
  // 16
  private String namesUsedBefore;
  private String namesUsedBeforeLastName;
  private String namesUsedBeforeFirstName;
  private String namesUsedBeforeMiddleName;

  private List<NameEntity> nameEntitiesUsedBefore;
  // 17
  private String streetNumberAndName;
  // 25
  private String aptNumber;
  // 18
  private String city;
  // 26
  private String state;
  // 27
  private String zipCode;

  private String province;
  private String postalCode;
  private String country;
  private String aptCheckbox;
  private String steCheckbox;
  private String flrCheckbox;

  // 23
  private String telePhoneAreaCode;
  // 24 after remove the area code
  private String telePhoneNumber;
  // 65
  private String inCareOf;
  // 61
  private String telePhoneAreaCodeOfMailingAddress;
  // 62
  private String telePhoneNumberOfMailingAddress;
  // 29
  private String streetNumberAndNameOfMailingAddress;
  // 28
  private String aptNumberOfMailingAddress;
  // 31
  private String cityOfMailingAddress;
  // 32
  private String stateOfMailingAddress;
  // 30
  private String zipCodeOfMailingAddress;

  // M
  private boolean genderMaleCheckbox;
  // F
  private boolean genderFemaleCheckbox;
  // S
  private boolean maritalStatusSingleCheckbox;
  // M
  private boolean maritalStatusMarriedCheckbox;
  // D
  private boolean maritalStatusDivorcedCheckbox;
  // W
  private boolean maritalStatusWidowedCheckbox;

  private String birthDate;
  private String cityAndCountryOfBirth;
  private String nationality;
  private String nationalityAtBirth;
  private String race;
  private String religion;

  // A
  private boolean immigrationCourtProceedingACheckbox;
  // B
  private boolean immigrationCourtProceedingBCheckbox;
  // C
  private boolean immigrationCourtProceedingCCheckbox;

  // 46
  private String whenLastLeaveCountry;
  private String i94Number;
  private List<EntryRecord> entryRecords;
  private String statusExpireDate;
  private String passportIssueCountry;
  private String passportNumber;
  private String travelDocumentNumber;
  private String nativeLanguage;
  private String expirationDate;
  private boolean fluentEnglishYesCheckbox;
  private boolean fluentEnglishNoCheckbox;

  private String otherFluentLanguages;
  private boolean haveNoChildrenCheckbox;
  private boolean haveChildrenCheckbox;
  private String childrenCnt;
  private boolean notMarriedCheckbox;
}
