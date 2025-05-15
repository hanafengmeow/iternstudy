package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class Interpreter {
    private String lastName;
    private String firstName;
    private String orgName;

    // Page 17, Question 4 - Daytime Telephone Number
    private String daytimeTelephoneNumber;

    // Page 17, Question 5 - Mobile Telephone Number
    private String mobileTelephoneNumber;

    // Page 17, Question 6 - Email Address
    private String emailAddress;

    // Page 18, Question 6 - Fluent Language
    private String fluentLanguage;

    // Page 18, Question 7a - Signature
    private String signature;

    // Page 18, Question 7b - Date of Signature
    private String dateOfSignature;
}
