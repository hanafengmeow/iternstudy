package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreparerDeclaration {
    // Section M - Information about the person who prepared the application, if it wasn't the employer

    // M1 - Was the application completed by the employer?
    private String completedByEmployer;

    // M2 - Preparer’s last name, if not completed by employer
    private String lastName;

    // M3 - Title
    private String title;

    // M4 - E-mail address
    private String email;

    // M5 - Signature of Preparer (Note: This typically won't be stored in a data class due to security practices around signature data)
    private String signature;
}
