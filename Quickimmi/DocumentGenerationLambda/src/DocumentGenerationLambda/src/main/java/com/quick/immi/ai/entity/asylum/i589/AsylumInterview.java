package com.quick.immi.ai.entity.asylum.i589;

import lombok.Data;

@Data
public class AsylumInterview {
    private String allTrueYesCheckbox;
    private String notAllTrueYesCheckbox;

    //382
    private String fromNumber;
    //383
    private String toNumber;
    //387
    private String applicantSignature;
    //384
    private String date;
    //386
    private String nameNativeAlphabet;
    //385
    private String asylumOfficerSignature;
}
