package com.quick.immi.ai.entity.familyBased.i130a;

import lombok.Data;

@Data
public class PetitionerStatement {
    //Part 4-1a
    private String canReadAndUnderstandEnglishCheckbox;
    //Part 4-1b 207
    private String interpreterReadAndTranslatedCheckbox;
    private String interpreterReadAndTranslatedNumber;
    //209
    private String preparerAssistanceCheckbox;
    private String preparerAssistanceNumber;
    //210
    private String daytimeTelephoneNumber;
    //212
    private String mobileTelephoneNumber;
    //211
    private String emailAddress;
    //226
    private String signature;
    //225
    private String dateOfSignature;
}
