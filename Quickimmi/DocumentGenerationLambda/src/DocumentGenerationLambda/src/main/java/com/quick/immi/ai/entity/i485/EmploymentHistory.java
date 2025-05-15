package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class EmploymentHistory {

    private String currentEmployer;
    private String employerName;
    private String occupation;
    private Address address;
    private String financialSupport;
}

