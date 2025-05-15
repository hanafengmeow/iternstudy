package com.quick.immi.ai.entity.eoir28;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EOIR28Table {
    // Part 1 Party Information
    private PartyInformation partyInformation;
    // Part 2 Representation Type and apprearance
    private Representation representation;
    // Part 3 Representation Contact Information
    private AttorneyInfo attorneyInfo;
    // Part 4 Type of Appearance
    private TypeOfAppearance typeOfAppearance;
    // Part 5 Proof of Service
    private ProofOfService proofOfService;
    // Part 6 dditional Information
    private AdditionalInformation additionalInformation;
}
