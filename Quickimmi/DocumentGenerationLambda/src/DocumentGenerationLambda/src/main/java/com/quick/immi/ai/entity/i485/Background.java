package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class Background {

    // Question 1 - Immigrant Visa Application at U.S. Embassy or Consulate
    private String hasAppliedForImmigrantVisaYes;
    private String hasAppliedForImmigrantVisaNo;

    // Question 2 - Location of U.S. Embassy or Consulate
    private String embassyCity;
    private String embassyCountry;

    // Question 3 - Decision on Immigrant Visa
    private String visaDecision;

    // Question 4 - Date of Decision
    private String visaDecisionDate;

    // Question 5 - Previously Applied for Permanent Residence
    private String hasPreviouslyAppliedForPermanentResidenceYes;
    private String hasPreviouslyAppliedForPermanentResidenceNo;

    // Question 6 - Held Lawful Permanent Resident Status
    private String hasHeldPermanentResidentStatusYes;
    private String hasHeldPermanentResidentStatusNo;

    // Question 7 - Employment and Educational History
    private EmploymentHistory employmentOrEducationHistories;

    // Question 8 - Most Recent Employer or School Outside the United States
    private EmploymentHistory mostRecentOutsideUS;
}

