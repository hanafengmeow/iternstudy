package com.quick.immi.ai.entity.g28;

import lombok.Data;

@Data
public class RepresentativeSignature {
    // Part 5 - g28 number - prerun number
    // 1a - 66
    private String signatureofRepresentative;
    // 1b - 70
    private String dateofSignatureRepresentative;
    // 2a - 67
    private String signatureofLawGraduate;
    // 2b - 68
    private String dateofSignatureLawGraduate;
}