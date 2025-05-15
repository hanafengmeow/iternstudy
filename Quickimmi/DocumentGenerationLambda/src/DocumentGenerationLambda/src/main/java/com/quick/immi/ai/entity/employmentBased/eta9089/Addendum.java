package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Addendum {

    // H.11 Job duties
    private String jobDuties;  // Corresponds to 241

    // H.14 Specific skills or other requirements
    private String specificSkillsOrRequirements;  // Corresponds to 242

    // I.5 Specify additional recruitment information
    private String additionalRecruitmentInformation;  // Corresponds to 243

    // K.9 Job details (Job 1)
    private String jobDetails1;  // Corresponds to 244

    // K. Alien Work Experience (Continued for additional jobs)
    // Job 1
    private String employerName1;  // Corresponds to 256
    private String address1_1;     // Corresponds to 282
    private String address2_1;     // Corresponds to 283
    private String city1;          // Corresponds to 245
    private String jobTitle1;       // Corresponds to 246
    private String startDate1;      // Corresponds to 247
    private String jobDetails1_1;   // Corresponds to 248

    // Job 2
    private String employerName2;  // Corresponds to 249
    private String address1_2;     // Corresponds to 250
    private String address2_2;     // Corresponds to 251
    private String city2;          // Corresponds to 252
    private String jobTitle2;       // Corresponds to 253
    private String startDate2;      // Corresponds to 254
    private String jobDetails2_1;   // Corresponds to 255
}