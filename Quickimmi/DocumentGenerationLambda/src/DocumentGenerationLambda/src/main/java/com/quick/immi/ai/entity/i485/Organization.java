package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class Organization {
    private String orgName;
    private String cityTown;
    private String state;
    private String country;
    private String natureOfGroup;
    private String natureOfInvolvement; 
    private String dateFrom;
    private String dateTo;
}
