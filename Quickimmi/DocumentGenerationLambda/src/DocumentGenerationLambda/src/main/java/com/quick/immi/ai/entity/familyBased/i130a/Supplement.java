package com.quick.immi.ai.entity.familyBased.i130a;
import java.util.List;
import lombok.Data;

@Data
public class Supplement {
    //284
    private String lastName;
    //285
    private String firstName;
    //286
    private String middleName;
    //293
    private String alienNumber;

    private List<AdditionalRecord> additionalRecords; 

    @Data
    public static class AdditionalRecord {
        private String pageNumber;   // Page number of the form
        private String partNumber;   // Part number of the form
        private String itemNumber;   // Item number of the form
        private String additionalInfo; // Additional information details
    }
}
