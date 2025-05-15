package com.quick.immi.ai.entity.employmentBased.i829;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChildInformation {

    // Child Information
    private String childFamilyName;
    private String childGivenName;
    private String childMiddleName;
    private String childGenderMale; // "Male" or "Female"
    private String childGenderFemale;
    private String childAlienRegistrationNumber;
    private String childUscisOnlineAccountNumber;
    private String childDateOfBirth;

    // Other Names Used by Child
    private String childOtherNameFamilyName;
    private String childOtherNameGivenName;
    private String childOtherNameMiddleName;

    // Child Mailing Address
    private String childStreetNumberAndName;
    private String childApartment;
    private String childSuite;
    private String childFloor;
    private String childApartmentSuiteFloor;
    private String childCityOrTown;
    private String childState;
    private String childZipCode;
    private String childProvince;
    private String childPostalCode;
    private String childCountry;

    // Additional Information for Child 1
    private String childLivingWithYouYes; // "Yes" or "No"
    private String childLivingWithYouNo;
    private String childApplyingWithYouYes; // "Yes" or "No"
    private String childApplyingWithYouNo; 
    private String childCurrentImmigrationStatus;
}