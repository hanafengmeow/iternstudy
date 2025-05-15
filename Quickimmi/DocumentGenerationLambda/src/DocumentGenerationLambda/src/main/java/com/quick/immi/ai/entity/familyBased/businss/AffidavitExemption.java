package com.quick.immi.ai.entity.familyBased.businss;

import lombok.Data;

@Data
public class AffidavitExemption {
    // todo new added
    private boolean hasEarned40QualifyingQuarters; // I have earned or can receive credit for 40 qualifying quarters of work in the United States (SSA defined).
    private boolean isUnder18AndChildOfUSCitizen; // I am under 18 years of age, unmarried, the child of a U.S. citizen, and will automatically become a U.S. citizen under INA section 320.
    private boolean isApplyingAsWidowOrWidower; // I am applying under the widow or widower of a U.S. citizen (Form I-360) immigrant category.
    private boolean isVAWASelfPetitioner; // I am applying as a VAWA self-petitioner.
    private boolean isNotRequiredToSubmitAffidavitAndNoExemption; // None of these exemptions apply, and I am not required to submit an Affidavit of Support Under Section 213A of the INA, nor am I required to request an exemption.
    private boolean isRequiredToSubmitAffidavit; // None of these exemptions apply, and I am required to submit an Affidavit of Support Under Section 213A of the INA.
}

