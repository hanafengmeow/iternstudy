package com.quick.immi.ai.entity.employmentBased.i829;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiographicInformation {

    // Ethnicity
    private String isHispanicOrLatino;
    private String isNotHispanicOrLatino;

    // Race
    private String isWhite;
    private String isAsian;
    private String isBlackOrAfricanAmerican;
    private String isAmericanIndianOrAlaskaNative;
    private String isNativeHawaiianOrOtherPacificIslander;

    // Height
    private String heightFeet;
    private String heightInches;

    // Weight
    private String weight;

    // Eye Color
    private String isEyeColorBlack;
    private String isEyeColorBlue;
    private String isEyeColorBrown;
    private String isEyeColorGray;
    private String isEyeColorGreen;
    private String isEyeColorHazel;
    private String isEyeColorMaroon;
    private String isEyeColorPink;
    private String isEyeColorUnknownOrOther;

    // Hair Color
    private String isHairColorBald;
    private String isHairColorBlack;
    private String isHairColorBlond;
    private String isHairColorBrown;
    private String isHairColorGray;
    private String isHairColorRed;
    private String isHairColorSandy;
    private String isHairColorWhite;
    private String isHairColorUnknownOrOther;
}
