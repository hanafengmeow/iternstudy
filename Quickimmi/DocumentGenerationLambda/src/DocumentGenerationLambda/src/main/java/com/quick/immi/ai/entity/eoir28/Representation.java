package com.quick.immi.ai.entity.eoir28;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Representation {
    // Representation Type Dropdown - Attorney_CB
    private String attorneyCheckbox;
    // Court Name - 10
    private String courtName;
    // Bar Number - 11
    private String barNumber;
    private String representativeCheckbox;
    // Organization - 12
    private String organization;
    private String lawStudentCheckbox;
    private String reputableCheckbox;
    private String foreignGovernmentOfficialCheckbox;
    // Country (for foreign government officials) - 13
    private String country;
    private String personWithAuthorizationCheckbox;

    // Attorey Apprearance Dropdown - AttorneyAppearance_CB
    private String appearanceAttorneyCheckbox;
    //EOIR has ordered the provision of a Qualified Representative for the party named above and I appear in that capacity.
    private String qualifiedRepresentativeCheckbox;

    private String eoirIdNumber;
    private String date;
}
