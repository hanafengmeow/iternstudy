package com.quick.immi.ai.entity;

import lombok.Data;
@Data
public class LawyerBasicInfo {
    private String uscisOnlineAccountNumber;
    // 2a - 4
    private String lastName;
    // 2b - 3
    private String firstName;
    // 2c - 2
    private String middleName;
    // 3a - 6
    private String streetNumberAndName;
    // 3b - apt. checkbox
    private boolean aptCheckbox;
    // 3b - ste. checkbox
    private boolean steCheckbox;
    // 3b - flr. checkbox
    private boolean flrCheckbox;
    // 3b - 9
    private String aptSteFlrNumber;
    // 3c - 11
    private String city;
    // 3d - state dropdown
    private String stateDropdown;
    // 3e - 13
    private String zipCode;
    // 3f - 14
    private String province;
    // 3g - 16
    private String postalCode;
    // 3h - 15
    private String country;
    // 4 - 1
    private String daytimeTelephoneNumber;
    // 5 - 18
    private String mobileTelephoneNumber;
    // 6 - 19 !!!notice - the prerun name of 18 & 19 flipped.
    private String emailAddress;
    // 7 - 17
    private String faxNumber;

    private String eoirNumber;
}
