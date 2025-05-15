package com.quick.immi.ai.entity.employmentBased.i526;

import com.quick.immi.ai.entity.Address;
import com.quick.immi.ai.entity.common.FamilyMember;
import com.quick.immi.ai.entity.common.LastArrival;
import com.quick.immi.ai.entity.common.EmploymentHistory;
import com.quick.immi.ai.entity.common.NameEntity;
import lombok.Data;

import java.util.List;

@Data
public class I526Table{
    private BasicInfo basicInfo;
    private String hasBeenEmployedCheckboxYes;
    private String hasBeenEmployedCheckboxNo;
    private List<EmploymentHistory> employmentHistories;
    private LastArrival lastArrival;
    private List<FamilyMember> familyMembers;
    private NewCommercialEnterpriseInfo newCommercialEnterpriseInfo;
    private EmploymentCreationInformation employmentCreationInformation;
    private VisaProcessingAndImmigrationProceeding visaProcessingAndImmigrationProceeding;
    private PetitionerStatement petitionerStatement;
    private PrepareInfo preparerInfo;
    private List<Supplement> supplements;

    @Data
    public static class BasicInfo {
        private String alienNumber;
        private String uSCISOnlineAccountNumber;
        private String ssn;
        private NameEntity currentName;
        private List<NameEntity> otherUsedNames;
        private String dateOfBirth;
        private String maleCheckbox;
        private String femaleCheckbox;
        private Address mailingAddress;
        private String mailingAddressSameAsPhysicalAddressYesCheckbox;
        private String mailingAddressSameAsPhysicalAddressNoCheckbox;
        private List<Address> lastFiveYearsPhysicalAddress;

    }

    @Data
    public static class NewCommercialEnterpriseInfo {
        private String legalName;
        private String otherNames;

        private String corporationCheckbox;
        private String partnershipCheckbox;
        private String limitedLiabilityCompanyCheckbox;
        private String otherCheckbox;
        private String otherDesc;
        private String holdingCompanyYesCheckbox;
        private String holdingCompanyNoCheckbox;

        private List<OwnedSubsidiary> ownedSubsidiaries;
        private String dateNECFormed;
        private String necEstablishedStat;
        private String otherRegisteredState;
        private String federalEmployerId;
        private String nceMailingAddressSameAsPhysicalAddressYesCheckbox;
        private Address nceMailingAddress;
        private Address ncePhysicalAddress;
        private String nceTelephoneNumber;
        private String nceEmailAddress;
        private String nceWebsiteAddress;
        private AddressAndCensusTract addressAndCensusTract;
        private String nceFormedAfterNov291990;
        private String nceRestingFromPurchaseBeforeNov291990;
        private String nceRestingFromCapitalInvestingBeforeNov291990;
        private String investingTroubledBusinessYesCheckbox;
        private String investingTroubledBusinessNoCheckbox;

        //NCE owner info
        private String percentageOfNCE;
        private String totalAmountOfCapitalInvestedNCE;
        private List<OwnershipAndCapitalInvestment> ownershipAndCapitalInvestments;

        private String submittedRequiredInitialEvidenceCheckbox;
        private String willSubmittedRequiredInitialEvidenceViaUSCISCheckbox;

        private String ruralAreaCheckbox;
        private String ruralAYesCheckbox;
        private String ruralANoCheckbox;

        private String ruralBYesCheckbox;
        private String ruralBNoCheckbox;


        private String highUnemploymentAreaCheckbox;
        private String highUnemploymentAreaACheckbox;
        private String highUnemploymentAreaBCheckbox;
        private String highUnemploymentAreaCCheckbox;
        private String highUnemploymentAreaDCheckbox;

        private String highEmploymentAreaCheckbox;
        private String NonTEAAndNonHighEmploymentCheckbox;
        private List<InvestmentHistory> investmentHistoriesForNCE;

        private String compositionOfInvestment6;
        private String compositionOfInvestment7;
        private String compositionOfInvestment8;
        private String compositionOfInvestment9;
        private String compositionOfInvestment10;
        private String compositionOfInvestment11;

        //12. Enter the date and amount of all administrative costs and fees associated with your investment.
        private List<InvestmentHistory> administrativeCostAndFee;
        private String currentNetWorth;

        private SourceOfInvestmentCapital sourceOfInvestmentCapital;

    }

    @Data
    public static class PetitionerStatement{
        private String canReadAndUnderstandEnglishCheckbox;
        private String interpreterReadCheckbox;
        private String language;

        private String preparerCheckbox;
        private String preparerName;

        private String petitionerDailyPhoneNumber;
        private String petitionerMobilePhoneNumber;
        private String petitionerEmail;
    }

    @Data
    public static class InterpreterContact{
        private String firstName;
        private String middleName;
        private String lastName;
        private Address mailingAddress;
        private String interpreterDailyPhoneNumber;
        private String interpreterMobilePhoneNumber;
        private String interpreterEmail;
        private String fluentLanguageOtherThanEnglish;
    }

    @Data
    public static class PrepareInfo {
        private String lastName;
        private String firstName;
        private String businessOrOrganizationName;
        private Address mailingAddress;

        private String daytimeTelephoneNumber;
        private String mobileTelephoneNumber;
        private String emailAddress;

        private String preparerNotAttorneyYesCheckbox;

        private String preparerIsAttorneyYesCheckbox;
        private String preparerRepresentationExtendsCheckbox;
        private String preparerRepresentationDoesNotExtendCheckbox;
    }
    @Data
    public static class VisaProcessingAndImmigrationProceeding {
        private String immigrantVisaProcessingCheckbox;
        private String applicationForAdjustmentStatusCheckbox;

        private String countryOfCitizenship;
        private String countryOfLastPermanentResidenceAbroad;
        private String countryOfCurrentResidence;

        private Address addressOfLastPermanentResidenceAbroad;
        private String phoneNumberOfLastPermanentResidenceAbroad;
        private Address addressOfLastPermanentResidenceAbroadInNative;


        private String filingAnyOtherPetitionsYesCheckbox;
        private String filingAnyOtherPetitionsNoCheckbox;
        private String formI485Checkbox;
        private String formI131Checkbox;
        private String formI765Checkbox;
        private String formOtherCheckbox;

        private String inImmigrationProceedingsYesCheckbox;
        private String inImmigrationProceedingsNoCheckbox;
        private String exclusionCheckbox;
        private String deportationCheckbox;
        private String removalCheckbox;
        private String cityOfProceedings;
        private String stateOfProceedings;

        private String beenSubjectToFinalOrderYesCheckbox;
        private String beenSubjectToFinalOrderNoCheckbox;


        private String workedInUSAYesCheckbox;
        private String workedInUSANoCheckbox;

        private String workedInUSAYesCheckboxExplain;
    }
    @Data
    public static class SourceOfInvestmentCapital {
        private String aCheckbox;
        private String bCheckbox;
        private String cCheckbox;
        private String dCheckbox;
        private String eCheckbox;
        private String fCheckbox;
        private String gCheckbox;
        private String hCheckbox;
        private String q15Content;
        private String q16Content;
        private String q17Content;
    }


    @Data
    public static class EmploymentCreationInformation {
        private String employedByNCEYesCheckbox;
        private String employedByNCENoCheckbox;

        private String employedByNCEAContent;
        private String employedByNCEBContent;

        private String q2Content;
        private String q3Content;
        private String q4Content;
        private String q5Content;
        private String q6Content;
    }


    @Data
    public static class InvestmentHistory {
        private String date;
        private String amount;
    }

    @Data
    public static class OwnershipAndCapitalInvestment {
        private String nameOfPerson;
        private String percentage;
        private String amountOfCapital;
    }

    @Data
    public static class AddressAndCensusTract {
        private String streetNumberAndName;
        private String aptCheckbox;
        private String steCheckbox;
        private String flrCheckbox;
        private String aptSteFlrNumber;
        private String city;
        private String state;
        private String zipCode;

        private String censusTrace;
        private String natureOfActivity;
        private String includedIndustries;
    }

    @Data
    public static class OwnedSubsidiary {
        private String name;
        private String dateOfFormation;
        private String jurisdictionOfFormation;
    }

}
