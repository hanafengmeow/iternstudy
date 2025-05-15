package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

import java.util.List;

@Data
public class ApplicantAdditionalInformation {
    // i485 part4 1
    private boolean appliedImmigrationVisaBeforeYes;
    private boolean appliedImmigrationVisaBeforeNo;

    //i485 part4 2
    private List<UsEmbassy> usEmbassies;

    //i485 part4 3
    private String decisionDetail;

    //i485 part4 4
    private String decisionDate;

    //i485 part4 5
    private boolean previouslyAppliedForPermanentResidence;

    //i485 part4 6
    private boolean heldPermanentResidentStatus;

    //i485 part4 7
    private List<Organization> employmentAndEducationalHistory;




}
