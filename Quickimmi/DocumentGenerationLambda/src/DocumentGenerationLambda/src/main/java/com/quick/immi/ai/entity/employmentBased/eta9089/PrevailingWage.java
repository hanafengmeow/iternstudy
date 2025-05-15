package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrevailingWage {
    // Section F1 - Prevailing wage tracking number (if applicable)
    private String trackingNumber;

    // Section F3 - Occupation title
    private String occupationTitle;

    // Section F5 - Prevailing wage
    private String prevailingWage;

    // Section F5 - Period term for the prevailing wage (e.g., Hour, Week, Bi-Weekly, Month, Year)
    private String wagePer;

    // Section F6 - Prevailing wage source (e.g., OES, CBA, Employer Conducted Survey, DBA, AC, Other)
    private String wageSource;

    // Section F6-A - If "Other" is indicated in question 6, specify
    private String otherWageSourceDetails;

    // Section F7 - Determination date
    private String determinationDate;

    // Section F8 - Expiration date
    private String expirationDate;
}
