/* (C) 2024 */
package com.quick.immi.ai.entity.document;

import lombok.Data;

@Data
public class IDCard {
  private String id;
  private String firstName;
  private String middleName;
  private String lastName;
  private String dateBirth;
  private String birthPlace;
  private String gender;
  private String expireDate;
}
