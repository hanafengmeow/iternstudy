package com.quick.immi.ai.entity.employmentBased.i829;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetitionerContactCertification {

    // Petitioner’s Contact Information
    private String daytimeTelephoneNumber;
    private String mobileTelephoneNumber;
    private String emailAddress;

    // Petitioner’s Certification and Signature
    private String petitionerSignature;
    private String dateOfSignature; // Expected format: mm/dd/yyyy
}