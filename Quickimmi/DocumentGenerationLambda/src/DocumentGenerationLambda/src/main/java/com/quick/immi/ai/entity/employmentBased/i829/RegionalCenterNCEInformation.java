package com.quick.immi.ai.entity.employmentBased.i829;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionalCenterNCEInformation {

    /**
     * Receipt Number for the Approved Form I-924.
     */
    private String formI924ReceiptNumber;

    /**
     * Indicates if the Regional Center was associated with the investor and if it was terminated.
     * Expected values: "Yes" or "No"
     */
    private String regionalCenterTerminatedYes;
    private String regionalCenterTerminatedNo;

    // Physical Address of the NCE
    private String nceStreetNumberAndName;
    private String nceApartment;
    private String nceSuite;
    private String nceFloor;
    private String nceApartmentSuiteFloor;
    private String nceCityOrTown;
    private String nceState;
    private String nceZipCode;

    /**
     * Telephone Number of the NCE.
     */
    private String nceTelephoneNumber;

    /**
     * Internet Web Site Address of the NCE, if established.
     */
    private String nceWebsiteAddress;

    /**
     * Included Industries (select North American Industry Classification System (NAICS) code or codes).
     */
    private String nceIncludedIndustries;

    /**
     * IRS Tax Identification Number of the NCE.
     */
    private String nceTaxIdentificationNumber;

    /**
     * Date Business Established (mm/dd/yyyy).
     */
    private String nceDateBusinessEstablished;

    /**
     * Date of the Investor's Initial Investment (mm/dd/yyyy).
     */
    private String dateOfInitialInvestment;

    /**
     * Amount of the Investor's Initial Investment.
     */
    private String initialInvestmentAmount;

    // Subsequent Investments in the NCE
    private String dateOfSubsequentInvestment;
    private String amountOfSubsequentInvestment;
    private String typeOfSubsequentInvestment;

    /**
     * Amount of Capital Investment Sustained in the NCE.
     */
    private String capitalInvestmentSustained;

    /**
     * Indicates if there have been changes in assets of the NCE.
     * Expected values: "Yes" or "No"
     */
    private String changesInNCEAssetsYes;
    private String changesInNCEAssetsNo;

    /**
     * Total amount of capital invested by EB-5 investors into the NCE.
     */
    private String totalCapitalInvestedByEB5;

    /**
     * Number of EB-5 investors associated with the NCE.
     */
    private String numberOfEB5Investors;

    /**
     * Indicates if the NCE has filed for bankruptcy, ceased business operations, or had any criminal or civil proceedings filed against it.
     * Expected values: "Yes" or "No"
     */
    private String nceFiledForBankruptcyYes;
    private String nceFiledForBankruptcyNo;
}