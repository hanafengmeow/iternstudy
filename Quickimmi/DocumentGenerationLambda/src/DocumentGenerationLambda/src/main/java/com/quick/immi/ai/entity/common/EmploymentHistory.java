package com.quick.immi.ai.entity.common;

import com.quick.immi.ai.entity.Address;
import lombok.Data;

@Data
public class EmploymentHistory {
    private String employerName;
    private String streetNumberAndName;
    private String aptCheckbox;
    private String steCheckbox;
    private String flrCheckbox;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String jobTitle;
    private String fromDate;
    private String endDate;
}
