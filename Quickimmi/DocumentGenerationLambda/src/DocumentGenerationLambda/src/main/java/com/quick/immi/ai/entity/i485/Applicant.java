package com.quick.immi.ai.entity.i485;

import java.util.List;
import lombok.Data;

@Data
public class Applicant {
    // Personal Information
    private String familyName;
    private String givenName;
    private String middleName;
//    private List<OtherName> otherNames;

    private String otherFamilyName1;
    private String otherGivenName1;
    private String otherMiddleName1;
    private String otherFamilyName2;
    private String otherGivenName2;
    private String otherMiddleName2;
    private String dateOfBirth;
    private String otherdateOfBirthYes;
    private String otherdateOfBirthNo;
    private String otherdateOfBirth1;
    private String otherdateOfBirth2;
    private String alienNumberYes;
    private String alienNumberNo;
    private String alienNumber;
    private String otherAlienNumberYes;
    private String otherAlienNumberNo;
    private String otherAlienNumber1;
    private String otherAlienNumber2;
    private String male;
    private String female;
    private String otherGender;
    private String placeOfBirthCity;
    private String placeOfBirthCountry;
    private String countryOfCitizenship;
    private String uscicAccountNumber;

    // Immigration Details
    private String passportNumber;
    private String passportExpirationDate;
    private String passportIssuingCountry;
    private String visaNumber;
    private String visaIssueDate;
    private String lastArrivalCity;
    private String lastArrivalState;
    private String lastArrivalDate;
    private String portOfEntryAdmissionCheckbox;
    private String portOfEntryAdmission; // Inspected at Port of Entry and admitted as
    private String portOfEntryParoleCheckbox;
    private String portOfEntryParole;    // Inspected at Port of Entry and paroled as
    private String noAdmissionParoleCheckbox;
    private String otherDetailsCheckbox;
    private String otherDetails;         // Other (custom details)

    private String i94FamilyName;                    // Family Name (Last Name)
    private String i94GivenName;                     // Given Name (First Name)
    private String i94Number;                        // Form I-94 Arrival/Departure Record Number
    private String i94ExpirationDate;                // Expiration Date of Authorized Stay (mm/dd/yyyy)
    private String immigrationStatusOnI94;           // Immigration Status on Form I-94
    private String lastArrivalFirstTimeYes;          // Was last arrival the first time in the U.S.? (Yes/No)
    private String lastArrivalFirstTimeNo; 
    private String currentImmigrationStatus;
    private String expirationDateCurrentStatus;
    private String issuedAlienCrewmanVisaYes;
    private String issuedAlienCrewmanVisaNo;
    private String arrivalToJoinVesselOrAircraftYes;
    private String arrivalToJoinVesselOrAircraftNo;
    
    private Address physicalAddress;
    private String isMailingAddressYes;
    private String isMailingAddressNo;
    private Address mailingAddress;
    private String hasResidedFiveYearsYes;
    private String hasResidedFiveYearsNo;
//    private Address priorAddress;
    private List<Address> addressHistories;

    private Address mostRecentOutSideUsAddressBefore5YearsAgo;
    
    // Social Security Details
    private String hasSSNYes;
    private String hasSSNNo;
    private String ssn;
    private String requestSSNYes;
    private String requestSSNNo;
    private String disclosureYes;
    private String disclosureNo;
}