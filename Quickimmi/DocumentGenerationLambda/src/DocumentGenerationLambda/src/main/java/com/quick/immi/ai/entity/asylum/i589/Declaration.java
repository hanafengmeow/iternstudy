package com.quick.immi.ai.entity.asylum.i589;

import lombok.Data;

@Data
public class Declaration {
    private String preparerSignature;
    private String completeName;
    private String teleNumberAreacode;
    private String teleNumber;
    private String streetNumberAndName;
    private String aptNumber;
    private String city;
    private String state;
    private String zipCode;
    //1
    private String g28FormAttachedYesCheckbox;
    private String attorneyStateBarNumber;
    private String attorneyUscisOnlineAccountNumber;
}
