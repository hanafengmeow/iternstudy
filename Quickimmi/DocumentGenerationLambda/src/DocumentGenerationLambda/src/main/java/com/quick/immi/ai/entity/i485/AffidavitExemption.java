package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class AffidavitExemption {

    private String hasEarned40QualifyingQuarters; // I have earned or can receive credit for 40 qualifying quarters of work in the United States (SSA defined).
    private String isUnder18AndChildOfUSCitizen; // I am under 18 years of age, unmarried, the child of a U.S. citizen, and will automatically become a U.S. citizen under INA section 320.
    private String isApplyingAsWidowOrWidower; // I am applying under the widow or widower of a U.S. citizen (Form I-360) immigrant category.
    private String isVAWASelfPetitioner; // I am applying as a VAWA self-petitioner.
    private String isNotRequiredToSubmitAffidavitAndNoExemption; // None of these exemptions apply, and I am not required to submit an Affidavit of Support Under Section 213A of the INA, nor am I required to request an exemption.
    private String isRequiredToSubmitAffidavit; // None of these exemptions apply, and I am required to submit an Affidavit of Support Under Section 213A of the INA.
}

