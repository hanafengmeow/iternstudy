package com.quick.immi.ai.entity.eoir28;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyInformation {
    // FirstName - 1
    private String firstName;
    // MiddleName - 2
    private String middleName;
    // LastName - 3
    private String lastName;
    // Address1 - 4
    private String address1;
    // Address2 - 5
    private String address2;
    // City - 6
    private String city;
    // State - 7
    private String state;
    // Zip - 8
    private String zip;
    // AlienNumber - 9
    private String alienNumber;
    // EntryOfAppearanceForDropdown - EntryOfAppearanceCB
    private String entryOfAppearanceForDropdown;

    private String allProceedingsCheckbox;
    private String custodyAndBondProceedingCheckbox;
    private String allProceedingsOtherThanCustodyCheckbox;
}
