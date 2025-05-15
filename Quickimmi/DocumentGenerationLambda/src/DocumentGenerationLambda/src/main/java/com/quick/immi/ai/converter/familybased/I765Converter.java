package com.quick.immi.ai.converter.familybased;

import com.quick.immi.ai.converter.common.I765TableUtils;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.common.NameEntity;
import com.quick.immi.ai.entity.familyBased.businss.*;
import com.quick.immi.ai.entity.familyBased.businss.Address;
import com.quick.immi.ai.entity.i765.I765Table;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.InstanceUtils;
import crawlercommons.utils.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import software.amazon.awssdk.utils.CollectionUtils;

public class I765Converter {
  private Properties checkboxProperties;
  private static final Logger log = Logger.getLogger(I765Converter.class.getName());

  public I765Converter() {
    checkboxProperties = FormFillUtils.getFormMapping(FormMapping.I765Checkbox);
  }

  public I765Table getTable(LawyerProfile lawyerProfile, FamilyBasedCaseProfile familyBasedCaseProfile) {
    try {
      I765Table i765Table = new I765Table();

      LawyerBasicInfo basicInfo = lawyerProfile.getBasicInfo();
      if (basicInfo == null) basicInfo = new LawyerBasicInfo();
      i765Table.setAttorneyUSCISOnlineAccountNumber(basicInfo.getUscisOnlineAccountNumber());
      i765Table.setG28AttachedCheckbox(checkboxProperties.get("g28AttachedCheckbox").toString());

      I765Table.ApplyingReason applyingReason = new I765Table.ApplyingReason();
      applyingReason.setInitialPermissionToAcceptEmploymentCheckbox(checkboxProperties
              .getProperty("applyingReason.initialPermissionToAcceptEmploymentCheckbox"));

      i765Table.setApplyingReason(applyingReason);

      I765Table.Applicant i765TableApplicant = getI765TableApplicant(familyBasedCaseProfile);
      i765Table.setApplicant(i765TableApplicant);
      I765Table.EligibilityCategory i765TableEligibility = getI765TableEligibility(familyBasedCaseProfile);
      i765Table.setEligibilityCategory(i765TableEligibility);
      I765Table.ApplicantStatement i765TableApplicantStatement = getI765TableApplicantStatement(lawyerProfile, familyBasedCaseProfile);
      i765Table.setApplicantStatement(i765TableApplicantStatement);
      I765Table.ApplicantContact i765TableApplicantContact = getI765TableApplicantContact(familyBasedCaseProfile);
      i765Table.setApplicantContact(i765TableApplicantContact);
      I765Table.Interpreter i765TableInterpreter = getI765TableInterpreter(familyBasedCaseProfile);
      i765Table.setInterpreter(i765TableInterpreter);
      I765Table.Preparer i765TablePreparer = I765TableUtils.convertPreparer(lawyerProfile, checkboxProperties);
      i765Table.setPreparer(i765TablePreparer);

      return i765Table;
    } catch (Exception exp) {
      log.log(Level.SEVERE, "an exception was thrown whe I765Converter", exp);
      throw new RuntimeException(exp);
    }
  }

  private I765Table.Applicant getI765TableApplicant(FamilyBasedCaseProfile familyBasedCaseProfile){
    Beneficiary applicantProfile = familyBasedCaseProfile.getBeneficiary();

    I765Table.Applicant applicant = new I765Table.Applicant();
    NameEntity currentNameEntity = NameEntity.builder()
        .firstName(applicantProfile.getFirstName())
        .middleName(applicantProfile.getMiddleName())
        .lastName(applicantProfile.getLastName())
        .build();

    applicant.setCurrentName(currentNameEntity);
    List<BeneficiaryOtherName> beneficiaryOtherNames = applicantProfile.getBeneficiaryOtherNames();
    List<NameEntity> otherUsedNames = new ArrayList<>();

    for (int i = 0; i < Math.min(3, beneficiaryOtherNames.size()); i++) {
      BeneficiaryOtherName beneficiaryOtherName = beneficiaryOtherNames.get(i);
      NameEntity nameEntity = NameEntity.builder()
          .firstName(beneficiaryOtherName.getFirstName())
          .middleName(beneficiaryOtherName.getMiddleName())
          .lastName(beneficiaryOtherName.getLastName())
          .build();
      otherUsedNames.add(nameEntity);
    }
    applicant.setOtherUsedNames(otherUsedNames);

    Address mailingAddress = applicantProfile.getMailingAddress();
    if (!applicantProfile.getAddressHistories().isEmpty()) {
      Address phyAddress = applicantProfile.getAddressHistories().get(0);
      if(phyAddress == null){
        phyAddress = InstanceUtils.createInstanceWithDefaults(Address.class);
      }
      I765Table.Address address = I765Table.Address
          .builder()
          .inCareOfName(mailingAddress.getInCareOf())
          .streetNumberAndName(mailingAddress.getStreetNumberAndName())
          .aptSteFlrNumber(mailingAddress.getAptSteFlrNumber())
          .city(mailingAddress.getCityOrTown())
          .state(mailingAddress.getState())
          .zipCode(mailingAddress.getZipCode())
          .country(phyAddress.getCountry())
          .build();
      applicant.setMailAddress(address);

      if (phyAddress.getStreetNumberAndName().equals(mailingAddress.getStreetNumberAndName())) {
        applicant.setSameAsPhysicalAddressYesCheckbox(
            checkboxProperties.getProperty("sameAsPhysicalAddressYesCheckbox"));
      } else {
        applicant.setSameAsPhysicalAddressNoCheckbox(
            checkboxProperties.getProperty("sameAsPhysicalAddressNoCheckbox"));
        I765Table.Address physicalAddress = I765Table.Address
            .builder()
            .inCareOfName(phyAddress.getInCareOf())
            .streetNumberAndName(phyAddress.getStreetNumberAndName())
            .aptSteFlrNumber(phyAddress.getAptSteFlrNumber())
            .city(phyAddress.getCityOrTown())
            .state(phyAddress.getState())
            .zipCode(phyAddress.getZipCode())
            .country(phyAddress.getCountry())
            .build();

        applicant.setPhysicalAddress(physicalAddress);
      }
    }
    applicant.setAlienNumber(applicantProfile.getAlienNumber());
    applicant.setUscisOnlineAccountNumber(applicantProfile.getUSCISOnlineAccountNumber());
    if(applicantProfile.isSexMaleCheckbox()){
      applicant.setGenderMaleCheckbox(checkboxProperties.getProperty("genderMaleCheckbox"));
    }
    if (applicantProfile.isSexFemaleCheckbox()) {
      applicant.setGenderMaleCheckbox(checkboxProperties.getProperty("genderFemaleCheckbox"));
    }

    if(applicantProfile.getMaritalInfo().isMaritalStatusSingleCheckbox()){
      applicant.setSingleMaritalStatusCheckbox(checkboxProperties.getProperty("singleMaritalStatusCheckbox"));
    }
    if(applicantProfile.getMaritalInfo().isMaritalStatusMarriedCheckbox()){
      applicant.setMarriedMaritalStatusCheckbox(checkboxProperties.getProperty("marriedMaritalStatusCheckbox"));
    }
    if(applicantProfile.getMaritalInfo().isMaritalStatusDivorcedCheckbox()){
      applicant.setDivorcedMaritalStatusCheckbox(checkboxProperties.getProperty("divorcedMaritalStatusCheckbox"));
    }
    if(applicantProfile.getMaritalInfo().isMaritalStatusWidowedCheckbox()){
      applicant.setWidowedMaritalStatusCheckbox(checkboxProperties.getProperty("widowedMaritalStatusCheckbox"));
    }

    applicant.setPreviousFilledFormNoCheckbox(checkboxProperties.getProperty("previousFilledFormNoCheckbox"));
    applicant.setSsn(applicantProfile.getSsn());
    if(Strings.isBlank(applicantProfile.getSsn()) || applicantProfile.getSsn().equals("N/A")){
      applicant.setIssuedSSNNoCheckbox(checkboxProperties.getProperty("issuedSSNNoCheckbox"));
      applicant.setWantSSNIssueSSNYesCheckbox(checkboxProperties.getProperty("wantSSNIssueSSNYesCheckbox"));
    }

    Parent mother = familyBasedCaseProfile.getBeneficiary().getFamily().getMother();
    Parent father = familyBasedCaseProfile.getBeneficiary().getFamily().getFather();

    applicant.setFatherFirstName(mother.getFirstName());
    applicant.setFatherLastName(getLastName(mother.getLastName()));

    applicant.setMotherFirstName(getFirstName(father.getFirstName()));
    applicant.setMotherLastName(getLastName(father.getLastName()));
    applicant.setConsentDisclosureYesCheckbox(checkboxProperties.getProperty("consentDisclosureYesCheckbox"));
    if (!CollectionUtils.isNullOrEmpty(applicantProfile.getBeneficiaryNationalities())) {
      applicant.setFirstCountry(applicantProfile.getBeneficiaryNationalities().get(0));
      if (applicantProfile.getBeneficiaryNationalities().size() > 1) {
        applicant.setSecondCountry(applicantProfile.getBeneficiaryNationalities().get(1));
      }
    }

    //ask Front fill the new fields
    applicant.setBirthCity(applicantProfile.getCityOfBirth());
    applicant.setBirthCountry(applicantProfile.getCountryOfBirth());
    applicant.setBirthDate(applicantProfile.getDateOfBirth());

    LastArrival lastArrival = new LastArrival();
    LastArrivalInformation lastArrivalInformation = applicantProfile.getLastArrivalInformation();
    if(lastArrivalInformation == null){
      lastArrivalInformation = InstanceUtils.createInstanceWithDefaults(LastArrivalInformation.class);
    }
    lastArrival.setI94(lastArrivalInformation.getI94Number());
    lastArrival.setPassportNumber(lastArrivalInformation.getPassportNumber());
    lastArrival.setTravelDocumentNumber(lastArrivalInformation.getTravelDocumentNumber());
    lastArrival.setIssueCountry(lastArrivalInformation.getPassportIssueCountry());
    lastArrival.setExpireDate(lastArrivalInformation.getAuthorizedStayExpirationDate());
    lastArrival.setLastArrivalImmigrationStatus(lastArrivalInformation.getArrivedAsAdmission());

    // check with lawyer.
    lastArrival.setCurrentImmigrationStatus(lastArrivalInformation.getCurrentImmigrationStatus());
    applicant.setLastArrival(lastArrival);

    return applicant;
  }

  private I765Table.EligibilityCategory getI765TableEligibility(FamilyBasedCaseProfile familyBasedCaseProfile){
    I765Table.EligibilityCategory eligibilityCategory = new I765Table.EligibilityCategory();

    return eligibilityCategory;
  }

  private I765Table.ApplicantStatement getI765TableApplicantStatement(LawyerProfile lawyerProfile, FamilyBasedCaseProfile familyBasedCaseProfile){
    I765Table.ApplicantStatement applicantStatement = new I765Table.ApplicantStatement();
    applicantStatement.setReadAndUnderstandEnglishCheckbox(checkboxProperties.getProperty("applicantStatement.readAndUnderstandEnglishCheckbox"));
    applicantStatement.setReadByInterpreterCheckbox(checkboxProperties.getProperty("applicantStatement.readByInterpreterCheckbox"));
    applicantStatement.setReadLanguage(familyBasedCaseProfile.getBeneficiary().getNativeLanguage());
    applicantStatement.setPreparedByOtherCheckbox(checkboxProperties.getProperty("applicantStatement.preparedByOtherCheckbox"));
    applicantStatement.setPrepareName(lawyerProfile.getBasicInfo().getFirstName() + " " + lawyerProfile.getBasicInfo().getMiddleName() + " " + lawyerProfile.getBasicInfo().getLastName());
    return applicantStatement;
  }

  private I765Table.ApplicantContact getI765TableApplicantContact(FamilyBasedCaseProfile familyBasedCaseProfile){
    I765Table.ApplicantContact applicantContact = new I765Table.ApplicantContact();
    I765Table.Contact contact = new I765Table.Contact();
    contact.setDaytimePhone(familyBasedCaseProfile.getBeneficiary().getDaytimePhoneNumber());
    contact.setMobilePhone(familyBasedCaseProfile.getBeneficiary().getMobilePhoneNumber());
    contact.setEmail(familyBasedCaseProfile.getBeneficiary().getEmailAddress());
    applicantContact.setContact(contact);
    applicantContact.setSalvadoranOrGuatermalanCheckbox(checkboxProperties.getProperty("applicantContact.salvadoranOrGuatermalanCheckbox"));
    return applicantContact;
  }

  private I765Table.Interpreter getI765TableInterpreter(FamilyBasedCaseProfile familyBasedCaseProfile){
    I765Table.Interpreter interpreter = new I765Table.Interpreter();
    interpreter.setLastName(familyBasedCaseProfile.getInterpreter().getLastName());
    interpreter.setFirstName(familyBasedCaseProfile.getInterpreter().getFirstName());
    interpreter.setOrganizationName(familyBasedCaseProfile.getInterpreter().getBusinessName());
    I765Table.Address address = I765Table.Address.builder()
        .streetNumberAndName(familyBasedCaseProfile.getInterpreter().getStreetNumberAndName())
        .aptSteFlrNumber(familyBasedCaseProfile.getInterpreter().getAptSteFlrNumber())
        .city(familyBasedCaseProfile.getInterpreter().getCity())
        .state(familyBasedCaseProfile.getInterpreter().getState())
        .zipCode(familyBasedCaseProfile.getInterpreter().getZipCode())
        .country(familyBasedCaseProfile.getInterpreter().getCountry())
        .province(familyBasedCaseProfile.getInterpreter().getProvince())
        .aptCheckbox(String.valueOf(familyBasedCaseProfile.getInterpreter().isAptCheckbox()))
        .steCheckbox(String.valueOf(familyBasedCaseProfile.getInterpreter().isSteCheckbox()))
        .flrCheckbox(String.valueOf(familyBasedCaseProfile.getInterpreter().isFlrCheckbox()))
        .build();
    interpreter.setAddress(address);
    I765Table.Contact contact = new I765Table.Contact();
    contact.setDaytimePhone(familyBasedCaseProfile.getInterpreter().getDaytimeTelephoneNumber());
    contact.setMobilePhone(familyBasedCaseProfile.getInterpreter().getMobileTelephoneNumber());
    contact.setEmail(familyBasedCaseProfile.getInterpreter().getEmailAddress());
    interpreter.setContact(contact);
    interpreter.setFluentLanguage(familyBasedCaseProfile.getBeneficiary().getNativeLanguage());
    return interpreter;
  }

  private I765Table.AdditionalInfo getI765TableAdditionalInfo(FamilyBasedCaseProfile familyBasedCaseProfile){
    I765Table.AdditionalInfo additionalInfo = new I765Table.AdditionalInfo();
    NameEntity nameEntity = NameEntity.builder()
        .firstName(familyBasedCaseProfile.getBeneficiary().getFirstName())
        .middleName(familyBasedCaseProfile.getBeneficiary().getMiddleName())
        .lastName(familyBasedCaseProfile.getBeneficiary().getLastName())
        .build();
    additionalInfo.setApplicantName(nameEntity);
    additionalInfo.setAlienNumber(familyBasedCaseProfile.getBeneficiary().getAlienNumber());
    Beneficiary applicantProfile = familyBasedCaseProfile.getBeneficiary();
    List<BeneficiaryOtherName> beneficiaryOtherNames = applicantProfile.getBeneficiaryOtherNames();
    if (beneficiaryOtherNames.size() > 4) {
      for (int i = 3; i < beneficiaryOtherNames.size(); i++) {
        BeneficiaryOtherName beneficiaryOtherName = beneficiaryOtherNames.get(i);
        String fullName = beneficiaryOtherName.getFirstName() + " " + beneficiaryOtherName.getMiddleName() + " " + beneficiaryOtherName.getLastName();

        switch (i) {
          case 3:
            additionalInfo.setA3PageNumber("1");
            additionalInfo.setA3PartNumber("2");
            additionalInfo.setA3ItemNumber("2");
            additionalInfo.setD3Content(fullName);
            break;
          case 4:
            additionalInfo.setA4PageNumber("1");
            additionalInfo.setA4PartNumber("2");
            additionalInfo.setA4ItemNumber("2");
            additionalInfo.setD4Content(fullName);
            break;
          case 5:
            additionalInfo.setA5PageNumber("1");
            additionalInfo.setA5PartNumber("2");
            additionalInfo.setA5ItemNumber("2");
            additionalInfo.setD5Content(fullName);
            break;
          case 6:
            additionalInfo.setA6PageNumber("1");
            additionalInfo.setA6PartNumber("2");
            additionalInfo.setA6ItemNumber("2");
            additionalInfo.setD6Content(fullName);
            break;
          case 7:
            additionalInfo.setA7PageNumber("1");
            additionalInfo.setA7PartNumber("2");
            additionalInfo.setA7ItemNumber("2");
            additionalInfo.setD7Content(fullName);
            break;
          default:
            // You can add more cases if needed, or handle if i exceeds the available fields.
            break;
        }
      }
    }
    int start = beneficiaryOtherNames.size() + 1;
    if (start <= 7) {
      if (applicantProfile.getBeneficiaryNationalities().size() > 2) {
        for (int i = 2; i < applicantProfile.getBeneficiaryNationalities().size(); i++) {
          switch(i - 2 + start) {
            case 3:
              additionalInfo.setA3PageNumber("2");
              additionalInfo.setA3PartNumber("2");
              additionalInfo.setA3ItemNumber("18");
              additionalInfo.setD3Content(applicantProfile.getBeneficiaryNationalities().get(i));
              break;
            case 4:
              additionalInfo.setA4PageNumber("2");
              additionalInfo.setA4PartNumber("2");
              additionalInfo.setA4ItemNumber("18");
              additionalInfo.setD4Content(applicantProfile.getBeneficiaryNationalities().get(i));
              break;
            case 5:
              additionalInfo.setA5PageNumber("2");
              additionalInfo.setA5PartNumber("2");
              additionalInfo.setA5ItemNumber("18");
              additionalInfo.setD5Content(applicantProfile.getBeneficiaryNationalities().get(i));
              break;
            case 6:
              additionalInfo.setA6PageNumber("2");
              additionalInfo.setA6PartNumber("2");
              additionalInfo.setA6ItemNumber("18");
              additionalInfo.setD6Content(applicantProfile.getBeneficiaryNationalities().get(i));
              break;
            case 7:
              additionalInfo.setA7PageNumber("2");
              additionalInfo.setA7PartNumber("2");
              additionalInfo.setA7ItemNumber("18");
              additionalInfo.setD7Content(applicantProfile.getBeneficiaryNationalities().get(i));
              break;
            default:
              // You can add more cases if needed, or handle if i exceeds the available fields.
              break;
          }

        }
      }
    }


    return additionalInfo;
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
}