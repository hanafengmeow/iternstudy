package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class ApplicantContact {
    private String applicantDaytimePhoneNumber; // Applicant's Daytime Telephone Number
    private String applicantMobilePhoneNumber;  // Applicant's Mobile Telephone Number (if any)
    private String applicantEmailAddress;       // Applicant's Email Address (if any)
    private String applicantSignature;          // Applicant's Signature
    private String dateOfSignature;             // Date of Signature (mm/dd/yyyy)
}
