package com.quick.immi.ai.entity.familyBased.i864;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentAndIncome {
  // 1 - Employed as a/an (Checkbox)
  private String employedAsCheckbox;

  // 236 - Employed as a/an (Occupation)
  private String employedOccupation;

  // 2 - Name of Employer 1
  // 235 - Name of Employer 1
  private String employer1Name;

  // 3 - Name of Employer 2 (if applicable)
  // 237 - Name of Employer 2
  private String employer2Name;

  // 4 - Self-Employed as a/an (Checkbox)
  private String selfEmployedCheckbox;

  // 238 - Self-employed occupation
  private String selfEmployedOccupation;

  // 5 - Retired Since (mm/dd/yyyy) (Checkbox)
  private String retiredCheckbox;
  private String retiredSince;

  // 6 - Unemployed Since (mm/dd/yyyy) (Checkbox)
  private String unemployedCheckbox;
  private String unemployedSince;

  // 7 - My current individual annual income
  // 240 - Current annual income
  private String individualAnnualIncome;

  // Income from other household members
  // 239 - 240
  private List<IncomeFromOtherHouseholdMember> incomeFromOtherHouseholdMember;

  // 20. My Current Annual Household Income
  // 277 - Total annual household income
  private String totalAnnualHouseholdIncome;

  // 21. People listed in Item Numbers 8, 11, 14, and 17 have completed Form I-864A (Checkbox)
  private String peopleCompletedFormI864ACheckbox;

  // 259 22. One or more people listed in Item Numbers 8, 11, 14, and 17 are the intending immigrant (Checkbox)
  private String intendingImmigrantCheckbox;
  // 258 - Name of person listed
  private String intendingImmigrantName;

  // Federal Income Tax Return Information
  // 23.a - Have you filed a federal income tax return for each of the three most recent tax years? (Yes/No Checkbox)
  private String filedFederalTaxReturnYesCheckbox;
  private String filedFederalTaxReturnNoCheckbox;

  // 260 23.b - Attached photocopies or transcripts of tax returns for second and third most recent years (Checkbox)
  private String attachedTaxReturnsCheckbox;

  // Tax Year Information
  // 24.a - Most Recent Tax Year
  // 263 - Most recent tax year
  private String mostRecentTaxYear;

  // 268 - Most recent tax year income
  private String mostRecentTaxYearIncome;

  // 24.b - 2nd Most Recent Tax Year
  // 267 - Second most recent tax year
  private String secondMostRecentTaxYear;

  // 266 - Second most recent tax year income
  private String secondMostRecentTaxYearIncome;

  // 24.c - 3rd Most Recent Tax Year
  // 264 - Third most recent tax year
  private String thirdMostRecentTaxYear;

  // 265 - Third most recent tax year income
  private String thirdMostRecentTaxYearIncome;

  // 25 - I was not required to file a Federal income tax return (Checkbox)
  private String notRequiredToFileTaxReturnCheckbox;
}

