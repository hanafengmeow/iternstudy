package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployerContact {
    // Section D1 - Contact’s last name
    private String lastName;

    // Section D1 - Contact’s first name
    private String firstName;

    // Section D1 - Contact’s middle initial
    private String middleInitial;

    // Section D2 - Address 1
    private String addressLine1;

    // Section D2 - Address 2 (if any)
    private String addressLine2;

    // Section D3 - City
    private String city;

    // Section D4 - Phone number
    private String phoneNumber;

    // Section D5 - Email address
    private String emailAddress;
}