package com.quick.immi.ai.entity.familyBased.i130;

import lombok.Data;

@Data
public class Preparer {
    //502
    private String lastName;
    //500
    private String firstName;
    //501
    private String businessName;
    //507
    private String streetNumberAndName;
    //Part 8-3b 511
    private String aptCheckbox;
    private String steCheckbox;
    private String flrCheckbox;
    private String aptSteFlrNumber;
    //506
    private String city;
    //514
    private String state;
    //513
    private String zipCode;
    //516
    private String province;
    //512
    private String postalCode;
    //515
    private String country;
    //518
    private String daytimeTelephoneNumber;
    //517
    private String mobileTelephoneNumber;
    //519
    private String emailAddress;
    //Part 8-7a
    private String nonAttorneyPreparerCheckbox;
    private String attorneyPreparerCheckbox;
    //Part 8-7b
    private String attorneyExtendsCheckbox;
    private String attorneyNotExtendCheckbox;
    //524
    private String signature;
    //525
    private String dateOfSignature;
}
