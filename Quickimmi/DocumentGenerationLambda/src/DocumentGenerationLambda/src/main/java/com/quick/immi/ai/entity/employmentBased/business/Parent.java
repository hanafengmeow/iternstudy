package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

@Data
public class Parent {
    // Fields for basic identity and birth names
    private String lastName;
    private String firstName;
    private String middleName;

    private String birthLastName;
    private String birthFirstName;
    private String birthMiddleName;

    // Date and place of birth
    private String dateOfBirth;
    private String cityOfBirth;
    private String countryOfBirth;

    // Current place of residence
    private String currentCityOfResidence;
    private String currentCountryOfResidence;

    // Sex/Gender indicators
    private boolean sexOfFemaleCheckbox;
    private boolean sexOfMaleCheckbox;
}

