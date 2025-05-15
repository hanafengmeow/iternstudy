package com.quick.immi.ai.entity.familyBased.i130;

import java.util.List;

import lombok.Data;

@Data
public class Petitioner {
    //132
    private String alienNumber;
    //133
    private String uSCISOnlineAccountNumber;
    //112
    private String ssn;
    //129
    private String lastName;
    //130
    private String firstName;
    //131
    private String middleName;
    //178 - 180
    private List <PetitionerOtherName> petitionerOtherNames;
    
    //177
    private String cityOfBirth;
    //137
    private String countryOfBirth;
    //134
    private String dateOfBirth;

    //Part 2-9-M
    private String sexMaleCheckbox;
    //Part 2-9-F
    private String sexFemaleCheckbox;
    //151
    private String inCareOf;
    //140
    private String streetNumberAndName;
    //144
    private String aptCheckbox;
    private String steCheckbox;
    private String flrCheckbox;
    private String aptSteFlrNumber;
    //145
    private String cityOrTown;
    //149
    private String state;
    //148
    private String zipCode;
    //146
    private String province;
    //147
    private String postalCode; 
    //150
    private String country;
    //Part2-11 Yes
    private String mailingAddressSameAsPhysicalAddressYesCheckbox;
    //No
    private String mailingAddressSameAsPhysicalAddressNoCheckbox;

    private List<Address> addressHistory;

    //181
    private String timesMarried;
    //Part2-17
    private String currentMartialStatusSingleCheckbox;
    private String currentMartialStatusMarriedCheckbox;
    private String currentMartialStatusDivorcedCheckbox;
    private String currentMartialStatusWidowedCheckbox;
    private String currentMartialStatusSeparatedCheckbox;
    private String currentMartialStatusAnnulledCheckbox;
    //197
    private String dateOfCurrentMarriage;
    //226
    private String cityOfCurrentMarriage;
    //227
    private String stateOfCurrentMarriage;
    //228
    private String provinceOfCurrentMarriage;
    //229
    private String countryOfCurrentMarriage;

    private List<Spouses> spouses;
    private Parents parent1;
    private Parents parent2;

    //Part2-36
    private String dfUsCitizenCheckbox;
    private String dfLawfulPermanentResidentCheckbox;

    //Part2-37
    private String citizenshipAcquiredByBirthCheckbox;
    private String citizenshipAcquiredByNaturalizationCheckbox;
    private String citizenshipAcquiredThroughParentsCheckbox;

    //Part2-38
    private String obtainedNaturalizationCertificateYesCheckbox;
    private String obtainedNaturalizationCertificateNoCheckbox;

    //217
    private String certificateNumber;
    //221
    private String placeOfIssuance;
    //220
    private String dateOfIssuance;

    //230
    private String classOfAdmission;
    //231
    private String dateOfAdmission;
    //232
    private String placeOfAdmissionCity;
    //286
    private String placeOfAdmissionState;
    //Part2-41
    private String gainedStatusThroughMarriageYesCheckbox;
    private String gainedStatusThroughMarriageNoCheckbox;

    private List<EmploymentHistory> employmentHistory;
}