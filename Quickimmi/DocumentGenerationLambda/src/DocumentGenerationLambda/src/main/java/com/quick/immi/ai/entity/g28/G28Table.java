package com.quick.immi.ai.entity.g28;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class G28Table {
    // Part 1  Information About Attorney or Accredited Representative
    private Representative representative;
    // Part 2 Eligibility Information for Attorney or Accredited Representative
    private RepresentativeEligibility representativeEligibility;
    // Part 3 Notice of Appearance as Attorney or Accredited Representative
    private Appearance appearance;
    // Part 4 Client's Consent to Representation and Signature
    private ClientConsent clientConsent;
    // Part 5 Signature of Attorney or Accredited Representative
    private RepresentativeSignature representativeSignature;
    // Part 6 Additional Information
    private AdditionalInfo additionalInfo;
}

