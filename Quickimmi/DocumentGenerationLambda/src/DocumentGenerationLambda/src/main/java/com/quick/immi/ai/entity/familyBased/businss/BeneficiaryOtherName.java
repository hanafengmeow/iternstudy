/* (C) 2024 */
package com.quick.immi.ai.entity.familyBased.businss;

import lombok.Data;

@Data
public class BeneficiaryOtherName {
  private String pageNumber = "5";
  private String partNumber = "4";
  private String itemNumber = "5";

  // Family name for beneficiary's other name used
  private String lastName;
  // Given name for beneficiary's other name used
  private String firstName;
  // Middle name for beneficiary's other name used
  private String middleName;
}
