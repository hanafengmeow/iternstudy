package com.quick.immi.ai.entity;

import lombok.Data;

@Data
public class LawyerEligibility {
    private boolean eligibleAttorneyCheckbox;
    // 1a - 31
    private String licensingAuthority;
    // 1b - 25
    private String barNumber;
    // 1c - checkbox am not
    private String amNotCheckbox;
    // 1c - checkbox am
    private boolean amCheckbox;
    // 1d - 32
    private String nameofLawFirm;
    // 2a - checkbox accredited representative
    private boolean accreditedRepresentativeCheckbox;
    // 2b - 20
    private String nameofRecognizedOrganization;
    // 2c - 21
    private String dateofAccreditation;
    // 3 - checkbox associate
    private String associateCheckbox;
    // 3 - 27
    private String nameofPreviousRepresentative;
    // 4a - checkbox law graduate
    private String lawGraduateCheckbox;
    // 4b - 30
    private String nameofLawStudentOrLawGraduate;

}
