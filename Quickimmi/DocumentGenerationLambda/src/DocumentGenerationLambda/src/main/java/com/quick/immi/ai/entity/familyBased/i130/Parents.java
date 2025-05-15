package com.quick.immi.ai.entity.familyBased.i130;

import lombok.Data;

@Data
public class Parents {
    private String part = "Part 2";
    private String question = "Q24 - Q35";
    private String lastName; // Mother: P3Q24a, Father: P3Q30a
    private String firstName; // Mother: P3Q24b, Father: P3Q30b
    private String middleName; // Mother: P3Q24c, Father: P3Q30c
    private String dateOfBirth; // Mother: P3Q25, Father: P3Q31

    private String sexOfMaleCheckbox; // Mother: P3Q26, Father: P3Q32
    private String sexOfFemaleCheckbox;  // Mother: P3Q26, Father: P3Q32
    
    private String countryOfBirth; // Mother: P3Q27, Father: P3Q33
    private String cityOfResidence; // Mother: P3Q28, Father: P3Q34
    private String countryOfResidence; // Mother: P3Q29, Father: P3Q35
}

  
    