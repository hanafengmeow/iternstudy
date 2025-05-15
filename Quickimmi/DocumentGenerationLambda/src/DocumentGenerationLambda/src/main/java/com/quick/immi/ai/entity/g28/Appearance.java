package com.quick.immi.ai.entity.g28;

import lombok.Data;

@Data
public class Appearance {
    // form number - prerun number
    // 1a - checkbox uscs 默认check
    private String uscisCheckbox;
    // 1b - 42
    private String formNumbers;
    // 2a - checkbox ice
    private String iceCheckbox;
    // 2b - 38
    private String specificMatterICE;
    // 3a - checkbox cbp
    private String cbpCheckbox;
    // 3b - 40
    private String specificMatterCBP;
    // 4 - 65
    private String receiptNumber;
    // 5 - checkbox applicant
    private String applicantCheckbox;
    // 5 - checkbox petitioner
    private String petitionerCheckbox;
    // 5 - checkbox requestor
    private String requestorCheckbox;
    // 5 - checkbox beneficiaryandDerivative
    private String beneficiaryandDerivativeCheckbox;
    // 5 - checkbox Respondent
    private String RespondentCheckbox;
    // 6a - 46
    private String clientLastName;
    // 6b - 45
    private String clientFirstName;
    // 6c - 44
    private String clientMiddleName;
    // 7a - 47
    private String clientNameofEntity;
    // 7b - 64
    private String clientTitleofAuthorizedSignatoryForEntity;
    // 8 - 49
    private String clientUSCISOnlineAcctNumber;
    // 9 - 62
    private String clientANumber;
    // 10 - 63
    private String clientDaytimeTelephoneNumber;
    // 11 - 48
    private String clientMobileTelephoneNumber;
    // 12 - 61
    private String clientEMail;
    // 13 - 53
    private String clientStreetNumberName;
    // 13b - checkbox apt
    private String aptCheckbox;
    // 13b - checkbox ste
    private String steCheckbox;
    // 13b - checkbox flr
    private String flrCheckbox;
    // 13c - 58
    private String clientAptNumber;
    // 13d - 54
    private String clientCity;
    // - checkbox state
    private String stateDropdown;
    // 13e - 55
    private String clientZipCode;
    // 13f - 51
    private String clientProvince;
    // 13g - 50
    private String clientPostalCode;
    // 13h - 52
    private String clientCountry;
}
