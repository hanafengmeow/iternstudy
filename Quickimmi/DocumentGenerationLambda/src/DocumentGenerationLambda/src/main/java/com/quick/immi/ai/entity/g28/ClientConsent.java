package com.quick.immi.ai.entity.g28;

import lombok.Data;

@Data
public class ClientConsent {
    // Part 4 - g28 number - prerun number
    // 1a - checkbox send original notices to my representative
    private String noticetoRepresentativeCheckbox;
    // 1b - checkbox send secure identity document to my representative
    private String secureIDtoRepresentativeCheckbox;
    // 1c - checkbox send my notice to me
    private String noticetoMeCheckbox;
    // 2a - 74
    private String signatureofClient;
    // 2b - 73
    private String dateofSignatureClient;
}