package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recruitment {
    // Section I.a - Occupation type: professional occupation, other than a college or university teacher
    private String isProfessionalOccupation;

    // Section I.a.2 - Is this application for a college or university teacher?
    private String isCollegeUniversityTeacher;

    // Section I.a.2-A - Did you select the candidate using a competitive recruitment and selection process?
    private String usedCompetitiveSelection;

    // Section I.a.2-B - Did you use the basic recruitment process for professional occupations?
    private String usedBasicRecruitmentProcess;

    // Section I.b - Date alien selected
    private String dateAlienSelected;

    // Section I.b - Name and date of national professional journal in which advertisement was placed
    private String journalAdDetails;

    // Section I.b - Specify additional recruitment information in this space
    private String additionalRecruitmentInformation;

    // Section I.c - Start date for the SWA job order
    private String swaJobOrderStartDate;

    // Section I.c - Is there a Sunday edition of the newspaper in the area of intended employment?
    private String isSundayEditionAvailable;

    // Section I.c - Name of newspaper (of general circulation) in which the first advertisement was placed
    private String firstAdvertisementNewspaperName;

    // Section I.c - Date of first advertisement
    private String firstAdvertisementDate;

    private String newspaperOrJournal;

    // Section I.c - Name of newspaper or professional journal (if applicable) in which second advertisement was placed
    private String secondAdvertisementNewspaperName;

    // Section I.c - Date of second newspaper advertisement or date of publication of journal
    private String secondAdvertisementDate;

    private String datesAdvertisedAtJobFairFrom;

    private String datesAdvertisedAtJobFairTo;

    private String datesOnCampusRecruitingFrom;

    private String datesOnCampusRecruitingTo;

    private String datesPostedOnEmployerWebsiteFrom;

    private String datesPostedOnEmployerWebsiteTo;

    private String datesListedWithJobSearchWebsiteFrom;

    private String datesListedWithJobSearchWebsiteTo;

    private String datesAdvertisedWithEmployeeReferralProgramFrom;

    private String datesAdvertisedWithEmployeeReferralProgramTo;

    private String datesAdvertisedWithLocalOrEthnicNewspaperFrom;

    private String datesAdvertisedWithLocalOrEthnicNewspaperTo;

    private String datesAdvertisedWithTradeOrProfessionalOrganizationFrom;

    private String datesAdvertisedWithTradeOrProfessionalOrganizationTo;

    private String datesListedWithPrivateEmploymentFirmFrom;

    private String datesListedWithPrivateEmploymentFirmTo;

    private String datesAdvertisedWithCampusPlacementOfficeFrom;

    private String datesAdvertisedWithCampusPlacementOfficeTo;

    private String datesAdvertisedWithRadioOrTvAdsFrom;

    private String datesAdvertisedWithRadioOrTvAdsTo;

    private String hasEmployerReceivedPaymentForSubmission;

    private String detailsOfPayment;

    private String bargainingRepresentativeNotified;

    private String noticePostedInWorkplace;

    private String employerLayoffOccurred;

    private String laidOffWorkersNotifiedAndConsidered;
}
