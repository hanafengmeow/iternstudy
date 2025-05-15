package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDeclaration {
    // Section N - Employer's Declaration about the job and application

    // N1 - Employer’s name
    private String lastName;

    // N1 - Employer’s title
    private String title;

    // N3 - Date signed
    private String signature;
}
