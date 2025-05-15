package com.quick.immi.ai.entity.asylum.business;

import lombok.Data;

@Data
public class Spouse {
    private boolean notMarriedCheckbox;
    //77
    private String alienNumber;
    //84
    private String passportNumber;
    //85
    private String birthDate;

    //86
    private String ssn;
    //78
    private String lastName;
    //87
    private String firstName;
    //88
    private String middleName;
    //89
    private String namesUsedBefore;
    //78
    private String marriageDate;
    //90
    private String marriagePlace;
    //91
    private String cityAndCountryOfBirth;
    //80
    private String nationality;
    //92
    private String race;

    private boolean genderMaleCheckbox;
    private boolean genderFemaleCheckbox;

    //Y
    private boolean personInUSYesCheckbox;
    //N
    private boolean personInUSNoCheckbox;
    //81
    private String specifyLocationIfNotInUS;
    //82
    private String placeLastEntryIntoUS;
    //96
    private String lastEntryUSDate;
    //97
    private String i94Number;
    //98
    private String lastAdmittedStatus;
    //83
    private String currentStatus;
    //99
    private String statusExpireDate;

    //Yes
    private boolean immigrationCourtProceedingYesCheckbox;
    //N
    private boolean immigrationCourtProceedingNoCheckbox;

    //100
    private String previousArrivalDate;

    private boolean inThisApplicationYesCheckbox;
    private boolean inThisApplicationNoCheckbox;
}
