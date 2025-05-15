package com.quick.immi.ai.entity.familyBased.i130;
import lombok.Data;

@Data

public class EmploymentHistory {
    private String pageNumber = "4";
    private String partNumber = "2";
    private String itemNumber = "42 - 45";

    private String nameOfEmployer;
    private String streetNumberAndName;

    private String aptCheckbox;
    private String steCheckbox;
    private String flrCheckbox;
    private String aptSteFlrNumber;

    private String city;
    private String state;
    private String zipCode;
    private String province;
    private String postalCode;
    private String country;
    private String occupation;
    private String dateFrom;
    private String dateTo;
}
