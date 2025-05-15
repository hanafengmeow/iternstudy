package com.quick.immi.ai.entity.familyBased.i130a;

import lombok.Data;

@Data
public class Parent {
    private String pageNumber = "2";
    private String partNumber = "1";
    private String itemNumber = "10 - 16";

    private String lastName;
    private String firstName;
    private String middleName;
    private String dateOfBirth;

    private String sexOfMaleCheckbox;
    private String sexOfFemaleCheckbox;

    private String cityOfBirth;
    private String countryOfBirth;
    private String currentCityOfResidence;
    private String currentCountryOfResidence;
}