/* (C) 2024 */
package com.quick.immi.ai.entity.familybased;

import lombok.Data;

@Data
public class Parent {
  // Fields for basic identity and birth names
  private String lastName; // Mother: P6Q1a, Father: P7Q9a
  private String firstName; // Mother: P6Q1b, Father: P7Q9b
  private String middleName; // Mother: P6Q1c, Father: P7Q9c

  private String birthLastName; // Mother: P6Q2a, Father: P7Q10a
  private String birthFirstName; // Mother: P6Q2b, Father: P7Q10b
  private String birthMiddleName; // Mother: P6Q2c, Father: P7Q10c

  // Date and place of birth
  private String dateOfBirth; // Mother: P6Q3, Father: P7Q11
  private String cityOfBirth; // Mother: P6Q5, Father: P7Q13
  private String countryOfBirth; // Mother: P6Q6, Father: P7Q14

  // Current place of residence
  private String currentCityOfResidence; // Mother: P7Q7, Father: P7Q15
  private String currentCountryOfResidence; // Mother: P7Q8, Father: P7Q16

  // Sex/Gender indicators
  private boolean femaleCheckbox; // Mother: P6Q4, Father: P7Q12
  private boolean maleCheckbox; // Mother: P6Q4, Father: P7Q12
}
