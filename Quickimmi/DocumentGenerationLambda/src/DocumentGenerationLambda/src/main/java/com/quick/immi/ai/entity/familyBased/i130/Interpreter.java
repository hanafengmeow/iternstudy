package com.quick.immi.ai.entity.familyBased.i130;
import lombok.Data;

@Data
public class Interpreter {
    //484
    private String lastName;
    //483
    private String firstName;
    //485
    private String businessName;
    //487
    private String streetNumberAndName;
    //Part 7-3b 491
    private String aptCheckbox;
    private String steCheckbox;
    private String flrCheckbox;
    private String aptSteFlrNumber;
    //486
    private String city;
    //494
    private String state;
    //493
    private String zipCode;
    //496
    private String province;
    //492
    private String postalCode;
    //495
    private String country;
    //503
    private String daytimeTelephoneNumber;
    //505
    private String mobileTelephoneNumber;
    //504
    private String emailAddress;
    //497
    private String fluentInEnglish;
    //499
    private String signature;
    //498
    private String dateOfSignature;
}