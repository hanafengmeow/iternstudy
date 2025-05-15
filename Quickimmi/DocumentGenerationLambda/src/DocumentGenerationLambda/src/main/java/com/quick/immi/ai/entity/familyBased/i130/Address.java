package com.quick.immi.ai.entity.familyBased.i130;

import lombok.Data;

@Data
public class Address {
    private String pageNumber = "2";
    private String partNumber = "2";
    private String itemNumber = "12 - 15";

    private String streetNumberAndName;

    private String aptCheckbox;
    private String steCheckbox;
    private String flrCheckbox;
    private String aptSteFlrNumber;
    
    private String cityOrTown;
    private String state;
    private String zipCode;
    private String province;
    private String postalCode;
    private String country;
    private String dateFrom;
    private String dateTo;
}
