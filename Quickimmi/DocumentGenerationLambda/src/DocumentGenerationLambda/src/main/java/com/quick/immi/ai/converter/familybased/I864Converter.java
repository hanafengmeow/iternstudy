package com.quick.immi.ai.converter.familybased;

import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.LawyerBasicInfo;
import com.quick.immi.ai.entity.LawyerEligibility;
import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.familyBased.businss.*;
import com.quick.immi.ai.entity.familyBased.i130.Supplement;
import com.quick.immi.ai.entity.familyBased.i864.*;
import com.quick.immi.ai.entity.familyBased.i864.Attorney;
import com.quick.immi.ai.entity.familyBased.i864.EmploymentAndIncome;
import com.quick.immi.ai.entity.familyBased.i864.HouseholdSize;
import com.quick.immi.ai.utils.CopyUtils;
import com.quick.immi.ai.utils.FormFillUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class I864Converter {
    private Properties checkboxProperties;
    private List<Supplement> supplements;

    public I864Converter() {
        checkboxProperties = FormFillUtils.getFormMapping(FormMapping.I130Checkbox);
        supplements = new ArrayList<>();
    }

    public I864Table getI864Table(LawyerProfile lawyerProfile, FamilyBasedCaseProfile familyBasedCaseProfile, int index) {
        I864Table table = new I864Table();
        if (familyBasedCaseProfile.getSponsorList() == null || familyBasedCaseProfile.getSponsorList().isEmpty()) {
            log.warn("sponsorList is null or empty....");
            return table;
        }
        if (index >= familyBasedCaseProfile.getSponsorList().size()) {
            throw new RuntimeException("index shouldn't >= familyBasedCaseProfile.getSponsorList().size()");
        }
        Sponsor sponsor = familyBasedCaseProfile.getSponsorList().get(index);
        if (sponsor == null) {
            sponsor = new Sponsor();
        }
        table.setAttorney(convertAttorney(lawyerProfile));
        table.setSponsorInfo(covertSponsorInfo(sponsor.getBasicInfo()));
        table.setPrincipalImmigrant(covertPrincipalImmigrant(familyBasedCaseProfile));
        table.setHouseholdSize(convertHouseholdSize(sponsor.getHouseholdSize()));
        table.setEmploymentAndIncome(covertEmploymentAndIncome(sponsor.getEmploymentAndIncome()));
        table.setSponsorDetails(convertSponsorDetails(sponsor));
        table.setAssetsSupplementIncome(convertAssetsSupplementIncome(sponsor.getAssetsSupplementIncome()));
        table.setSponsoredImmigrants(convertSponsoredImmigrants());
        table.setInterpreterInfo(convertInterpreterInfo(familyBasedCaseProfile.getInterpreter()));
        table.setPreparerInfo(convertPreparer(lawyerProfile));

        return table;
    }

    private PreparerInfo convertPreparer(LawyerProfile lawyerProfile) {
        PreparerInfo preparerInfo = new PreparerInfo();
        LawyerEligibility eligibility = lawyerProfile.getEligibility();
        LawyerBasicInfo basicInfo = lawyerProfile.getBasicInfo();
        if(basicInfo == null){
            basicInfo = new LawyerBasicInfo();
        }
        if(eligibility == null){
            eligibility = new LawyerEligibility();
        }
        MailAddress mailAddress = new MailAddress();
        if (basicInfo.isAptCheckbox()) {
            mailAddress.setAptCheckbox("");
        }
        if(basicInfo.isFlrCheckbox()){
            mailAddress.setFlrCheckbox("");
        }
        if(basicInfo.isSteCheckbox()){
            mailAddress.setSteCheckbox("");
        }
        mailAddress.setStreetNumberAndName(basicInfo.getStreetNumberAndName());
        mailAddress.setAptSteFlrNumber(basicInfo.getAptSteFlrNumber());
        mailAddress.setState(basicInfo.getStateDropdown());
        mailAddress.setCountry(basicInfo.getCountry());
        mailAddress.setCity(basicInfo.getCity());
        mailAddress.setZipCode(basicInfo.getZipCode());

        preparerInfo.setMailAddress(mailAddress);

        preparerInfo.setFirstName(basicInfo.getFirstName());
        preparerInfo.setLastName(basicInfo.getLastName());
        preparerInfo.setBusinessOrOrganizationName(eligibility.getNameofLawFirm());
        preparerInfo.setIsAttorneyCheckbox("Y");
        preparerInfo.setExtendsBeyondCheckbox("Y");
        preparerInfo.setMailAddress(mailAddress);

        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmailAddress(basicInfo.getEmailAddress());
        contactInformation.setDaytimeTelephoneNumber(basicInfo.getDaytimeTelephoneNumber());
        contactInformation.setMobileTelephoneNumber(basicInfo.getMobileTelephoneNumber());

        preparerInfo.setContactInformation(contactInformation);
        return preparerInfo;
    }

    private InterpreterInfo convertInterpreterInfo(Interpreter interpreter) {
        if (interpreter == null) {
            InterpreterInfo source = new InterpreterInfo();
            InterpreterInfo target = new InterpreterInfo();
            CopyUtils.copy(source, target, checkboxProperties, "");
            target.setInterpreterSignature("");
            target.setDateOfSignature("");
            return target;
        }

        InterpreterInfo interpreterInfo = new InterpreterInfo();
        interpreterInfo.setFirstName(interpreter.getFirstName());
        interpreterInfo.setLastName(interpreter.getLastName());
        interpreterInfo.setBusinessOrOrganizationName(interpreter.getBusinessName());
        interpreterInfo.setFluentLanguage(interpreter.getFluentLanguage());

        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmailAddress(interpreter.getEmailAddress());
        contactInformation.setDaytimeTelephoneNumber(interpreter.getDaytimeTelephoneNumber());
        contactInformation.setMobileTelephoneNumber(interpreter.getMobileTelephoneNumber());

        interpreterInfo.setContactInformation(contactInformation);

        MailAddress mailAddress = new MailAddress();
        if (interpreter.isAptCheckbox()) {
            mailAddress.setAptCheckbox("");
        }
        if(interpreter.isFlrCheckbox()){
            mailAddress.setFlrCheckbox("");
        }
        if(interpreter.isSteCheckbox()){
            mailAddress.setSteCheckbox("");
        }
        mailAddress.setStreetNumberAndName(interpreter.getStreetNumberAndName());
        mailAddress.setAptSteFlrNumber(interpreter.getAptSteFlrNumber());
        mailAddress.setState(interpreter.getState());
        mailAddress.setCountry(interpreter.getCountry());
        mailAddress.setCity(interpreter.getCity());
        mailAddress.setZipCode(interpreter.getZipCode());

        interpreterInfo.setMailAddress(mailAddress);

        return interpreterInfo;
    }
    private SponsoredImmigrants convertSponsoredImmigrants() {
        SponsoredImmigrants sponsoredImmigrants = new SponsoredImmigrants();

        return sponsoredImmigrants;
    }

    private AssetsSupplementIncome convertAssetsSupplementIncome(Sponsor.AssetsSupplementIncome assetsSupplementIncome) {
        if (assetsSupplementIncome == null) {
            assetsSupplementIncome = new Sponsor.AssetsSupplementIncome();
        }
        AssetsSupplementIncome targetAssetsSupplementIncome = new AssetsSupplementIncome();

        CopyUtils.copy(assetsSupplementIncome, targetAssetsSupplementIncome, checkboxProperties, "");
        return targetAssetsSupplementIncome;
    }

    private SponsorDetails convertSponsorDetails(Sponsor sponsor) {
        SponsorDetails sponsorDetails = new SponsorDetails();
        Sponsor.SponsorDetails sourceSponsorDetails = sponsor.getSponsorDetails();
        if (sourceSponsorDetails == null) {
            sourceSponsorDetails = new Sponsor.SponsorDetails();
        }

        sponsorDetails.setFirstName(sourceSponsorDetails.getFirstName());
        sponsorDetails.setLastName(sourceSponsorDetails.getLastName());
        sponsorDetails.setMiddleName(sourceSponsorDetails.getMiddleName());

        MailAddress mailAddress = new MailAddress();
        Address mailAddressSource = sourceSponsorDetails.getMailAddress();
        if (mailAddressSource == null) {
            mailAddressSource = new Address();
        }
        mailAddress.setInCareOfName(mailAddressSource.getInCareOf());
        mailAddress.setStreetNumberAndName(mailAddressSource.getStreetNumberAndName());
        if (mailAddressSource.isAptCheckbox()) {
            mailAddress.setAptCheckbox(checkboxProperties.getProperty("sponsorDetails.aptCheckbox"));
        }
        if (mailAddressSource.isFlrCheckbox()) {
            mailAddress.setFlrCheckbox(checkboxProperties.getProperty("sponsorDetails.steCheckbox"));
        }
        if (mailAddressSource.isSteCheckbox()) {
            mailAddress.setAptCheckbox("sponsorDetails.flrCheckbox");
        }
        mailAddress.setAptSteFlrNumber(mailAddressSource.getAptSteFlrNumber());
        mailAddress.setCity(mailAddressSource.getCityOrTown());
        mailAddress.setZipCode(mailAddressSource.getZipCode());
        mailAddress.setProvince(mailAddressSource.getProvince());
        mailAddress.setPostalCode(mailAddressSource.getPostalCode());
        mailAddress.setCountry(mailAddressSource.getCountry());

        sponsorDetails.setMailAddress(mailAddress);

        String currentCountry = "";

        if (sourceSponsorDetails.isMailingAddressSameAsPhysicalNoCheckbox()) {
            sponsorDetails.setMailingAddressSameAsPhysicalNoCheckbox("N");
            MailAddress physicAddress = new MailAddress();

            Address physicalAddressSource = sourceSponsorDetails.getPhysicalAddress();
            if (physicalAddressSource == null) {
                physicalAddressSource = new Address();
            }
            CopyUtils.copy(physicalAddressSource, physicAddress, checkboxProperties, "physicalAptCheckbox");
            currentCountry = physicalAddressSource.getCountry();
            sponsorDetails.setPhysicalAddress(physicAddress);
        }
        if (sourceSponsorDetails.isMailingAddressSameAsPhysicalYesCheckbox()) {
            sponsorDetails.setMailingAddressSameAsPhysicalYesCheckbox("");
        }
        sponsorDetails.setCountryOfDomicile(currentCountry);
        sponsorDetails.setDateOfBirth(sponsorDetails.getDateOfBirth());
        sponsorDetails.setCityOfBirth(sponsorDetails.getCityOfBirth());

        sponsorDetails.setStateOrProvinceOfBirth(sponsorDetails.getCityOfBirth());
        sponsorDetails.setStateOrProvinceOfBirth(sponsorDetails.getStateOrProvinceOfBirth());
        sponsorDetails.setCountryOfBirth(sponsorDetails.getCountryOfBirth());
        sponsorDetails.setSsn(sponsorDetails.getSsn());
        if (sourceSponsorDetails.isUsCitizenCheckbox()) {
            sponsorDetails.setIsUsCitizenCheckbox("");
        }
        if (sourceSponsorDetails.isLawfulPermanentResidentCheckbox()) {
            sponsorDetails.setIsLawfulPermanentResidentCheckbox("");
        }


//        // 11.b - I am a U.S. national (Checkbox)
//        private String isUsNationalCheckbox;

        return sponsorDetails;
    }

    private EmploymentAndIncome covertEmploymentAndIncome(Sponsor.EmploymentAndIncome employmentAndIncome) {
        if (employmentAndIncome == null) {
            employmentAndIncome = new Sponsor.EmploymentAndIncome();
        }
        EmploymentAndIncome targetEmploymentAndIncome = new EmploymentAndIncome();
        CopyUtils.copy(employmentAndIncome, targetEmploymentAndIncome, checkboxProperties, "employmentAndIncome");

        List<Sponsor.IncomeFromOtherHouseholdMember> incomeFromOtherHouseholdMember = employmentAndIncome.getIncomeFromOtherHouseholdMember();

        List<IncomeFromOtherHouseholdMember> targetIncomeFromOtherHouseholdMember = new ArrayList<>();

        if (incomeFromOtherHouseholdMember != null) {
            for (int i = 0; i < incomeFromOtherHouseholdMember.size() && i < 4; i++) {
                Sponsor.IncomeFromOtherHouseholdMember sourceIncomeFromOther = incomeFromOtherHouseholdMember.get(i);
                IncomeFromOtherHouseholdMember target = new IncomeFromOtherHouseholdMember();
                CopyUtils.copy(sourceIncomeFromOther, target, checkboxProperties, "");
                targetIncomeFromOtherHouseholdMember.add(target);
            }
        }

        targetEmploymentAndIncome.setIncomeFromOtherHouseholdMember(targetIncomeFromOtherHouseholdMember);
        return targetEmploymentAndIncome;
    }

    private HouseholdSize convertHouseholdSize(com.quick.immi.ai.entity.familyBased.businss.HouseholdSize householdSize) {
        if (householdSize == null) {
            householdSize = new com.quick.immi.ai.entity.familyBased.businss.HouseholdSize();
        }
        HouseholdSize targetHouseholdSize = new HouseholdSize();
        CopyUtils.copy(householdSize, targetHouseholdSize, checkboxProperties, "");
        return targetHouseholdSize;
    }

    private PrincipalImmigrant covertPrincipalImmigrant(FamilyBasedCaseProfile familyBasedCaseProfile) {
        PrincipalImmigrant principalImmigrant = new PrincipalImmigrant();

        Beneficiary beneficiary = familyBasedCaseProfile.getBeneficiary();
        if (beneficiary == null) {
            beneficiary = new Beneficiary();
        }

        principalImmigrant.setLastName(beneficiary.getLastName());
        principalImmigrant.setFirstName(beneficiary.getFirstName());
        principalImmigrant.setMiddleName(beneficiary.getMiddleName());
        MailAddress targetMailAddress = new MailAddress();
        Address mailingAddress = beneficiary.getMailingAddress();

        if (mailingAddress == null) {
            mailingAddress = new Address();
        }
        CopyUtils.copy(mailingAddress, targetMailAddress, checkboxProperties, "mailAddress");

        principalImmigrant.setMailAddress(targetMailAddress);
        // 124 - 129

        principalImmigrant.setCountryOfCitizenship(beneficiary.getPassportIssuingCountry());
        principalImmigrant.setDateOfBirth(beneficiary.getCityOfBirth());
        principalImmigrant.setAlienNumber(beneficiary.getAlienNumber());
        principalImmigrant.setUSCISOnlineAccountNumber(beneficiary.getUSCISOnlineAccountNumber());
        principalImmigrant.setDaytimeTelephoneNumber(beneficiary.getDaytimePhoneNumber());

        return principalImmigrant;
    }

    private SponsorInfo covertSponsorInfo(Sponsor.BasicInfo basicInfo) {
        if (basicInfo == null) {
            basicInfo = new Sponsor.BasicInfo();
        }
        SponsorInfo sponsorInfo = new SponsorInfo();
        CopyUtils.copy(basicInfo, sponsorInfo, checkboxProperties, "sponsorInfo");

        return sponsorInfo;
    }

    private Attorney convertAttorney(LawyerProfile lawyerProfile) {
        Attorney attorney = new Attorney();
        attorney.setG28AttachedCheckbox("");
        attorney.setUSCISOnlineAccountNumber(lawyerProfile.getBasicInfo().getUscisOnlineAccountNumber());
        attorney.setStateBarNumber(lawyerProfile.getEligibility().getBarNumber());
        return attorney;
    }
}
