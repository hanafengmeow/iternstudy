/* (C) 2024 */
package com.quick.immi.ai.entity.document;

import lombok.Data;

@Data
public class MarriageCertificate {
  private String licenseHolder;
  private String registrationDate;
  private String licenseNumber;

  private String holderName;
  private String gender;
  private String nationality;
  private String birthDate;
  private String idNumber;

  private String spouseName;
  private String spouseGender;
  private String spouseNationality;
  private String spouseBirthDate;
  private String spouseIdNumber;
  private String seal;
}
