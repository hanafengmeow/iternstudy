package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlienInformation {
    // Section J1 - Alien’s last name
    private String lastName;

    // Section J2 - Current address 1
    private String currentAddress1;

    // Section J2 - Current address 2 (if any)
    private String currentAddress2;

    // Section J3 - City
    private String city;

    // Section J4 - Phone number of current residence
    private String phoneNumber;

    // Section J5 - Country of citizenship
    private String countryOfCitizenship;

    // Section J7 - Alien’s date of birth
    private String dateOfBirth;

    // Section J9 - Alien registration number (A#)
    private String alienRegistrationNumber;

    // Section J11 - Education: highest level achieved as required by the requested job opportunity
    private String highestEducationLevel;

    // Section J11-A - If Other indicated in question 11, specify
    private String otherEducationDetails;

    // Section J12 - Specify major field(s) of study
    private String majorFieldsOfStudy;

    // Section J13 - Year relevant education completed
    private String yearEducationCompleted;

    // Section J14 - Institution where relevant education specified in question 11 was received
    private String educationInstitutionName;

    // Section J15 - Address 1 of conferring institution
    private String institutionAddress1;

    // Section J15 - Address 2 of conferring institution (if any)
    private String institutionAddress2;

    // Section J16 - City of conferring institution
    private String institutionCity;

    private String didCompleteTrainingRequired;

    private String hasRequiredExperience;

    private String hasAlternateEducationExperienceCombination;

    private String hasExperienceInAlternateOccupation;

    private String gainedQualifyingExperienceWithEmployer;

    private String employerPaidForEducationOrTraining;
    
    private String currentlyEmployedByPetitioningEmployer;
}