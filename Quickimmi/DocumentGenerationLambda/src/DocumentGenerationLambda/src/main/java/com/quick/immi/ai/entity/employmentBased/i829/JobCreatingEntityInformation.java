package com.quick.immi.ai.entity.employmentBased.i829;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCreatingEntityInformation {

    // JCE 1 Information
    private String jce1Name;
    private String jce1StreetNumberAndName;
    private String jce1Apartment;
    private String jce1Suite;
    private String jce1Floor;
    private String jce1ApartmentSuiteFloor;
    private String jce1CityOrTown;
    private String jce1State;
    private String jce1ZipCode;

    // JCE 2 Information
    private String jce2Name;
    private String jce2StreetNumberAndName;
    private String jce2Apartment;
    private String jce2Suite;
    private String jce2Floor;
    private String jce2ApartmentSuiteFloor;
    private String jce2CityOrTown;
    private String jce2State;
    private String jce2ZipCode;

    // JCE 3 Information
    private String jce3Name;
    private String jce3StreetNumberAndName;
    private String jce3Apartment;
    private String jce3Suite;
    private String jce3Floor;
    private String jce3ApartmentSuiteFloor;
    private String jce3CityOrTown;
    private String jce3State;
    private String jce3ZipCode;

    /**
     * Indicates if any of the JCEs have filed for bankruptcy, ceased business operations,
     * or had criminal or civil proceedings filed against them.
     * Expected values: "Yes" or "No"
     */
    private String jceFiledForBankruptcyOrOtherIssuesYes;
    private String jceFiledForBankruptcyOrOtherIssuesNo;
}