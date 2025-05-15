package com.quick.immi.ai.entity.i131;

import lombok.Data;


@Data
public class I131Table {
    // P0 - Attorney or Representative Information
    private Attorney attorney;
    // P1 - Information About You
    private Applicant applicant;
    // P2 - Application Type or Filing Category
    private ApplicationType applicationType;
    // Part 3 - Processing Information
    private ProcessingInfo processingInfo;
    // Part 4 - Information About Your Proposed Travel
    private Travel travel;
    // Part 5 - Complete Only If Applying for a Re-entry Permit
    private ApplyReentryPermit applyReentryPermit;
    // Part 6 - Complete Only If Applying for a Refugee Travel Document
    private ApplyRefugeeTravel applyRefugeeTravel;
    // Part 7 - Complete Only If Applying for Advance Parole
    private ApplyAdvanceParole applyAdvanceParole;
    // Part 8 - Employment Authorization For New Period of Parole Under Operation Allies Welcome
    private EmploymentAuthorization employmentAuthorization;
    // Part 9 - Signature
    private Signature signature;
    // P11 - Information About Person Who Prepared This Application
    private Preparer preparer;

    @Data
    public static class Attorney {
        // P0 - Q1(126)
        private String g28AttachedCheckbox;
        // P0 - Q2(125)
        private String attorneyStateLicenseNumber;
    }

    @Data
    public static class Applicant {
        // P1 - Q1a(106)
        private String firstName;
        // P1 - Q1b(107)
        private String lastName;
        // P1 - Q1c(114)
        private String middleName;
        // P1 - Q2 - Address
        private Address physicalAddress;
        // P1 - Q3(105)
        private String alienNumber;
        // P1 - Q4(127)
        private String countryOfBirth;
        // P1 - Q5(128)
        private String countryOfCitizenship;
        // P1 - Q6(112)
        private String classOfAdmission;
        // P1 - Q7(110,111)
        private String maleCheckbox;
        private String femaleCheckbox;
        // P1 - Q8(120)
        private String dateOfBirth;
        // P1 - Q9(109)
        private String ssn;
    }

    @Data
    public static class ApplicationType {
        // P2 - Q1a
        private String permanentResidentCheckbox;
        // P2 - Q1b
        private String refugeeOrAsyleeCheckbox;
        // P2 - Q1c
        private String directResultRefugeeOrAsyleeCheckbox;
        // P2 - Q1d
        private String advanceParoleReentryCheckbox;
        // P2 - Q1e
        private String advanceParoleOutsideUSCheckbox;
        // P2 - Q1f
        private String advanceParoleForAnotherCheckbox;
        // P2 - Q2a
        private String lastName;
        // P2 - Q2b
        private String firstName;
        // P2 - Q2c
        private String middleName;
        // P2 - Q2d
        private String dateOfBirth;
        // P2 - Q2e
        private String countryOfBirth;
        // P2 - Q2f
        private String countryOfCitizenship;
        // P2 - Q2g
        private PhoneNumber daytimePhoneNumber;
        // P2 - Address
        private Address physicalAddress;
    }

    @Data
    public static class ProcessingInfo {
        // P3 - Q1(147)
        private String intendedDepartureDate;
        // P3 - Q2(138)
        private String expectedLengthOfTrip;
        // P3 - Q3a(159,160)
        private String inExclusionProceedingsYesCheckbox;
        private String inExclusionProceedingsNoCheckbox;
        // P3 - Q3b(139)
        private String dhsOfficeName;
        // P3 - Q4a(144,145)
        private String previousReentryPermitYesCheckbox;
        private String previousReentryPermitNoCheckbox;
        // P3 - Q4b(148)
        private String previousReentryPermitIssueDate;
        // P3 - Q4c(142)
        private String previousReentryPermitDisposition;
        // P3 - Q5(183)
        private String sendDocumentToUSAddressCheckbox;
        // P3 - Q6(182)
        private String sendDocumentToEmbassyAddressCheckbox;
        // P3 - Q6a(184)
        private String usEmbassyCity;
        // P3 - Q6b(197)
        private String usEmbassyCountry;
        // P3 - Q7(167)
        private String sendDocumentToDHSOfficeCheckbox;
        // P3 - Q7a(176)
        private String dhsOfficeCity;
        // P3 - Q7b(196)
        private String dhsOfficeCountry;
        // P3 - Q8(168)
        private String sendDocumentToPart2AddressCheckbox;
        // P3 - Q9(169)
        private String sendDocumentToPart3AddressCheckbox;
        // P3 - Q10a(177)
        private Address address;
        // P3 - Q10j(200,198,199)
        private PhoneNumber daytimePhoneNumber;
    }

    @Data
    public static class Travel {
        // P4 - Q1a(178)
        private String travelPurpose;
        // P4 - Q1b(179)
        private String listTravelCountries;
    }

    @Data
    public static class ApplyReentryPermit {
        // P5 - Q1a(170)
        private String timeOutsideUSLessThan6MonthsCheckbox;
        // P5 - Q1b(171)
        private String timeOutsideUS6MonthsTo1YearCheckbox;
        // P5 - Q1c(172)
        private String timeOutsideUS1To2YearsCheckbox;
        // P5 - Q1d(173)
        private String timeOutsideUS2To3YearsCheckbox;
        // P5 - Q1e(174)
        private String timeOutsideUS3To4YearsCheckbox;
        // P5 - Q1f(175)
        private String timeOutsideUSMoreThan4YearsCheckbox;
        // P5 - Q2(180)
        private String filedTaxAsNonResidentYesCheckbox;
        // P5 - Q2(181)
        private String filedTaxAsNonResidentNoCheckbox;
    }

    @Data
    public static class ApplyRefugeeTravel {
        // P6 - Q1(231)
        private String refugeeAsyleeCountry;
        // P6 - Q2(219,216)
        private String planToTravelToRefugeeCountryYesCheckbox;
        private String planToTravelToRefugeeCountryNoCheckbox;
        // P6 - Q3a(217,218)
        private String returnedToRefugeeCountryYesCheckbox;
        private String returnedToRefugeeCountryNoCheckbox;
        // P6 - Q3b(212,201)
        private String appliedForPassportFromRefugeeCountryYesCheckbox;
        private String appliedForPassportFromRefugeeCountryNoCheckbox;
        // P6 - Q3c(202,210)
        private String receivedBenefitsFromRefugeeCountryYesCheckbox;
        private String receivedBenefitsFromRefugeeCountryNoCheckbox;
        // P6 - Q4a(209,204)
        private String reacquiredNationalityYesCheckbox;
        private String reacquiredNationalityNoCheckbox;
        // P6 - Q4b(205,208)
        private String acquiredNewNationalityYesCheckbox;
        private String acquiredNewNationalityNoCheckbox;
        // P6 - Q4c(207,206)
        private String grantedRefugeeStatusOtherCountryYesCheckbox;
        private String grantedRefugeeStatusOtherCountryNoCheckbox;
    }
    

    @Data
    public static class ApplyAdvanceParole {
        // P7 - Q1(203,211)
        private String oneTripCheckbox;

        private String moreThanOneTripCheckbox;
        
        // P7 - Q2a(215)
        private String destinationCity;
        // P7 - Q2b(232)
        private String destinationCountry;
        // P7 - Q3(213)
        private String deliverToAddressPart2Checkbox;
        // P7 - Q4(214)
        private String deliverToAddressPart7Checkbox;
        // P7 - Q4a(226)
        private Address address;
        // P7 - Q4j(236,237,238)
        private PhoneNumber daytimePhoneNumber;
    }

    @Data
    public static class EmploymentAuthorization {
        // P8 - Q1(235)
        private String requestEADForOAWYesCheckbox;
        // P8 - Q1(234)
        private String requestEADForOAWNoCheckbox;
    }

    @Data
    public static class Signature {
        // P9 - Q1a(244)
        private String signatureOfApplicant;
        // P9 - Q1b(245)
        private String dateOfSignature;
        // P9 - Q2(259,260,261)
        private PhoneNumber daytimePhoneNumber;
    }

    @Data
    public static class Preparer {
        // P10 - Q1a(239,240)
        private String lastName;
        private String firstName;
        // P10 - Q2(241)
        private String businessOrOrganizationName;
        // P10 - Q3
        private Address mailingAddress;
        // P10 - Q4(258)
        private String daytimePhoneNumberExtension;
        // P10 - Q4(263,262,264)
        private PhoneNumber daytimePhoneNumber;
        // P10 - Q5(242)
        private String emailAddress;
        // P10 - Q6a(243)
        private String signatureOfPreparer;
        // P10 - Q6b(246)
        private String dateOfSignature;
    }

    @Data
    public static class Address {
        private String inCareOf;
        private String streetNumberAndName;
        private String aptCheckbox;
        private String steCheckbox;
        private String flrCheckbox;
        private String aptSteFlrNumber;
        private String cityOrTown;
        private String state;
        private String zipCode;
        private String province;
        private String postalCode;
        private String country;
    }

    @Data
    public static class PhoneNumber {
        private String number1to3;
        private String number4to6;
        private String number7to10;
    }
}

