package com.quick.immi.ai.entity.g28;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Representative {
    // form number - prerun number
    // 1 - 5
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
    private String aptCheckbox;
    // 3b - ste. checkbox
    private String steCheckbox;
    // 3b - flr. checkbox
    private String flrCheckbox;
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
}
