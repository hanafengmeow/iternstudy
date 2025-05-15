/* (C) 2024 */
package com.quick.immi.ai.entity.document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Passport {
  private String idNumber;
  private String firstName;
  private String middleName;
  private String lastName;
  private String countryCode;
  // mm/dd/yyyy
  private String birthDate;
  private String birthPlace;
  private String nationality;
  private String gender;
  private String expireDate;
}
