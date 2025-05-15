package com.quick.immi.ai.entity.familyBased.i130;

import java.util.List;
import lombok.Data;

@Data
public class Supplement {
    //541
    private String lastName;

    //542
    private String firstName;

    //543
    private String middleName;

    //526
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