/* (C) 2024 */
package com.quick.immi.ai.entity.asylum;

import java.util.List;
import lombok.Data;

@Data
public class Child {
  private Long passportDocumentId;

  private List<Long> passportStampPageDocumentIds;

  // 122
  private String alienNumber;
  // 123
  private String passportNumber;
  // 124
  private String martialStatus;
  // 125
  private String ssn;
  // 109
  private String lastName;
  // 111
  private String firstName;
  // 112
  private String middleName;
  // 113
  private String birthDate;
  // 110
  private String cityAndCountryOfBirth;
  // 114
  private String nationality;
  // 115
  private String race;
  // 1
  private boolean genderMaleCheckbox;
  // 2
  private boolean genderFemaleCheckbox;

  private boolean personInUSYesCheckbox;
  private boolean personInUSNoCheckbox;
  // 132
  private String specifyLocationIfNotInUS;
  // 116
  private String placeLastEntryIntoUS;

  // 117
  private String lastEntryUSDate;
  // 118
  private String i94Number;

  // 119
  private String lastAdmittedStatus;
  // 120
  private String currentStatus;

  // 121
  private String statusExpireDate;

  // Yes
  private boolean immigrationCourtProceedingYesCheckbox;
  // No
  private boolean immigrationCourtProceedingNoCheckbox;
  // Y
  private boolean inThisApplicationYesCheckbox;
  // N
  private boolean inThisApplicationNoCheckbox;
}
