package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobOpportunity {
    // Section H1 - Primary worksite (where work is to be performed) address 1
    private String primaryWorksiteAddress1;

    // Section H1 - Primary worksite (where work is to be performed) address 2 (if any)
    private String primaryWorksiteAddress2;

    // Section H2 - City
    private String city;

    // Section H3 - Job title
    private String jobTitle;

    // Section H4 - Education: minimum level required
    private String minimumEducationRequired;

    // Section H4-A - If "Other" is indicated in question 4, specify the education required
    private String otherEducationRequired;

    // Section H4-B - Major field of study
    private String majorFieldOfStudy;

    // Section H5 - Is training required for the job opportunity?
    private String isTrainingRequired;

    // Section H5-A - If Yes, number of months of training required
    private String monthsOfTrainingRequired;

    // Section H5-B - Indicate the field of training:
    private String fieldOfTraining;

    // Section H6 - Is experience in the job offered required for the job?
    private String isExperienceRequired;

    // Section H6-A - If Yes, number of months experience required
    private String monthsExperienceRequired;

    // Section H7 - Is there an alternate field of study that is acceptable?
    private String isAlternateFieldOfStudyAcceptable;

    // Section H7-A - If Yes, specify the major field of study
    private String alternateFieldOfStudy;

    // Section H8 - Is there an alternate combination of education and experience that is acceptable?
    private String isAlternateCombinationAcceptable;

    // Section H8-A - If Yes, specify the alternate level of education required
    private String alternateEducationRequired;

    // Section H8-B - If Other is indicated in question 8-A, indicate the alternate level of education required
    private String alternateLevelOfEducation;

    // Section H8-C - If applicable, indicate the number of years experience acceptable in question 8
    private String yearsExperienceAcceptable;

    // Section H9 - Is a foreign educational equivalent acceptable?
    private String isForeignEducationalEquivalentAcceptable;

    // Section H10 - Is experience in an alternate occupation acceptable?
    private String isAlternateOccupationExperienceAcceptable;

    // Section H10-A - If Yes, number of months experience in alternate occupation required
    private String monthsExperienceInAlternateOccupationRequired;

    // Section H10-B - Identify the job title of the acceptable alternate occupation
    private String alternateOccupationJobTitle;

    // Section H11 - Job duties
    private String jobDuties;

    // Section H12 - Are the job opportunity's requirements normal for the occupation?
    private String areRequirementsNormal;

    // Section H13 - Is knowledge of a foreign language required to perform the job duties?
    private String isForeignLanguageRequired;

    // Section H14 - Specific skills or other requirements
    private String specificSkillsOrOtherRequirements;

    private String involvesJobCombination;

    private String positionOfferedToAlien;

    private String requiresAlienToLiveOnPremises;

    private String isLiveInHouseholdWorker;
    
    private String hasEmployerProvidedContract;
}