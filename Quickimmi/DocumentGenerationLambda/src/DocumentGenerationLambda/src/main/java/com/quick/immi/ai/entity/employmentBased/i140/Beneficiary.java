package com.quick.immi.ai.entity.employmentBased.i140;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiary {

  // Part 3: Information about the person for whom you are filing
  private String lastName;  // 1.a. Field 143
  private String firstName;   // 1.b. Field 144
  private String middleName;  // 1.c. Field 145

  // Mailing Address - Reusing MailAddress class for 2.a to 2.i fields
  private MailAddress mailAddress;

  // Other Information
  private String dateOfBirth;        // 3. Field 158
  private String cityOfBirth;        // 4. Field 159
  private String stateOrProvinceOfBirth; // 5. Field 175

  private String countryOfBirth;          // 6. Field 160
  private String countryOfCitizenship;    // 7. Field 161
  private String alienRegistrationNumber; // 8. Field 162
  private String ssn;                     // 9. Field 163

  // Information about the last arrival in the US
  private String dateOfLastArrival;       // 10. Field 164
  private String formI94Number;           // 11.a Field 165
  private String expirationOfStay;        // 11.b Field 170
  private String statusOnFormI94;         // 11.c Field 171

  // Passport and travel document information
  private String passportNumber;          // 12. Field 166
  private String travelDocumentNumber;    // 13. Field 167
  private String countryOfIssuance;       // 14. Field 168
  private String expirationDateForPassport; // 15. Field 169
}
