package com.quick.immi.ai.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationCase {
    private Long id;
    private Integer userId;
    private String subType;
    private String applicantName;
    private String type;
    private String currentStep;
    private String email;
    private String status;
    //json string --- in original language
    private String profile;
    //json string --- in english
    private String translatedProfile;
    private String profileEnglish;
    private String uscisReceiptNumber;
    private Integer assignedLawyer;
    private Long createdAt;
    private Long updatedAt;
}
