package com.quick.immi.ai.converter.asylum;

import com.amazonaws.util.StringUtils;
import com.quick.immi.ai.converter.common.I765TableUtils;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.asylum.business.*;
import com.quick.immi.ai.entity.common.NameEntity;
import com.quick.immi.ai.entity.i765.I765Table;
import com.quick.immi.ai.utils.FormFillUtils;
import crawlercommons.utils.Strings;

import java.util.Properties;

public class I765TableConverter {
    private Properties checkboxProperties;

    public I765TableConverter() {
        checkboxProperties = FormFillUtils.getFormMapping(FormMapping.I765Checkbox);
    }

    public I765Table getTable(ApplicationCase applicationCase, LawyerProfile lawyerProfile, AsylumCaseProfile asylumCaseProfile){
        I765Table i765Table = new I765Table();

        LawyerBasicInfo basicInfo = lawyerProfile.getBasicInfo();

        i765Table.setAttorneyUSCISOnlineAccountNumber(basicInfo.getUscisOnlineAccountNumber());
        i765Table.setG28AttachedCheckbox(checkboxProperties.get("g28AttachedCheckbox").toString());

        I765Table.ApplyingReason applyingReason = new I765Table.ApplyingReason();
        applyingReason.setInitialPermissionToAcceptEmploymentCheckbox(checkboxProperties
                .get("applyingReason.initialPermissionToAcceptEmploymentCheckbox")
                .toString());
        i765Table.setApplyingReason(applyingReason);

        I765Table.Applicant i765TableApplicant = convertApplicant(asylumCaseProfile);
        i765Table.setApplicant(i765TableApplicant);
        i765Table.setEligibilityCategory(convertEligibilityCategory());

        i765Table.setApplicantStatement(convertApplicantStatement(lawyerProfile, asylumCaseProfile));
        i765Table.setApplicantContact(convertI765TableApplicantContact(applicationCase.getEmail(), getPhoneNumber(asylumCaseProfile.getApplicant())));
        i765Table.setPreparer(I765TableUtils.convertPreparer(lawyerProfile, checkboxProperties));
        return i765Table;
    }

    private String getPhoneNumber(Applicant applicant){
        if(applicant.getTelePhoneAreaCode() == null || applicant.getTelePhoneNumber() == null){
            return "";
        }

        return applicant.getTelePhoneAreaCode() + applicant.getTelePhoneNumber();
    }

    private I765Table.ApplicantContact convertI765TableApplicantContact(String email, String phoneNumber){
        I765Table.ApplicantContact applicantContact = new I765Table.ApplicantContact();
        I765Table.Contact contact = new I765Table.Contact();
        contact.setDaytimePhone(phoneNumber);
        contact.setMobilePhone(phoneNumber);
        contact.setEmail(email);
        applicantContact.setContact(contact);
        applicantContact.setSalvadoranOrGuatermalanCheckbox(checkboxProperties.getProperty("applicantContact.salvadoranOrGuatermalanCheckbox"));
        return applicantContact;
    }

    private I765Table.ApplicantStatement convertApplicantStatement(LawyerProfile lawyerProfile, AsylumCaseProfile asylumCaseProfile) {
        LawyerBasicInfo basicInfo = lawyerProfile.getBasicInfo();
        I765Table.ApplicantStatement applicantStatement = new I765Table.ApplicantStatement();

        applicantStatement.setPreparedByOtherCheckbox(checkboxProperties.getProperty("applicantStatement.preparedByOtherCheckbox"));
        applicantStatement.setPrepareName(basicInfo.getFirstName() + getMiddleName(basicInfo.getMiddleName()) + basicInfo.getLastName());

        return applicantStatement;
    }

    private I765Table.EligibilityCategory convertEligibilityCategory(){
        I765Table.EligibilityCategory eligibilityCategory = new I765Table.EligibilityCategory();
        return eligibilityCategory;
    }

    private I765Table.Applicant convertApplicant(AsylumCaseProfile asylumCaseProfile){
        Applicant applicantProfile = asylumCaseProfile.getApplicant();
        Family family = asylumCaseProfile.getFamily();

        I765Table.Applicant applicant = new I765Table.Applicant();
        NameEntity currentNameEntity = NameEntity.builder()
                .firstName(applicantProfile.getFirstName())
                .middleName(applicantProfile.getMiddleName())
                .lastName(applicantProfile.getLastName())
                .build();

        applicant.setCurrentName(currentNameEntity);

        I765Table.Address address = I765Table.Address
                .builder()
                .inCareOfName(applicantProfile.getInCareOf())
                .streetNumberAndName(applicantProfile.getStreetNumberAndNameOfMailingAddress())
                .aptSteFlrNumber(applicantProfile.getAptNumberOfMailingAddress())
                .city(applicantProfile.getCityOfMailingAddress())
                .state(applicantProfile.getStateOfMailingAddress())
                .zipCode(applicantProfile.getZipCodeOfMailingAddress())
                .country("USA")
                .build();
        applicant.setMailAddress(address);

        if (applicantProfile.getStreetNumberAndName().equals(applicantProfile.getAptNumberOfMailingAddress())) {
            applicant.setSameAsPhysicalAddressYesCheckbox(checkboxProperties.getProperty("sameAsPhysicalAddressYesCheckbox"));
        } else {
            applicant.setSameAsPhysicalAddressNoCheckbox(checkboxProperties.getProperty("sameAsPhysicalAddressNoCheckbox"));
            I765Table.Address physicalAddress = I765Table.Address
                    .builder()
                    .inCareOfName(applicantProfile.getInCareOf())
                    .streetNumberAndName(applicantProfile.getStreetNumberAndName())
                    .aptSteFlrNumber(applicantProfile.getAptSteFlrNumber())
                    .city(applicantProfile.getCity())
                    .state(applicantProfile.getState())
                    .zipCode(applicantProfile.getZipCode())
                    .country("USA")
                    .build();

            applicant.setPhysicalAddress(physicalAddress);
        }
        applicant.setAlienNumber(applicantProfile.getAlienNumber());
        applicant.setUscisOnlineAccountNumber(applicantProfile.getUscisOnlineAccountNumber());
        if(applicantProfile.isGenderMaleCheckbox()){
            applicant.setGenderMaleCheckbox(checkboxProperties.getProperty("genderMaleCheckbox"));
        }
        if (applicantProfile.isGenderFemaleCheckbox()) {
            applicant.setGenderMaleCheckbox(checkboxProperties.getProperty("genderFemaleCheckbox"));
        }

        if(applicantProfile.isMaritalStatusSingleCheckbox()){
            applicant.setSingleMaritalStatusCheckbox(checkboxProperties.getProperty("singleMaritalStatusCheckbox"));
        }
        if(applicantProfile.isMaritalStatusMarriedCheckbox()){
            applicant.setMarriedMaritalStatusCheckbox(checkboxProperties.getProperty("marriedMaritalStatusCheckbox"));
        }
        if(applicantProfile.isMaritalStatusDivorcedCheckbox()){
            applicant.setDivorcedMaritalStatusCheckbox(checkboxProperties.getProperty("divorcedMaritalStatusCheckbox"));
        }
        if(applicantProfile.isMaritalStatusWidowedCheckbox()){
            applicant.setWidowedMaritalStatusCheckbox(checkboxProperties.getProperty("widowedMaritalStatusCheckbox"));
        }

        applicant.setPreviousFilledFormNoCheckbox(checkboxProperties.getProperty("previousFilledFormNoCheckbox"));
        applicant.setSsn(applicantProfile.getSsn());
        if(Strings.isBlank(applicantProfile.getSsn()) || applicantProfile.getSsn().equals("N/A")){
            applicant.setIssuedSSNNoCheckbox(checkboxProperties.getProperty("issuedSSNNoCheckbox"));
            applicant.setWantSSNIssueSSNYesCheckbox(checkboxProperties.getProperty("wantSSNIssueSSNYesCheckbox"));
        }

        FamilyMember father = family.getFather();
        FamilyMember mother = family.getMother();

        applicant.setFatherFirstName(getFirstName(father.getName()));
        applicant.setFatherLastName(getLastName(father.getName()));

        applicant.setMotherFirstName(getFirstName(mother.getName()));
        applicant.setMotherLastName(getLastName(mother.getName()));
        applicant.setConsentDisclosureYesCheckbox(checkboxProperties.getProperty("consentDisclosureYesCheckbox"));
        applicant.setFirstCountry(applicantProfile.getNationality());

        //ask Front fill the new fields
        applicant.setBirthCity(applicantProfile.getBirthCity());
        applicant.setBirthCountry(applicantProfile.getBirthCountry());
        applicant.setBirthDate(applicantProfile.getBirthDate());

        LastArrival lastArrival = new LastArrival();
        lastArrival.setI94(applicantProfile.getI94Number());
        lastArrival.setPassportNumber(applicantProfile.getPassportNumber());
        lastArrival.setTravelDocumentNumber(applicantProfile.getTravelDocumentNumber());
        lastArrival.setIssueCountry(applicantProfile.getPassportIssueCountry());
        lastArrival.setExpireDate(applicantProfile.getStatusExpireDate());
        lastArrival.setLastArrivalImmigrationStatus(applicantProfile.getLastAdmittedStatus());

        // check with lawyer.
        lastArrival.setCurrentImmigrationStatus(applicantProfile.getLastAdmittedStatus());
        applicant.setLastArrival(lastArrival);

        return applicant;
    }

    private String getFirstName(String name){
        if(Strings.isBlank(name)){
            return null;
        }
        String[] s = name.split(" ");
        return s[0];
    }

    private String getLastName(String name){
        if(Strings.isBlank(name)){
            return null;
        }
        String[] s = name.split(" ");
        return s[s.length - 1];
    }

    private String getMiddleName(String middleName){
        return (StringUtils.isNullOrEmpty(middleName) || middleName.trim().equalsIgnoreCase("N/A"))
                ? " " : " " + middleName + " ";
    }

//
//    private String consentDisclosureYesCheckbox;
//    private String consentDisclosureNoCheckbox;

//
//    private String firstCountry;
//    private String secondCountry;
//    private String birthCity;
//    private String birthState;
//    private String birthCountry;
//    private String birthDate;
//
//    private LastArrival lastArrival;
//
}
