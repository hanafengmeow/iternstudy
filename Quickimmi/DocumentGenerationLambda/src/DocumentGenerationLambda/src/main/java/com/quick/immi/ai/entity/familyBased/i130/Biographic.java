package com.quick.immi.ai.entity.familyBased.i130;
import lombok.Data;

@Data
public class Biographic {
    //Part3-1
    private String ethnicityHispanicCheckbox;
    private String ethnicityNotHispanicCheckbox;

    //Part3-2
    private String raceWhiteCheckbox;
    private String raceAsianCheckbox;
    private String raceBlackCheckbox;
    private String raceAmericanIndianCheckbox;
    private String raceNativeHawaiianCheckbox;

    //268
    private String heightFeet;
    //269
    private String heightInches;
    //27
    private String weightPounds1;
    private String weightPounds2;
    private String weightPounds3;
    //Part3-5
    private String eyeColorBlackCheckbox;
    private String eyeColorBlueCheckbox;
    private String eyeColorBrownCheckbox;
    private String eyeColorGrayCheckbox;
    private String eyeColorGreenCheckbox;
    private String eyeColorHazelCheckbox;
    private String eyeColorMaroonCheckbox;
    private String eyeColorPinkCheckbox;
    private String eyeColorUnknownCheckbox;
    //Part3-6
    private String hairColorBaldCheckbox;
    private String hairColorBlackCheckbox;
    private String hairColorBlondCheckbox;
    private String hairColorBrownCheckbox;
    private String hairColorGrayCheckbox;
    private String hairColorRedCheckbox;
    private String hairColorSandyCheckbox;
    private String hairColorWhiteCheckbox;
    private String hairColorUnknownCheckbox;
}
