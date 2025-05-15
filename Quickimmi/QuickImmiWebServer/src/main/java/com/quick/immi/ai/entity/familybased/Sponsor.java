/* (C) 2024 */
package com.quick.immi.ai.entity.familybased;

import java.util.List;
import lombok.Data;

@Data
public class Sponsor {
  private BasicInfo basicInfo;
  private SponsorDetails sponsorDetails;
  private HouseholdSize householdSize;
  // Part 6: Sponsor's Employment and Income
  private EmploymentAndIncome employmentAndIncome;
  // Part 7: Use of Assets to Supplement Income (Optional)
  private AssetsSupplementIncome assetsSupplementIncome;
  // Part 8: Sponsor's Contract, Declaration, Certification, and Signature
  private SponsorContract sponsorContract;

  @Data
  public static class SponsorContract {

    // Sponsor's Statement
    // 283 1.a - I can read and understand English (Checkbox)
    private boolean canReadUnderstandEnglishCheckbox;

    // 290 1.b - The interpreter named in Part 9 read every question and my answer (Checkbox)
    private boolean interpreterHelpedCheckbox;

    // 284 - Language in which I am fluent
    private String fluentLanguage;

    // Preparer information
    // 285 2 - At my request, the preparer named in Part 10 prepared this affidavit (Checkbox)
    private boolean preparerCheckbox;

    // 286 - Information provided by sponsor for affidavit
    private String preparerInformationProvided;

    // Sponsor's Contact Information
    // 287 - Sponsor's Daytime Telephone Number
    // 288 - Sponsor's Mobile Telephone Number (if any)
    // 289 - Sponsor's Email Address (if any)
    private String daytimeTelephoneNumber;

    // Mobile Telephone Number (if any)
    private String mobileTelephoneNumber;

    //  Email Address (if any)
    private String emailAddress;

    // Sponsor's Signature
    // 306 - Sponsor's Signature
    private String sponsorSignature;

    // 305 - Date of Signature (mm/dd/yyyy)
    private String dateOfSignature;
  }

  @Data
  public static class EmploymentAndIncome {
    // 1 - Employed as a/an (Checkbox)
    private boolean employedAsCheckbox;
    // 236 - Employed as a/an (Occupation)
    private String employedOccupation;

    // 2 - Name of Employer 1
    // 235 - Name of Employer 1
    private String employer1Name;

    // 3 - Name of Employer 2 (if applicable)
    // 237 - Name of Employer 2
    private String employer2Name;

    // 4 - Self-Employed as a/an (Checkbox)
    private boolean selfEmployedCheckbox;

    // 238 - Self-employed occupation
    private String selfEmployedOccupation;

    // 5 - Retired Since (mm/dd/yyyy) (Checkbox)
    private boolean retiredCheckbox;
    private String retiredSince;

    // 6 - Unemployed Since (mm/dd/yyyy) (Checkbox)
    private boolean unemployedCheckbox;
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
    private boolean peopleCompletedFormI864ACheckbox;

    // 259 22. One or more people listed in Item Numbers 8, 11, 14, and 17 are the intending
    // immigrant (Checkbox)
    private boolean intendingImmigrantCheckbox;
    // 258 - Name of person listed
    private String intendingImmigrantName;

    // Federal Income Tax Return Information
    // 23.a - Have you filed a federal income tax return for each of the three most recent tax
    // years? (Yes/No Checkbox)
    private boolean filedFederalTaxReturnYesCheckbox;
    private boolean filedFederalTaxReturnNoCheckbox;

    // 260 23.b - Attached photocopies or transcripts of tax returns for second and third most
    // recent years (Checkbox)
    private boolean attachedTaxReturnsCheckbox;

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
    private boolean notRequiredToFileTaxReturnCheckbox;
  }

  @Data
  public static class IncomeFromOtherHouseholdMember {

    private String name;

    // 242 - Relationship
    private String relationship;

    // 243 - Current Income
    private String currentIncome;
  }

  @Data
  public static class AssetsSupplementIncome {
    // Your Assets (Optional)
    // 270 - Enter the balance of all savings and checking accounts
    private String balanceSavingsCheckingAccounts;

    // 271 - Enter the net cash value of real-estate holdings (Net value = assessed value - mortgage
    // debt)
    private String netRealEstateHoldings;

    // 275 - Enter the net cash value of all stocks, bonds, certificates of deposit, and other
    // assets
    private String netStocksBondsAssets;

    // 272 - Add together Item Numbers 1 - 3 and enter the total here
    private String totalAssets;

    // Assets from Form I-864A, Part 4, Item Number 3.d, for:
    // 273 - Name of Relative
    private String nameOfRelative;

    // 274 - Your household member's assets from Form I-864A (optional)
    private String householdMemberAssets;

    // Assets of the principal sponsored immigrant (optional)
    // 276 - Balance of the principal immigrant's savings and checking accounts
    private String principalImmigrantSavingsCheckingBalance;

    // 279 - Net cash value of all the principal immigrant's real estate holdings
    private String principalImmigrantRealEstateHoldings;

    // 280 - Current cash value of the principal immigrant's stocks, bonds, and other assets
    private String principalImmigrantStocksBondsAssets;

    // 281 - Add together Item Numbers 6 - 8 and enter the number here
    private String totalPrincipalImmigrantAssets;

    // Total Value of Assets
    // 282 - Add together Item Numbers 4, 5.b, and 9 and enter the total here
    private String totalValueOfAssets;
  }

  @Data
  public static class BasicInfo {
    private boolean isPetitionerCheckbox;
    private boolean filedAlienWorkerPetitionCheckbox;
    private String relationshipToImmigrant;
    private boolean ownershipInterest5PercentCheckbox;
    private String ownershipInterestDescription;
    private String ownershipRelationshipToImmigrant;
    private boolean onlyJointSponsorCheckbox;
    private boolean notOnlyJointSponsorCheckbox;
    private boolean firstJointSponsorCheckbox;
    private boolean secondJointSponsorCheckbox;
    private boolean substituteSponsorCheckbox;
    private String substituteSponsorRelationship;
  }

  @Data
  public static class SponsorDetails {
    // Sponsor's Full Name
    // 187 - Family Name (Last Name)
    private String lastName;

    // 186 - Given Name (First Name)
    private String firstName;

    // 185 - Middle Name
    private String middleName;

    // Sponsor's Mailing Address
    // 199 - In Care Of Name
    private String inCareOfName;

    // 189 - Street Number and Name
    private Address mailAddress;

    // 3 - Is your current mailing address the same as your physical address? (Checkbox)
    private boolean mailingAddressSameAsPhysicalYesCheckbox;
    private boolean mailingAddressSameAsPhysicalNoCheckbox;

    // Sponsor's Physical Address (if different)
    // 203 - 208
    private Address physicalAddress;

    // Other Information
    // 214 - Country of Domicile
    private String countryOfDomicile;

    // 213 - Date of Birth (mm/dd/yyyy)
    private String dateOfBirth;

    // 215 - City or Town of Birth
    private String cityOfBirth;

    // 216 - State or Province of Birth
    private String stateOrProvinceOfBirth;

    // 217 - Country of Birth
    private String countryOfBirth;

    // 225 - U.S. Social Security Number (Required)
    private String ssn;

    // Citizenship or Residency
    // 11.a - I am a U.S. citizen (Checkbox)
    private boolean isUsCitizenCheckbox;

    // 11.b - I am a U.S. national (Checkbox)
    private boolean isUsNationalCheckbox;

    // 11.c - I am a lawful permanent resident (Checkbox)
    private boolean isLawfulPermanentResidentCheckbox;

    // 12 - Sponsor's A-Number (if any)
    private String sponsorANumber;

    // 13 - Sponsor's USCIS Online Account Number (if any)
    private String uSCISOnlineAccountNumber;

    // 14 - Military Service (To be completed by petitioner sponsors only)
    private boolean militaryServiceActiveDutyYesCheckbox;
    private boolean militaryServiceActiveDutyNoCheckbox;
  }
}
