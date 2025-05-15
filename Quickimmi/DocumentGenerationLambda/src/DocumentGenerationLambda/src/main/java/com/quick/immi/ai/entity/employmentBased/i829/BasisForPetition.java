package com.quick.immi.ai.entity.employmentBased.i829;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasisForPetition {
    
    /**
     * Indicates whether the investment is associated with a Regional Center.
     * Expected values: "Yes" or "No"
     */
    private String investmentAssociatedWithRegionalCenterYes;
    private String investmentAssociatedWithRegionalCenterNo;
    
    /**
     * The name of the Regional Center.
     */
    private String regionalCenterName;
    
    /**
     * The identification number of the Regional Center.
     */
    private String regionalCenterIdentificationNumber;
    
    /**
     * The name of the New Commercial Enterprise (NCE).
     */
    private String newCommercialEnterpriseName;
    
    /**
     * The identification number of the New Commercial Enterprise (NCE).
     */
    private String nceIdentificationNumber;
    
    /**
     * Indicates if the petitioner is a conditional permanent resident based on their investment.
     */
    private String conditionalPermanentResidentBasedOnInvestment;
    
    /**
     * Indicates if the petitioner is a conditional permanent resident filing separately from the investor.
     */
    private String conditionalPermanentResidentFilingSeparately;
    
    /**
     * Indicates if the petitioner is a conditional permanent resident spouse or child of a deceased investor.
     */
    private String conditionalPermanentResidentSpouseOrChildOfDeceasedInvestor;
}