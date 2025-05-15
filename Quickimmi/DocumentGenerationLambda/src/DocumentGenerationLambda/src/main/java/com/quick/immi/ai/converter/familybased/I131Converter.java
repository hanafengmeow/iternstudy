package com.quick.immi.ai.converter.familybased;

import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.familyBased.businss.Address;
import com.quick.immi.ai.entity.familyBased.businss.*;
import com.quick.immi.ai.entity.i131.I131Table;
import com.quick.immi.ai.utils.CopyUtils;
import com.quick.immi.ai.utils.FormFillUtils;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class I131Converter {
    private static final Logger log = Logger.getLogger(I131Converter.class.getName());

    private Properties checkboxProperties;

    public I131Converter() {
        checkboxProperties = FormFillUtils.getFormMapping(FormMapping.I131Checkbox);
    }

    public I131Table getI131Table(LawyerProfile lawyerProfile, FamilyBasedCaseProfile familyBasedCaseProfile) {
        try {
            I131Table i131Table = new I131Table();
            Beneficiary beneficiary =familyBasedCaseProfile.getBeneficiary();
            if(beneficiary == null){
                beneficiary = new Beneficiary();
            }
            i131Table.setAttorney(convertAttorney(lawyerProfile));
            log.info("i131Converter----convertAttorney successfully...");
            i131Table.setApplicant(convertApplicant(beneficiary));
            log.info("i131Converter----convertApplicant successfully...");
            i131Table.setApplicationType(convertApplicationType());
            log.info("i131Converter----convertApplicationType successfully...");
            i131Table.setProcessingInfo(convertProcessingInfo());
            log.info("i131Converter----convertProcessingInfo successfully...");
            i131Table.setTravel(convertTravel());
            log.info("i131Converter----convertTravel successfully...");
            i131Table.setApplyReentryPermit(convertApplyReentryPermit());
            log.info("i131Converter----convertApplyReentryPermit successfully...");
            i131Table.setApplyRefugeeTravel(convertApplyRefugeeTravel());
            log.info("i131Converter----convertApplyRefugeeTravel successfully...");
            i131Table.setApplyAdvanceParole(convertApplyAdvanceParole());
            log.info("i131Converter----convertApplyAdvanceParole successfully...");
            i131Table.setEmploymentAuthorization(convertEmploymentAuthorization());
            log.info("i131Converter----convertEmploymentAuthorization successfully...");
            i131Table.setSignature(convertSignature());
            log.info("i131Converter----convertSignature successfully...");
            i131Table.setPreparer(convertPreparer(lawyerProfile));
            log.info("i131Converter----convertPreparer successfully...");
            return i131Table;
        } catch (Exception exception){
            log.log(Level.SEVERE, "an exception was thrown whe getI131Table", exception);
            throw new RuntimeException(exception);
        }

    }

    public I131Table.Attorney convertAttorney(LawyerProfile lawyerProfile) {
        I131Table.Attorney targetAttorney = new I131Table.Attorney();

        LawyerEligibility eligibility = lawyerProfile.getEligibility();
        if (eligibility != null) {
            targetAttorney.setAttorneyStateLicenseNumber(convertToNA(eligibility.getBarNumber()));
        }
        return targetAttorney;
    }

    // Convert Applicant
    public I131Table.Applicant convertApplicant(Beneficiary beneficiary) {
        I131Table.Applicant targetApplicant = new I131Table.Applicant();
        targetApplicant.setFirstName(convertToNA(beneficiary.getFirstName()));
        targetApplicant.setLastName(convertToNA(beneficiary.getLastName()));
        targetApplicant.setMiddleName(convertToNA(beneficiary.getMiddleName()));
        targetApplicant.setAlienNumber(convertToNA(beneficiary.getAlienNumber()));
        targetApplicant.setCountryOfBirth(convertToNA(beneficiary.getCountryOfBirth()));
        targetApplicant.setCountryOfCitizenship(convertToNA(beneficiary.getPassportIssuingCountry()));
        if(beneficiary.getLastArrivalInformation() != null){
            targetApplicant.setClassOfAdmission(convertToNA(beneficiary.getLastArrivalInformation().getArrivedAsAdmission()));
        }
        if (beneficiary.isSexMaleCheckbox()) {
            targetApplicant.setMaleCheckbox(checkboxProperties.getProperty("applicant.maleCheckbox"));
        }
        if (beneficiary.isSexFemaleCheckbox()) {
            targetApplicant.setFemaleCheckbox(checkboxProperties.getProperty("applicant.femaleCheckbox"));
        }
        targetApplicant.setDateOfBirth(convertToNA(beneficiary.getDateOfBirth()));
        targetApplicant.setSsn(convertToNA(beneficiary.getSsn()));
        targetApplicant.setPhysicalAddress(convertPhysicalAddress(beneficiary.getPhysicalAddress())); // Converting Address as well

        return targetApplicant;
    }

    private String convertToNA(String value) {
        if (value == null || value.isEmpty()) {
            return "N/A";
        } else {
            return value;
        }
    }

    // Convert ApplicationType
    public I131Table.ApplicationType convertApplicationType() {
        I131Table.ApplicationType targetApplicationType = new I131Table.ApplicationType();
        targetApplicationType.setAdvanceParoleReentryCheckbox("Y");

        return targetApplicationType;
    }

    // Convert ProcessingInfo
    public I131Table.ProcessingInfo convertProcessingInfo() {
        I131Table.ProcessingInfo targetProcessingInfor = new I131Table.ProcessingInfo();
        //targetProcessingInfor.setIntendedDepartureDate();
        targetProcessingInfor.setExpectedLengthOfTrip("30");
        targetProcessingInfor.setInExclusionProceedingsNoCheckbox(checkboxProperties.getProperty("processingInfo.inExclusionProceedingsNoCheckbox"));
        targetProcessingInfor.setPreviousReentryPermitNoCheckbox(checkboxProperties.getProperty("processingInfo.previousReentryPermitNoCheckbox"));
        targetProcessingInfor.setSendDocumentToUSAddressCheckbox(checkboxProperties.getProperty("processingInfo.sendDocumentToUSAddressCheckbox"));

        return targetProcessingInfor;
    }

    // Convert Travel
    public I131Table.Travel convertTravel() {
        I131Table.Travel targetTravel = new I131Table.Travel();

        //targetTravel.setListTravelCountries(null);
        //targetTravel.setTravelPurpose(null);
        return targetTravel;
    }

    // Convert ApplyReentryPermit
    public I131Table.ApplyReentryPermit convertApplyReentryPermit() {
        I131Table.ApplyReentryPermit targApplyReentryPermit = new I131Table.ApplyReentryPermit();

        return targApplyReentryPermit;
    }

    // Convert ApplyRefugeeTravel
    public I131Table.ApplyRefugeeTravel convertApplyRefugeeTravel() {
        I131Table.ApplyRefugeeTravel targetRefugeeTravel = new I131Table.ApplyRefugeeTravel();

        return targetRefugeeTravel;
    }

    // Convert ApplyAdvanceParole
    public I131Table.ApplyAdvanceParole convertApplyAdvanceParole() {
        I131Table.ApplyAdvanceParole targetApplyAdvanceParole = new I131Table.ApplyAdvanceParole();
        targetApplyAdvanceParole.setMoreThanOneTripCheckbox(checkboxProperties.getProperty("applyAdvanceParole.moreThanOneTripCheckbox"));
        return targetApplyAdvanceParole;
    }

    // Convert EmploymentAuthorization
    public I131Table.EmploymentAuthorization convertEmploymentAuthorization() {
        I131Table.EmploymentAuthorization targetEmploymentAuthorization = new I131Table.EmploymentAuthorization();

        return targetEmploymentAuthorization;
    }

    // Convert Signature
    public I131Table.Signature convertSignature() {
        I131Table.Signature targetSignature = new I131Table.Signature();

        return targetSignature;
    }

    // Convert Preparer
    public I131Table.Preparer convertPreparer(LawyerProfile lawyerProfile) {
        I131Table.Preparer targetPreparer = new I131Table.Preparer();

        LawyerBasicInfo basicInfo = lawyerProfile.getBasicInfo();
        LawyerEligibility eligibility = lawyerProfile.getEligibility();

        if (basicInfo == null) {
            basicInfo = new LawyerBasicInfo();
        }
        if(eligibility == null){
            eligibility = new LawyerEligibility();
        }

        targetPreparer.setLastName(convertToNA(basicInfo.getLastName()));
        targetPreparer.setFirstName(convertToNA(basicInfo.getFirstName()));
        targetPreparer.setBusinessOrOrganizationName(convertToNA(eligibility.getNameofLawFirm()));
        log.info("i131Converter----setBusinessOrOrganizationName successfully...");
        targetPreparer.setMailingAddress(convertAddressForPreparer(basicInfo)); // Converting Address
        log.info("i131Converter----convertAddressForPreparer successfully...");
        targetPreparer.setDaytimePhoneNumber(convertPhoneNumber(basicInfo.getDaytimeTelephoneNumber()));
        log.info("i131Converter----convertPhoneNumber successfully...");

        targetPreparer.setEmailAddress(convertToNA(basicInfo.getEmailAddress()));

        return targetPreparer;
    }


    public I131Table.Address convertPhysicalAddress(Address address) {
        if(address == null){
            address = new Address();
        }
        I131Table.Address targetAddress = new I131Table.Address();
        System.out.println("cityOrTown from data: " + address.getCityOrTown());
        CopyUtils.copy(address, targetAddress, checkboxProperties, "preparer.mailingAddress");
        System.out.println("cityOrTown after setting: " + targetAddress.getCityOrTown());
        return targetAddress;
    }

    public I131Table.Address convertAddressForPreparer(LawyerBasicInfo basicInfo){
        I131Table.Address targetAddress = new I131Table.Address();
        if(basicInfo == null){
            basicInfo = new LawyerBasicInfo();
        }
        targetAddress.setStreetNumberAndName(convertToNA(basicInfo.getStreetNumberAndName()));
        if (basicInfo.isAptCheckbox()) {
            targetAddress.setAptCheckbox(checkboxProperties.getProperty("preparer.mailingAddress.aptCheckbox"));
        }
        if (basicInfo.isFlrCheckbox()) {
            targetAddress.setAptCheckbox(checkboxProperties.getProperty("preparer.mailingAddress.flrCheckbox"));
        }
        if (basicInfo.isSteCheckbox()) {
            targetAddress.setSteCheckbox(checkboxProperties.getProperty("preparer.mailingAddress.steCheckbox"));
        }
        targetAddress.setAptSteFlrNumber(convertToNA(basicInfo.getAptSteFlrNumber()));
        targetAddress.setCityOrTown(convertToNA(basicInfo.getCity()));
        targetAddress.setState(convertToNA(basicInfo.getStateDropdown()));
        targetAddress.setZipCode(convertToNA(basicInfo.getZipCode()));
        targetAddress.setProvince(convertToNA(basicInfo.getProvince()));
        targetAddress.setPostalCode(convertToNA(basicInfo.getPostalCode()));
        targetAddress.setCountry(convertToNA(basicInfo.getCountry()));

        return targetAddress;
    }

    public I131Table.PhoneNumber convertPhoneNumber(String phoneNumber) {
        I131Table.PhoneNumber targetPhoneNumber = new I131Table.PhoneNumber();
        if(phoneNumber != null){
            targetPhoneNumber.setNumber1to3(phoneNumber.substring(0, 3));
            targetPhoneNumber.setNumber4to6(phoneNumber.substring(3, 6));
            targetPhoneNumber.setNumber7to10(phoneNumber.substring(6, 10));
        }
        return targetPhoneNumber;
    }
}