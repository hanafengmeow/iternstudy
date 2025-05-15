package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WageOffer {
    // Section G1 - Offered wage range: From
    private String offeredWageFrom;

    // Section G1 - Offered wage range: To (Optional)
    private String  offeredWageTo;

    // Section G1 - Period term for the offered wage (e.g., Hour, Week, Bi-Weekly, Month, Year)
    private String wagePer;
}