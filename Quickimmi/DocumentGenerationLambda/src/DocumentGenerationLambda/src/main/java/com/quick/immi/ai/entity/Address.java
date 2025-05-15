package com.quick.immi.ai.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String inCareOfName;
    private String streetNumberAndName;
    private String aptCheckbox;
    private String steCheckbox;
    private String flrCheckbox;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String fromDate;
    private String endDate;
}
