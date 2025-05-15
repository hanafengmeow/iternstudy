package com.quick.immi.ai.entity.eoir28;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttorneyInfo {
    // RepInfoFirstName - 16
    private String firstName;
    // RepInfoMiddleName - 17
    private String middleName;
    // RepInfoLastName - 18
    private String lastName;
    // RepInfoAddress1 - 19
    private String address1;
    // RepInfoAddress2 - 20
    private String address2;
    // Law Firm - 41
    private String lawFirm;
    // RepInfoCity - 21
    private String city;
    // RepInfoState - 22
    private String state;
    // RepInfoZip - 23
    private String zip;

    private String telephone;
    // RepInfoEmail - 26
    private final String facsimile = "N/A";
    // NewAddressCB Checkbox - NewAddressCB
    private String email;
}
