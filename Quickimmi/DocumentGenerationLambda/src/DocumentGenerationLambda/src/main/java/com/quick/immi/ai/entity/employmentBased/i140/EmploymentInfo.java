package com.quick.immi.ai.entity.employmentBased.i140;

import com.quick.immi.ai.entity.familyBased.i864.MailAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentInfo {

  private String jobTitle;                     // 1. Job Title - field number 233
  private String socCode1;                      // 2. SOC Code - field number 231
  private String socCode2;                      // 2. SOC Code - field number 232
  private String nonTechnicalJobDescription;   // 3. Nontechnical Job Description - field number 234

  private String fullTimePositionCheckboxYes;  // 4. Is this a full-time position? (Yes) - field number 237
  private String fullTimePositionCheckboxNo;   // 4. Is this a full-time position? (No) - field number 236

  private String hoursPerWeek;                 // 5. Hours per week - field number 235 (only if answer to 4 is No)

  private String permanentPositionCheckboxYes; // 6. Is this a permanent position? (Yes) - field number 241
  private String permanentPositionCheckboxNo;  // 6. Is this a permanent position? (No) - field number 240

  private String newPositionCheckboxYes;       // 7. Is this a new position? (Yes) - field number 239
  private String newPositionCheckboxNo;        // 7. Is this a new position? (No) - field number 238

  private String wages;                        // 8. Wages (amount) - field number 250
  private String wageUnit;                     // 8. Wages (per hour, week, month, or year) - field number 251

  private MailAddress workAddress;

}
