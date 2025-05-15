package com.quick.immi.ai.entity.asylum.i589;

import lombok.Data;

@Data
public class Child {
    //122
    private String alienNumber;
    //123
    private String passportNumber;
    //124
    private String martialStatus;
    //125
    private String ssn;
    //109
    private String lastName;
    //111
    private String firstName;
    //112
    private String middleName;
    //113
    private String birthDate;
    //110
    private String cityAndCountryOfBirth;
    //114
    private String nationality;
    //115
    private String race;
    //1
    private String genderMaleCheckbox;
    //2
    private String genderFemaleCheckbox;

    private String personInUSYesCheckbox;
    private String personInUSNoCheckbox;
    //132
    private String specifyLocationIfNotInUS;
    //116
    private String placeLastEntryIntoUS;

    //117
    private String lastEntryUSDate;
    //118
    private String i94Number;

    //119
    private String lastAdmittedStatus;
    //120
    private String currentStatus;

    //121
    private String statusExpireDate;

    //Yes
    private String immigrationCourtProceedingYesCheckbox;
    //No
    private String immigrationCourtProceedingNoCheckbox;
    //Y
    private String inThisApplicationYesCheckbox;
    //N
    private String inThisApplicationNoCheckbox;
}
