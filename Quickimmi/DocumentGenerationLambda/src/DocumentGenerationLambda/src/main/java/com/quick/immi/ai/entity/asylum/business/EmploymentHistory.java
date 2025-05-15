package com.quick.immi.ai.entity.asylum.business;

import lombok.Data;

@Data
public class EmploymentHistory {
    private String part = "Part A.III";
    private String question = "Q4";
    private String nameAndAddress;
    private String occupation;
    private String startDate;
    private String endDate;
}
