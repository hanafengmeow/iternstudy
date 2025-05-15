package com.quick.immi.ai.entity.common;

import lombok.Data;

@Data
public class FamilyMember {
    private String lastName;
    private String middleName;
    private String firstName;
    private String birthDate;
    private String birthCountry;
    private String spouseCheckbox;
    private String childChildCheckbox;
    private String applyForAdjustOfStatusYes;
    private String applyForAdjustOfStatusNo;
    private String applyForVisaAbroadYes;
    private String applyForVisaAbroadNo;
}
