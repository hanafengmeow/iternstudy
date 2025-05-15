package com.quick.immi.ai.entity.employmentBased.i829;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInformation {

    /**
     * Petitioner's last name.
     */
    private String familyName;

    /**
     * Petitioner's first name.
     */
    private String givenName;

    /**
     * Petitioner's middle name.
     */
    private String middleName;

    /**
     * Alien Registration Number (A-Number), if any.
     */
    private String alienRegistrationNumber;

    /**
     * USCIS Online Account Number, if any.
     */
    private String uscisOnlineAccountNumber;

    /**
     * U.S. Social Security Number, if any.
     */
    private String socialSecurityNumber;

    /**
     * Date of Birth in mm/dd/yyyy format.
     */
    private String dateOfBirth;

    /**
     * Petitioner's gender ("Male" or "Female").
     */
    private String genderMale;
    private String genderFemale;

    /**
     * Country of Birth.
     */
    private String countryOfBirth;

    /**
     * Country of Citizenship or Nationality.
     */
    private String countryOfCitizenship;

    /**
     * Date of Admission as a Conditional Permanent Resident (mm/dd/yyyy).
     */
    private String dateOfAdmission;

    /**
     * Form I-526 Receipt Number on which this petition is based.
     */
    private String i526ReceiptNumber;

    /**
     * Any additional Form I-526 or Form I-829 Receipt Numbers for other petitions filed by the investor.
     */
    private String additionalReceiptNumbers;

    // Other names used
    private String otherName1FamilyName;
    private String otherName1GivenName;
    private String otherName1MiddleName;
    private String otherName2FamilyName;
    private String otherName2GivenName;
    private String otherName2MiddleName;

    // Mailing address
    private String inCareOfName;
    private String streetNumberAndName;
    private String apartment;
    private String suite;
    private String floor;
    private String apartmentSuiteFloor;
    private String cityOrTown;
    private String state;
    private String zipCode;
    private String isMailingAddressSameAsPhysicalAddressYes;
    private String isMailingAddressSameAsPhysicalAddressNo;

    // Physical address
    private String physicalStreetNumberAndName;
    private String physicalApartment;
    private String physicalSuite;
    private String physicalFloor;
    private String physicalApartmentSuiteFloor;
    private String physicalCityOrTown;
    private String physicalState;
    private String physicalZipCode;
    private String physicalProvince;
    private String physicalPostalCode;
    private String physicalCountry;

    // Criminal history
    private String everArrestedYes;
    private String everArrestedNo;
    private String everCommittedCrimeYes;
    private String everCommittedCrimeNo;
}