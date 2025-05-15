package com.quick.immi.ai.entity.employmentBased.i140;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalPetitionerInfo {

  private String petitionerTypeEmployerCheckbox;  // 1.a. Checkbox for Employer ("Y" for Yes)
  private String petitionerTypeSelfCheckbox;      // 1.b. Checkbox for Self ("Y" for Yes)
  private String petitionerTypeOtherCheckbox;     // 1.c. Checkbox for Other ("Y" for Yes)
  private String petitionerTypeOther;             // 218
  private String typeOfBusiness;                  // 2. Type of Business - field number 219
  private String dateEstablished;                 // 3. Date Established (mm/dd/yyyy) - field number 220
  private String numberOfUSEmployees;             // 4. Current Number of U.S. Employees - field number 221
  private String grossAnnualIncome;               // 5. Gross Annual Income ($) - field number 222
  private String netAnnualIncome;                 // 6. Net Annual Income ($) - field number 223
  private String naicsCode;                       // 7. NAICS Code - field number 227
  private String laborCertificationDOLCaseNumber; // 8. Labor Certification DOL Case Number - field number 226
  private String laborCertificationFilingDate;    // 9. Labor Certification DOL Filing Date (mm/dd/yyyy) - field number 252
  private String laborCertificationExpirationDate;// 10. Labor Certification Expiration Date (mm/dd/yyyy) - field number 228
  private String occupation;                      // 11. Occupation - field number 230
  private String annualIncome;                    // 12. Annual Income ($) - field number 229
}
