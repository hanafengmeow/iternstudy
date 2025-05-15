package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class Preparer {
    private String lastName;
    private String firstName;
    private String orgName;

    // Page 18, Question 4 - Daytime Phone Number
    private String daytimePhoneNumber;

    // Page 18, Question 5 - Mobile Phone Number
    private String mobileTelephoneNumber;

    // Page 18, Question 6 - Email Address
    private String emailAddress;

    // Page 19, Question 8a - Signature
    private String signature;

    // Page 19, Question 8b - Date of Signature
    private String dateOfSignature;
}
