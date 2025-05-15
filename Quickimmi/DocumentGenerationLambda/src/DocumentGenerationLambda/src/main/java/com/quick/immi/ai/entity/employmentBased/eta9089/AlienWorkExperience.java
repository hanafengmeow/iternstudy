package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlienWorkExperience {
    // Section K - List all jobs the alien has held during the past 3 years

    // Job 1 Information
    private String employerName1;
    private String employerAddress11;
    private String employerAddress12;
    private String employerCity1;
    private String jobTitle1;
    private String startDate1;
    private String jobDetails1;

    // Job 2 Information
    private String employerName2;
    private String employerAddress21;
    private String employerAddress22;
    private String employerCity2;
    private String jobTitle2;
    private String startDate2;
    private String jobDetails2;

    // Job 3 Information
    private String employerName3;
    private String employerAddress31;
    private String employerAddress32;
    private String employerCity3;
    private String jobTitle3;
    private String startDate3;
    private String jobDetails3;
}
