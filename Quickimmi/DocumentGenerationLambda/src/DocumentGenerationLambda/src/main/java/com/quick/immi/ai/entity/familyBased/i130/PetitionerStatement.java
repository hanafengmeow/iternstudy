package com.quick.immi.ai.entity.familyBased.i130;
import lombok.Data;

@Data

public class PetitionerStatement {
    //Part 6-1a
    private String canReadAndUnderstandEnglishCheckbox;
    //Part 6-1b 478
    private String interpreterReadAndTranslatedCheckbox;
    private String interpreterReadAndTranslatedNumber;
    //480
    private String preparerAssistanceCheckbox;
    private String preparerAssistanceNumber;
    //469
    private String daytimeTelephoneNumber;
    //471
    private String mobileTelephoneNumber;
    //470
    private String petitionerEmailAddress;
    //482
    private String petitionerSignature;
    //481
    private String dateOfSignature;
}
