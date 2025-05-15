package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

@Data
public class Child {
    private String pageNumber = "8";
    private String partNumber = "6";
    private String itemNumber = "2-16";

    // Page 8-9, Question 2, 7, 12 - Child's Name
    private String lastName;
    private String firstName;
    private String middleName;

    // Page 8-9, Question 3, 8, 13 - Child's A-Number
    private String alienNumber;

    // Page 8-9, Question 4-5, 9-10, 14-15 - Child's Date of Birth
    private String dateOfBirth;
    private String countryOfBirth;
    private String relationship;
    // Page 8-9, Question 6, 11, 16 - Is Child Applying
    private boolean applyingYesCheckbox;
    private boolean applyingNoCheckbox;
}
