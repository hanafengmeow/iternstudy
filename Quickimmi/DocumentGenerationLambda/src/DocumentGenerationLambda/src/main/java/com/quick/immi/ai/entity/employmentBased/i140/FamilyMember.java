package com.quick.immi.ai.entity.employmentBased.i140;

import lombok.Data;

@Data
public class FamilyMember {

    //
    private String pageNumber = "5";
    //
    private String partNumber = "7";
    //
    private String itemNumber = "1-6";

    private String lastName;

    private String firstName;

    private String middleName;


    private String dateOfBirth;

    private String countryOfBirth;

    private String relationship;

    private String isApplyingForAdjustmentOfStatusYes;
    private String isApplyingForAdjustmentOfStatusNo;

    private String isApplyingForVisaAboardYes;
    private String isApplyingForVisaAboardNo;
}
