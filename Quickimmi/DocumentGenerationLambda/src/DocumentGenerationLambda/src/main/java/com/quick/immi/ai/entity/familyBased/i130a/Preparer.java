package com.quick.immi.ai.entity.familyBased.i130a;

import lombok.Data;

@Data
public class Preparer {
    //246
    private String lastName;
    //244
    private String firstName;
    //245
    private String businessName;
    //251
    private String streetNumberAndName;
    //Part 6-3b 255
    private String aptCheckbox;
    private String steCheckbox;
    private String flrCheckbox;
    private String aptSteFlrNumber;
    //250
    private String city;
    //258
    private String state;
    //257
    private String zipCode;
    //260
    private String province;
    //256
    private String postalCode;
    //259
    private String country;
    //262
    private String daytimeTelephoneNumber;
    //261
    private String mobileTelephoneNumber;
    //263
    private String emailAddress;
    //Part 6-7a
    private String nonAttorneyPreparerCheckbox;
    //Part 6-7b 
    private String attorneyPreparerCheckbox;
    private String attorneyExtendsCheckbox;
    private String attorneyNotExtendCheckbox;
    //268
    private String signature;
    //269
    private String dateOfSignature; 
}
