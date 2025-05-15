package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlienDeclaration {
    // Section L - Declaration by the alien regarding the truthfulness of their information and intention to accept the job if certified

    // L1 - Alien's affirmation of the truthfulness of the information provided
    private String lastName;

    // L2 - Alien's signature (Note: This typically won't be stored in a data class due to security practices around signature data)
    private String signature;
}