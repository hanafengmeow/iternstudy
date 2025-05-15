package com.quick.immi.ai.entity.asylum.i589;

import lombok.Data;

@Data
public class RemovalHearing {
    private String allTrueYesCheckbox;
    private String notAllTrueYesCheckbox;

    //390
    private String fromNumber;
    //391
    private String toNumber;
    //395
    private String applicantSignature;
    //392
    private String date;
    //394
    private String nameNativeAlphabet;
    //393
    private String immigrationJudgeSignature;
}
