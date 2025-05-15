package com.quick.immi.ai.entity.asylum.business;

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
    private boolean g28FormAttachedYesCheckbox;
    private String attorneyStateNarNumber;
    private String attorneyUscisOnlineAccountNumber;
}
