package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentAttorney {
    // Section E1 - Agent or attorney’s last name
    private String lastName;

    // Section E2 - Firm name
    private String firmName;

    // Section E3 - Firm EIN
    private String firmEin;

    // Section E5 - Address 1
    private String addressLine1;

    // Section E5 - Address 2 (if any)
    private String addressLine2;

    // Section E6 - City
    private String city;

    // Section E7 - Email address
    private String emailAddress;
}
