package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class I485Table {

    private TopRight topRight;

    // PART 0 - Attorney or Representative Information
    private Attorney attorney;

    // PART 1 - Information About You
    private Applicant applicant;

    // PART 2 - Application Type or Filing Category
    private ApplicationType applicationType;

    // Part 3 - Request for Exemption for Intending Immigrant's Affidavit of Support
    private AffidavitExemption affidavitExemption;

    // Part 4 - Additional Information About You
    private Background background;

    // Part 5 - Information About Your Marital History
    private ParentInfo parentInfo;

    // Part 6 - Information About Your Marital History
    private MaritalHistory maritalHistory;

    // Part 7 - Information About Your Children
    private ChildrenInfo childrenInfo;

    // Part 8 - Biographic Information
    private Biographic biographic;

    // Part 9 - Eligibility
    private Eligibility eligibility;

    // Part 10 - Accommodations for Individuals With Disabilities and/or Impairments
    private ApplicantContact applicantContact;

    // Part 11 - Interpreter's Information
    private Interpreter interpreter;

    // Part 12 - Preparer's Information
    private Preparer preparer;

    // Part 13 - Signature at Interview (SKIP)
    private Signature signature;

    // Part 14 - Supplement
    private Supplement supplements;
}

