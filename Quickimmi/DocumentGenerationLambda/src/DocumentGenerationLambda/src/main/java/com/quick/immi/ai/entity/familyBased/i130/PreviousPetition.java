package com.quick.immi.ai.entity.familyBased.i130;

import java.util.List;
import lombok.Data;


@Data
public class PreviousPetition {
    //part 5-1
    private String filedPetitionYesCheckbox;
    private String filedPetitionNoCheckbox;

    //456
    private String lastName;
    //457
    private String firstName;
    //458
    private String middleName;
    //461
    private String city;
    //462
    private String state;
    //460
    private String date;
    //459
    private String result;


    private List<AdditionalRelative> additionalRelatives;
}
