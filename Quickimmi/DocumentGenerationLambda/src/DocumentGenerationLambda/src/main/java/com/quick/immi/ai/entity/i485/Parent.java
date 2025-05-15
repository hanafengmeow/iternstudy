package com.quick.immi.ai.entity.i485;

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
    private String dateOfBirth; // Mother: P6Q3, Father: P7Q11
    private String countryOfBirth; // Mother: P6Q6, Father: P7Q14
}

