package com.quick.immi.ai.entity.employmentBased.i829;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterpreterContactCertification {

    // Interpreter’s Full Name
    private String interpreterFamilyName;
    private String interpreterGivenName;
    private String interpreterBusinessOrOrganizationName;

    // Interpreter’s Contact Information
    private String interpreterDaytimeTelephoneNumber;
    private String interpreterMobileTelephoneNumber;
    private String interpreterEmailAddress;

    // Interpreter’s Certification
    private String interpreterLanguageFluency;
    private String interpreterSignature;
    private String interpreterDateOfSignature; // Expected format: mm/dd/yyyy
}
