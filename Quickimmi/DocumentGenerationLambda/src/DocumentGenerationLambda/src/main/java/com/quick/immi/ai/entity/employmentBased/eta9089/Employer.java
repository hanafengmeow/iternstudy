package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employer {
    // Section C1 - Employer’s name
    private String employerName;

    // Section C2 - Address 1
    private String addressLine1;

    // Section C2 - Address 2 (if any)
    private String addressLine2;

    // Section C3 - City
    private String city;

    // Section C4 - Phone number
    private String phoneNumber;

    // Section C5 - Number of employees
    private String numberOfEmployees;

    // Section C6 - Year commenced business
    private String yearCommencedBusiness;

    // Section C7 - FEIN (Federal Employer Identification Number)
    private String fein;

    // Section C8 - NAICS Code
    private String naicsCode;

    // Section C9 - Is the employer a closely held corporation, partnership, or sole proprietorship?
    private String isCloselyHeldCorporation;
}