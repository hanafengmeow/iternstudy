/* (C) 2024 */
package com.quick.immi.ai.entity.familybased;

import lombok.Data;

@Data
public class BeneficiaryFamily {
  private String pageNumber = "6";
  private String partNumber = "4";
  private String itemNumber = "25 - 28";

  private String lastName;
  private String firstName;
  private String middleName;
  private String relationship;
  private String dateOfBirth;
  private String countryOfBirth;
}
