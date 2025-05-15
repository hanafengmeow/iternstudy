package com.quick.immi.ai.entity.employmentBased.i829;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpouseInformation {

    /**
     * Spouse's last name.
     */
    private String spouseFamilyName;

    /**
     * Spouse's first name.
     */
    private String spouseGivenName;

    /**
     * Spouse's middle name.
     */
    private String spouseMiddleName;

    /**
     * Spouse's gender ("Male" or "Female").
     */
    private String spouseGenderFemale;
    private String spouseGenderMale;

    /**
     * Spouse's Alien Registration Number (A-Number), if any.
     */
    private String spouseAlienRegistrationNumber;

    /**
     * Spouse's USCIS Online Account Number, if any.
     */
    private String spouseUscisOnlineAccountNumber;

    /**
     * Spouse's Date of Birth in mm/dd/yyyy format.
     */
    private String spouseDateOfBirth;

    // Other names used by the spouse
    private String otherName1FamilyName;
    private String otherName1GivenName;
    private String otherName1MiddleName;
    private String otherName2FamilyName;
    private String otherName2GivenName;
    private String otherName2MiddleName;

    // Spouse's physical address
    private String spouseStreetNumberAndName;
    private String spouseApartment;
    private String spouseSuite;
    private String spouseFloor;
    private String spouseApartmentSuiteFloor;
    private String spouseCityOrTown;
    private String spouseState;
    private String spouseZipCode;
    private String spouseProvince;
    private String spousePostalCode;
    private String spouseCountry;

    // Other information about the spouse
    private String currentSpouse; // "Current Spouse" or "Former Conditional Permanent Resident Spouse"
    private String formerSpouse;
    private String dateOfMarriage; // mm/dd/yyyy format
    private String dateMarriageTerminated; // mm/dd/yyyy format
    private String isSpouseLivingWithYouYes; // "Yes" or "No"
    private String isSpouseLivingWithYouNo;
    private String isSpouseApplyingWithYouYes; // "Yes" or "No"
    private String isSpouseApplyingWithYouNo;
    private String spouseCurrentImmigrationStatus;
    private String isSpouseStatusBasedOnYourStatusYes; // "Yes" or "No"
    private String isSpouseStatusBasedOnYourStatusNo;
}
