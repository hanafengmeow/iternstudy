package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class AdditionalRecord {
    
    private String pageNumber;   // Page number of the form
    private String partNumber;   // Part number of the form
    private String itemNumber;   // Item number of the form
    private String additionalInfo; // Additional information details
}
