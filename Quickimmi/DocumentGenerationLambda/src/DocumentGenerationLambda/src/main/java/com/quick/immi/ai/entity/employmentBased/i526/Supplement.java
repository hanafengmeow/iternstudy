package com.quick.immi.ai.entity.employmentBased.i526;

import lombok.Data;

import java.util.List;


///

//
@Data
public class Supplement {
    // Page 20, Question 1a - Last Name
    private String lastName;

    // Page 20, Question 1b - First Name
    private String firstName;

    // Page 20, Question 1c - Middle Name
    private String middleName;

    // Page 20, Question 2 - Alien Number
    private String alienNumber;

    // List to manage multiple blocks of additional information
    private List<AdditionalRecord> additionalRecords;

    @Data
    public static class AdditionalRecord {
        private String pageNumber;   // Page number of the form
        private String partNumber;   // Part number of the form
        private String itemNumber;   // Item number of the form
        private String additionalInfo; // Additional information details
    }
}
