package com.quick.immi.ai.entity.employmentBased.i829;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreparerContactCertification {

    // Preparer’s Full Name
    private String preparerFamilyName;
    private String preparerGivenName;
    private String preparerBusinessOrOrganizationName;

    // Preparer’s Contact Information
    private String preparerDaytimeTelephoneNumber;
    private String preparerMobileTelephoneNumber;
    private String preparerEmailAddress;

    // Preparer’s Certification and Signature
    private String preparerSignature;
    private String preparerDateOfSignature; // Expected format: mm/dd/yyyy
}