package com.quick.immi.ai.converter.familybased;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.LawyerBasicInfo;
import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.familyBased.businss.Beneficiary;
import com.quick.immi.ai.entity.familyBased.businss.Family;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.familyBased.businss.LastArrivalInformation;
import com.quick.immi.ai.entity.familyBased.i130.Petitioner;
import com.quick.immi.ai.entity.i485.AdditionalRecord;
import com.quick.immi.ai.entity.i485.Address;
import com.quick.immi.ai.entity.i485.AffidavitExemption;
import com.quick.immi.ai.entity.i485.Applicant;
import com.quick.immi.ai.entity.i485.ApplicantContact;
import com.quick.immi.ai.entity.i485.ApplicationType;
import com.quick.immi.ai.entity.i485.Attorney;
import com.quick.immi.ai.entity.i485.Background;
import com.quick.immi.ai.entity.i485.Benefit;
import com.quick.immi.ai.entity.i485.Biographic;
import com.quick.immi.ai.entity.i485.Child;
import com.quick.immi.ai.entity.i485.ChildrenInfo;
import com.quick.immi.ai.entity.i485.Eligibility;
import com.quick.immi.ai.entity.i485.EmploymentHistory;
import com.quick.immi.ai.entity.i485.I485Table;
import com.quick.immi.ai.entity.i485.Institutionalization;
import com.quick.immi.ai.entity.i485.Interpreter;
import com.quick.immi.ai.entity.i485.MaritalHistory;
import com.quick.immi.ai.entity.i485.Parent;
import com.quick.immi.ai.entity.i485.ParentInfo;
import com.quick.immi.ai.entity.i485.Preparer;
import com.quick.immi.ai.entity.i485.Signature;
import com.quick.immi.ai.entity.i485.Supplement;
import com.quick.immi.ai.entity.i485.TopRight;
import com.quick.immi.ai.utils.CopyUtils;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.InstanceUtils;

public class I485Converter {

  private static final Logger log = Logger.getLogger(I131Converter.class.getName());

  private Properties checkboxProperties;
  private Supplement supplement;

  public I485Converter() {
    checkboxProperties = FormFillUtils.getFormMapping(FormMapping.I485Checkbox);
  }

  public I485Table getI485Table(FamilyBasedCaseProfile familyBasedCaseProfile,
      LawyerProfile lawyerProfile) {
    try {
      I485Table i485Table = new I485Table();
      supplement = new Supplement();
      supplement.setAdditionalRecords(new ArrayList<AdditionalRecord>());
      i485Table.setSupplements(supplement);
      i485Table.setTopRight(convertTopRight(familyBasedCaseProfile));
      i485Table.setAttorney(convertAttorney(lawyerProfile));
      i485Table.setApplicant(convertApplicant(familyBasedCaseProfile));
      i485Table.setApplicationType(convertApplicationType(familyBasedCaseProfile, lawyerProfile));
      i485Table.setAffidavitExemption(convertAffidavitExemption(familyBasedCaseProfile, lawyerProfile));
      i485Table.setBackground(convertBackground(familyBasedCaseProfile));
      i485Table.setParentInfo(convertParentInfo(familyBasedCaseProfile));
      i485Table.setMaritalHistory(conveMaritalHistory(familyBasedCaseProfile));
      i485Table.setChildrenInfo(convertChildrenInfo(familyBasedCaseProfile));
      i485Table.setBiographic(convertBiographic(familyBasedCaseProfile));
      i485Table.setEligibility(convertEligibility(familyBasedCaseProfile));
      i485Table.setApplicantContact(convertApplicantContact(familyBasedCaseProfile));
      i485Table.setInterpreter(convertInterpreter());
      i485Table.setPreparer(convertPreparer());
      i485Table.setSignature(convertSignature());
      i485Table.setSupplements(supplement);
      return i485Table;
    } catch (Exception exception) {
      log.log(Level.SEVERE, "an exception was thrown whe getI131Table", exception);
      throw new RuntimeException(exception);
    }
  }

  private TopRight convertTopRight(FamilyBasedCaseProfile familyBasedCaseProfile) {
    TopRight targetTopRight = new TopRight();
    Beneficiary beneficiary = familyBasedCaseProfile.getBeneficiary();
    if (beneficiary == null) {
      beneficiary = InstanceUtils.createInstanceWithDefaults(Beneficiary.class);
    }
    targetTopRight.setAlienNumberPage1(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage2(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage3(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage4(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage5(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage6(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage7(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage8(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage9(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage10(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage11(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage12(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage13(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage14(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage15(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage16(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage17(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage18(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage19(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage20(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage21(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage22(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage23(beneficiary.getAlienNumber());
    targetTopRight.setAlienNumberPage24(beneficiary.getAlienNumber());
    return targetTopRight;
  }


  private Attorney convertAttorney(LawyerProfile lawyerProfile) {
    Attorney targetAttorney = new Attorney();
    if (lawyerProfile == null) {
      lawyerProfile = InstanceUtils.createInstanceWithDefaults(LawyerProfile.class);
    }
    targetAttorney.setG28AttachedCheckbox(
        checkboxProperties.getProperty("attorney.g28AttachedCheckbox"));
    if (lawyerProfile.getEligibility() != null) {
      targetAttorney.setAttorneyStateBarNumber(lawyerProfile.getEligibility().getBarNumber());
    }
    if (lawyerProfile.getBasicInfo() != null) {
      targetAttorney.setUSCISOnlineAcctNumber(
          lawyerProfile.getBasicInfo().getUscisOnlineAccountNumber());
    }
    return targetAttorney;
  }

  private Applicant convertApplicant(FamilyBasedCaseProfile familyBasedCaseProfile) {
    Applicant targetApplicant = new Applicant();
    Beneficiary beneficiary = familyBasedCaseProfile.getBeneficiary();
    if (beneficiary == null) {
      beneficiary = InstanceUtils.createInstanceWithDefaults(Beneficiary.class);
    }
    LastArrivalInformation lastArrivalInformation = beneficiary.getLastArrivalInformation();
    if (lastArrivalInformation == null) {
      lastArrivalInformation = InstanceUtils.createInstanceWithDefaults(
          LastArrivalInformation.class);
    }
    targetApplicant.setFamilyName(beneficiary.getLastName());
    targetApplicant.setGivenName(beneficiary.getFirstName());
    targetApplicant.setMiddleName(beneficiary.getMiddleName());
    if (beneficiary.getBeneficiaryOtherNames() != null) {
      if (beneficiary.getBeneficiaryOtherNames().size() >= 1) {
        targetApplicant.setOtherFamilyName1(
            beneficiary.getBeneficiaryOtherNames().get(0).getLastName());
        targetApplicant.setOtherGivenName1(
            beneficiary.getBeneficiaryOtherNames().get(0).getFirstName());
        targetApplicant.setOtherMiddleName1(
            beneficiary.getBeneficiaryOtherNames().get(0).getMiddleName());
      }
      if (beneficiary.getBeneficiaryOtherNames().size() >= 2) {
        targetApplicant.setOtherFamilyName2(
            beneficiary.getBeneficiaryOtherNames().get(1).getLastName());
        targetApplicant.setOtherGivenName2(
            beneficiary.getBeneficiaryOtherNames().get(1).getFirstName());
        targetApplicant.setOtherMiddleName2(
            beneficiary.getBeneficiaryOtherNames().get(1).getMiddleName());
      }
    }
    // Date of Birth
    targetApplicant.setDateOfBirth(beneficiary.getDateOfBirth());
    if (beneficiary.isOtherdateOfBirthYes()) {
      targetApplicant.setOtherdateOfBirthYes(
          checkboxProperties.getProperty("applicant.otherdateOfBirthYes"));
      targetApplicant.setOtherdateOfBirth1(beneficiary.getOtherdateOfBirth1());
      targetApplicant.setOtherdateOfBirth2(beneficiary.getOtherdateOfBirth2());
    }
    if (beneficiary.isOtherdateOfBirthNo()) {
      targetApplicant.setOtherdateOfBirthNo(
          checkboxProperties.getProperty("applicant.otherdateOfBirthNo"));
    }

    targetApplicant.setAlienNumberYes(checkboxProperties.getProperty("applicant.alienNumberYes"));
    // Alien Number
    if (beneficiary.isAlienNumberYes()) {
      targetApplicant.setAlienNumberYes(checkboxProperties.getProperty("applicant.alienNumberYes"));
      targetApplicant.setAlienNumber(beneficiary.getAlienNumber());
    }
    if (beneficiary.isAlienNumberNo()) {
      targetApplicant.setAlienNumberNo(checkboxProperties.getProperty("applicant.alienNumberNo"));
    }
    if (beneficiary.isOtherAlienNumberYes()) {
      targetApplicant.setOtherAlienNumberYes(
          checkboxProperties.getProperty("applicant.otherAlienNumberYes"));
      targetApplicant.setOtherAlienNumber1(beneficiary.getOtherAlienNumber1());
      targetApplicant.setOtherAlienNumber2(beneficiary.getOtherAlienNumber2());
    }
    if (beneficiary.isOtherAlienNumberNo()) {
      targetApplicant.setOtherAlienNumberNo(
          checkboxProperties.getProperty("applicant.otherAlienNumberNo"));
    }

    if (beneficiary.isSexMaleCheckbox()) {
      targetApplicant.setMale(checkboxProperties.getProperty("applicant.male"));
    }
    if (beneficiary.isSexFemaleCheckbox()) {
      targetApplicant.setFemale(checkboxProperties.getProperty("applicant.female"));
    }
    // Other Gender
    if (beneficiary.isOtherGender()) {
      targetApplicant.setOtherGender(checkboxProperties.getProperty("applicant.otherGender"));
    }
    targetApplicant.setPlaceOfBirthCity(beneficiary.getCityOfBirth());
    targetApplicant.setPlaceOfBirthCountry(beneficiary.getCountryOfBirth());
    targetApplicant.setCountryOfCitizenship(beneficiary.getPassportIssuingCountry());
    targetApplicant.setUscicAccountNumber(beneficiary.getUSCISOnlineAccountNumber());

    targetApplicant.setPassportNumber(lastArrivalInformation.getPassportNumber());
    targetApplicant.setPassportExpirationDate(
        lastArrivalInformation.getExpirationDateForPassport());
    targetApplicant.setPassportIssuingCountry(lastArrivalInformation.getPassportIssueCountry());
    targetApplicant.setVisaNumber(lastArrivalInformation.getVisaNumber());
    targetApplicant.setVisaIssueDate(lastArrivalInformation.getVisaIssueDate());
    targetApplicant.setLastArrivalCity(lastArrivalInformation.getArrivalCity());
    targetApplicant.setLastArrivalState(lastArrivalInformation.getArrivalState());
    targetApplicant.setLastArrivalDate(lastArrivalInformation.getDateOfArrival());
    if (lastArrivalInformation.isAdmittedAtPortOfEntryCheckbox()) {
      targetApplicant.setPortOfEntryAdmission(lastArrivalInformation.getAdmissionEntryDetail());
      targetApplicant.setPortOfEntryAdmissionCheckbox(
          checkboxProperties.getProperty("applicant.portOfEntryAdmissionCheckbox"));
    }
    if (lastArrivalInformation.isParoledAtPortOfEntryCheckbox()) {
      targetApplicant.setPortOfEntryParole(lastArrivalInformation.getParoleEntranceDetail());
      targetApplicant.setPortOfEntryParoleCheckbox(
          checkboxProperties.getProperty("applicant.portOfEntryParoleCheckbox"));
    }
    if (lastArrivalInformation.isEnteredWithoutAdmissionCheckbox()) {
      targetApplicant.setNoAdmissionParoleCheckbox(
          checkboxProperties.getProperty("applicant.noAdmissionParoleCheckbox"));
    }
    if (lastArrivalInformation.isOtherEntryMethodCheckbox()) {
      targetApplicant.setOtherDetails(lastArrivalInformation.getOtherEntryDetail());
      targetApplicant.setOtherDetailsCheckbox(
          checkboxProperties.getProperty("applicant.otherDetailsCheckbox"));
    }

    targetApplicant.setCurrentImmigrationStatus(
        lastArrivalInformation.getCurrentImmigrationStatus());
    // Last Arrival First Time in the U.S.
    if (lastArrivalInformation.isLastArrivalFirstTimeYes()) {
      targetApplicant.setLastArrivalFirstTimeYes(
          checkboxProperties.getProperty("applicant.lastArrivalFirstTimeYes"));
    }
    if (lastArrivalInformation.isLastArrivalFirstTimeNo()) {
      targetApplicant.setLastArrivalFirstTimeNo(
          checkboxProperties.getProperty("applicant.lastArrivalFirstTimeNo"));
    }

// Expiration Date of Current Status
    targetApplicant.setExpirationDateCurrentStatus(beneficiary.getExpirationDateCurrentStatus());
    targetApplicant.setI94FamilyName(beneficiary.getLastName());
    targetApplicant.setI94GivenName(beneficiary.getFirstName());
    targetApplicant.setI94Number(lastArrivalInformation.getI94Number());
    targetApplicant.setI94ExpirationDate(lastArrivalInformation.getAuthorizedStayExpirationDate());
    targetApplicant.setImmigrationStatusOnI94(lastArrivalInformation.getI94Status());

//    // Address
//    com.quick.immi.ai.entity.familyBased.businss.Address physicalAddress  = familyBasedCaseProfile.getBeneficiary()
//        .getPhysicalAddress();
//    if (eligibility == null) {
//      eligibility = new com.quick.immi.ai.entity.familyBased.businss.Eligibility();
//    }
//    Eligibility targetEligibility = new Eligibility();
    CopyUtils.copyAllWithList(beneficiary, targetApplicant, checkboxProperties, "applicant");
      // todo add address history to supplement

    return targetApplicant;
  }

  private ApplicationType convertApplicationType(FamilyBasedCaseProfile familyBasedCaseProfile, LawyerProfile lawyerProfile) {
      ApplicationType targetApplicationType = new ApplicationType();
      com.quick.immi.ai.entity.familyBased.businss.Petitioner petitioner = familyBasedCaseProfile.getPetitioner();
      if (petitioner == null) {
          petitioner = InstanceUtils.createInstanceWithDefaults(com.quick.immi.ai.entity.familyBased.businss.Petitioner.class);
      }
      if (petitioner.isFilingWithEOIRYes()) {
          targetApplicationType.setIsFilingWithEOIRYes(checkboxProperties.getProperty("applicationType.isFilingWithEOIRYes"));
      }
      if (petitioner.isFilingWithEOIRNo()) {
          targetApplicationType.setIsFilingWithEOIRNo(checkboxProperties.getProperty("applicationType.isFilingWithEOIRNo"));
      }
      targetApplicationType.setReceiptNumber(petitioner.getReceiptNumber());
      targetApplicationType.setPriorityDate(petitioner.getPriorityDate());
      if (petitioner.isPrincipalApplicant()) {
          targetApplicationType.setIsPrincipalApplicant(checkboxProperties.getProperty("applicationType.isPrincipalApplicant"));
      }
      if (petitioner.isDerivativeApplicant()) {
          targetApplicationType.setIsDerivativeApplicant(checkboxProperties.getProperty("applicationType.isDerivativeApplicant"));
          targetApplicationType.setPrincipalApplicantFamilyName(petitioner.getPrincipalApplicantFamilyName());
          targetApplicationType.setPrincipalApplicantGivenName(petitioner.getPrincipalApplicantGivenName());
          targetApplicationType.setPrincipalApplicantMiddleName(petitioner.getPrincipalApplicantMiddleName());
          targetApplicationType.setPrincipalApplicantANumber(petitioner.getPrincipalApplicantANumber());
          targetApplicationType.setPrincipalApplicantDateOfBirth(petitioner.getPrincipalApplicantDateOfBirth());
      }
      if (petitioner.isSpouseOfUSCitizen()) {
          targetApplicationType.setIsSpouseOfUSCitizen(checkboxProperties.getProperty("applicationType.isSpouseOfUSCitizen"));
      }
      if (petitioner.isUnmarriedChildUnder21OfUSCitizen()) {
          targetApplicationType.setIsUnmarriedChildUnder21OfUSCitizen(checkboxProperties.getProperty("applicationType.isUnmarriedChildUnder21OfUSCitizen"));
      }
      if (petitioner.isParentOfUSCitizen()) {
          targetApplicationType.setIsParentOfUSCitizen(checkboxProperties.getProperty("applicationType.isParentOfUSCitizen"));
      }
      if (petitioner.isPersonAdmittedAsFianceOrChildOfFianceOfUSCitizen()) {
          targetApplicationType.setIsPersonAdmittedAsFianceOrChildOfFianceOfUSCitizen(checkboxProperties.getProperty("applicationType.isPersonAdmittedAsFianceOrChildOfFianceOfUSCitizen"));
      }
      if (petitioner.isWidowOrWidowerOfUSCitizen()) {
          targetApplicationType.setIsWidowOrWidowerOfUSCitizen(checkboxProperties.getProperty("applicationType.isWidowOrWidowerOfUSCitizen"));
      }
      if (petitioner.isSpouseChildParentOfDeceasedUSArmedForcesMember()) {
          targetApplicationType.setIsSpouseChildParentOfDeceasedUSArmedForcesMember(checkboxProperties.getProperty("applicationType.isSpouseChildParentOfDeceasedUSArmedForcesMember"));
      }
      if (petitioner.isUnmarriedSonDaughterOver21OfUSCitizen()) {
          targetApplicationType.setIsUnmarriedSonDaughterOver21OfUSCitizen(checkboxProperties.getProperty("applicationType.isUnmarriedSonDaughterOver21OfUSCitizen"));
      }
      if (petitioner.isMarriedSonDaughterOfUSCitizen()) {
          targetApplicationType.setIsMarriedSonDaughterOfUSCitizen(checkboxProperties.getProperty("applicationType.isMarriedSonDaughterOfUSCitizen"));
      }
      if (petitioner.isBrotherOrSisterOfUSCitizen()) {
          targetApplicationType.setIsBrotherOrSisterOfUSCitizen(checkboxProperties.getProperty("applicationType.isBrotherOrSisterOfUSCitizen"));
      }
      if (petitioner.isSpouseOfLawfulPermanentResident()) {
          targetApplicationType.setIsSpouseOfLawfulPermanentResident(checkboxProperties.getProperty("applicationType.isSpouseOfLawfulPermanentResident"));
      }
      if (petitioner.isUnmarriedChildUnder21OfLawfulPermanentResident()) {
          targetApplicationType.setIsUnmarriedChildUnder21OfLawfulPermanentResident(checkboxProperties.getProperty("applicationType.isUnmarriedChildUnder21OfLawfulPermanentResident"));
      }
      if (petitioner.isUnmarriedSonDaughterOver21OfLawfulPermanentResident()) {
          targetApplicationType.setIsUnmarriedSonDaughterOver21OfLawfulPermanentResident(checkboxProperties.getProperty("applicationType.isUnmarriedSonDaughterOver21OfLawfulPermanentResident"));
      }
      if (petitioner.isVAWASelfPetitioningSpouse()) {
          targetApplicationType.setIsVAWASelfPetitioningSpouse(checkboxProperties.getProperty("applicationType.isVAWASelfPetitioningSpouse"));
      }
      if (petitioner.isVAWASelfPetitioningChild()) {
          targetApplicationType.setIsVAWASelfPetitioningChild(checkboxProperties.getProperty("applicationType.isVAWASelfPetitioningChild"));
      }
      if (petitioner.isVAWASelfPetitioningParent()) {
          targetApplicationType.setIsVAWASelfPetitioningParent(checkboxProperties.getProperty("applicationType.isVAWASelfPetitioningParent"));
      }
      if (petitioner.isApplyingUnderINA245iYes()) {
          targetApplicationType.setIsApplyingUnderINA245iYes(checkboxProperties.getProperty("applicationType.isApplyingUnderINA245iYes"));
      }
      if (petitioner.isApplyingUnderINA245iNo()) {
          targetApplicationType.setIsApplyingUnderINA245iNo(checkboxProperties.getProperty("applicationType.isApplyingUnderINA245iNo"));
      }
      if (petitioner.isApplyingUnderCSPAYes()) {
          targetApplicationType.setIsApplyingUnderCSPAYes(checkboxProperties.getProperty("applicationType.isApplyingUnderCSPAYes"));
      }
      if (petitioner.isApplyingUnderCSPANo()) {
          targetApplicationType.setIsApplyingUnderCSPANo(checkboxProperties.getProperty("applicationType.isApplyingUnderCSPANo"));
      }
      return targetApplicationType;
  }

  private AffidavitExemption convertAffidavitExemption(FamilyBasedCaseProfile familyBasedCaseProfile, LawyerProfile lawyerProfile) {
    AffidavitExemption targetAffidavitExemption = new AffidavitExemption();
    com.quick.immi.ai.entity.familyBased.businss.AffidavitExemption affidavitExemption = familyBasedCaseProfile.getBeneficiary().getAffidavitExemption();
    CopyUtils.copyAllWithList(affidavitExemption, targetAffidavitExemption, checkboxProperties, "affidavitExemption");
    return targetAffidavitExemption;
  }

  private Background convertBackground(FamilyBasedCaseProfile familyBasedCaseProfile) {
    Background targetBackground = new Background();
    Beneficiary beneficiary = familyBasedCaseProfile.getBeneficiary();
    if (beneficiary == null) {
      beneficiary = InstanceUtils.createInstanceWithDefaults(Beneficiary.class);
    }
    if (beneficiary.isAppliedImmigrationVisaBefore()) {
      targetBackground.setHasAppliedForImmigrantVisaYes(
          checkboxProperties.getProperty("background.hasAppliedForImmigrantVisaYes"));
      targetBackground.setEmbassyCity(beneficiary.getUsEmbassies().get(0).getCity());
      targetBackground.setEmbassyCountry(beneficiary.getUsEmbassies().get(0).getCountry());
      targetBackground.setVisaDecision(beneficiary.getUsEmbassies().get(0).getVisaDecision());
      targetBackground.setVisaDecisionDate(
          beneficiary.getUsEmbassies().get(0).getVisaDecisionDate());

    } else {
      targetBackground.setHasAppliedForImmigrantVisaNo(
          checkboxProperties.getProperty("background.hasAppliedForImmigrantVisaNo"));
    }
    if (beneficiary.isPreviouslyAppliedForPermanentResidence()) {
      targetBackground.setHasPreviouslyAppliedForPermanentResidenceYes(
          checkboxProperties.getProperty("background.hasPreviouslyAppliedForPermanentResidenceYes"));
    } else {
      targetBackground.setHasPreviouslyAppliedForPermanentResidenceNo(
          checkboxProperties.getProperty("background.hasPreviouslyAppliedForPermanentResidenceNo"));
    }
    if (beneficiary.isHeldPermanentResidentStatus()) {
      targetBackground.setHasHeldPermanentResidentStatusYes(
          checkboxProperties.getProperty("background.hasHeldPermanentResidentStatusYes"));
    } else {
      targetBackground.setHasHeldPermanentResidentStatusNo(
          checkboxProperties.getProperty("background.hasHeldPermanentResidentStatusNo"));
    }
    if (!beneficiary.getEmploymentHistories().isEmpty()) {
      EmploymentHistory employmentHistory = new EmploymentHistory();
      employmentHistory.setCurrentEmployer(
          beneficiary.getEmploymentHistories().get(0).getNameOfEmployer());
      employmentHistory.setEmployerName(
          beneficiary.getEmploymentHistories().get(0).getNameOfEmployer());
      employmentHistory.setOccupation(beneficiary.getEmploymentHistories().get(0).getOccupation());
      Address address = new Address();
      address.setStreetNumberAndName(
          beneficiary.getEmploymentHistories().get(0).getStreetNumberAndName());
      if (beneficiary.getEmploymentHistories().get(0).isAptCheckbox()) {
        address.setAptCheckbox(checkboxProperties.getProperty(
            "background.employmentOrEducationHistories.address.aptCheckbox"));
      }
      if (beneficiary.getEmploymentHistories().get(0).isSteCheckbox()) {
        address.setSteCheckbox(checkboxProperties.getProperty(
            "background.employmentOrEducationHistories.address.steCheckbox"));
      }
      if (beneficiary.getEmploymentHistories().get(0).isFlrCheckbox()) {
        address.setFlrCheckbox(checkboxProperties.getProperty(
            "background.employmentOrEducationHistories.address.flrCheckbox"));
      }
      address.setAptSteFlrNumber(beneficiary.getEmploymentHistories().get(0).getAptSteFlrNumber());
      address.setCityOrTown(beneficiary.getEmploymentHistories().get(0).getCity());
      address.setCityOrTown(beneficiary.getEmploymentHistories().get(0).getCity());
      address.setState(beneficiary.getEmploymentHistories().get(0).getState());
      address.setZipCode(beneficiary.getEmploymentHistories().get(0).getZipCode());
      address.setProvince(beneficiary.getEmploymentHistories().get(0).getProvince());
      address.setPostalCode(beneficiary.getEmploymentHistories().get(0).getPostalCode());
      address.setCountry(beneficiary.getEmploymentHistories().get(0).getCountry());
      address.setDateFrom(beneficiary.getEmploymentHistories().get(0).getDateFrom());
      address.setDateTo(beneficiary.getEmploymentHistories().get(0).getDateTo());
      employmentHistory.setAddress(address);
      employmentHistory.setFinancialSupport(
          beneficiary.getEmploymentHistories().get(0).getFinancialSupport());
      targetBackground.setEmploymentOrEducationHistories(employmentHistory);
      for (int i = 1; i < beneficiary.getEmploymentHistories().size(); i++) {
        com.quick.immi.ai.entity.familyBased.businss.EmploymentHistory employment2 = beneficiary.getEmploymentHistories()
            .get(i);

        AdditionalRecord additionalRecord = new AdditionalRecord();
        additionalRecord.setPageNumber("8");
        additionalRecord.setPartNumber("4");
        additionalRecord.setItemNumber("7");
        additionalRecord.setAdditionalInfo(
            "Name of Employer: " + employment2.getNameOfEmployer() + " "
                + "Street Number and Name: '" + employment2.getAptSteFlrNumber() + " "
                + "Number: " + employment2.getAptSteFlrNumber() + " "
                + "City: " + employment2.getCity() + " "
                + "State: " + employment2.getState() + " "
                + "Zipcode: " + employment2.getZipCode() + " "
                + "Postalcode" + employment2.getPostalCode() + " "
                + "Country: " + employment2.getCountry() + " "
                + "Occupation: " + employment2.getOccupation() + " "
                + "Date from: " + employment2.getDateFrom() + " "
                + "Date to: " + employment2.getDateTo() + " "
                + "Financial Support" + employment2.getFinancialSupport());
        supplement.getAdditionalRecords().add(additionalRecord);

      }
    }
    if (beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo() != null) {
      EmploymentHistory employmentHistory = new EmploymentHistory();
      // Current Employer
      employmentHistory.setEmployerName(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getNameOfEmployer());
      employmentHistory.setOccupation(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getOccupation());
      Address address = new Address();
      address.setStreetNumberAndName(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getStreetNumberAndName());
      if (beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().isAptCheckbox()) {
        address.setAptCheckbox(checkboxProperties.getProperty(
            "background.employmentOrEducationHistories.address.aptCheckbox"));
      }
      if (beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().isSteCheckbox()) {
        address.setSteCheckbox(checkboxProperties.getProperty(
            "background.employmentOrEducationHistories.address.steCheckbox"));
      }
      if (beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().isFlrCheckbox()) {
        address.setFlrCheckbox(checkboxProperties.getProperty(
            "background.employmentOrEducationHistories.address.flrCheckbox"));
      }
      address.setAptSteFlrNumber(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getAptSteFlrNumber());
      address.setCityOrTown(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getCity());
      address.setCityOrTown(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getCity());
      address.setState(beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getState());
      address.setZipCode(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getZipCode());
      address.setProvince(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getProvince());
      address.setPostalCode(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getPostalCode());
      address.setCountry(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getCountry());
      address.setDateFrom(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getDateFrom());
      address.setDateTo(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getDateTo());
      employmentHistory.setAddress(address);
      employmentHistory.setFinancialSupport(
          beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo().getFinancialSupport());
      // Financial Support
      targetBackground.setMostRecentOutsideUS(employmentHistory);
    }
    return targetBackground;
  }

  private ParentInfo convertParentInfo(FamilyBasedCaseProfile familyBasedCaseProfile) {
    ParentInfo targetParentInfo = new ParentInfo();
    Family family = familyBasedCaseProfile.getBeneficiary().getFamily();
    if (family == null) {
      family = InstanceUtils.createInstanceWithDefaults(Family.class);
    }
    Parent father = new Parent();
    Parent mother = new Parent();
    com.quick.immi.ai.entity.familyBased.businss.Parent fa = family.getFather();
    if (fa == null) {
      fa = InstanceUtils.createInstanceWithDefaults(
          com.quick.immi.ai.entity.familyBased.businss.Parent.class);
    }
    com.quick.immi.ai.entity.familyBased.businss.Parent mo = family.getMother();
    if (mo == null) {
      mo = InstanceUtils.createInstanceWithDefaults(
          com.quick.immi.ai.entity.familyBased.businss.Parent.class);
    }
    father.setLastName(fa.getLastName());
    father.setFirstName(fa.getFirstName());
    father.setMiddleName(fa.getMiddleName());
    father.setBirthLastName(fa.getBirthLastName());
    father.setBirthFirstName(fa.getBirthFirstName());
    father.setBirthMiddleName(fa.getBirthMiddleName());
    father.setDateOfBirth(fa.getDateOfBirth());
    father.setCountryOfBirth(fa.getCountryOfBirth());
    mother.setLastName(mo.getLastName());
    mother.setFirstName(mo.getFirstName());
    mother.setMiddleName(mo.getMiddleName());
    mother.setBirthLastName(mo.getBirthLastName());
    mother.setBirthFirstName(mo.getBirthFirstName());
    mother.setBirthMiddleName(mo.getBirthMiddleName());
    mother.setDateOfBirth(mo.getDateOfBirth());
    mother.setCountryOfBirth(mo.getCountryOfBirth());
    targetParentInfo.setParents(new ArrayList<Parent>());
    targetParentInfo.getParents().add(father);
    targetParentInfo.getParents().add(mother);
    return targetParentInfo;
  }

  private MaritalHistory conveMaritalHistory(FamilyBasedCaseProfile familyBasedCaseProfile) {
    MaritalHistory targetMaritalHistory = new MaritalHistory();
    Beneficiary beneficiary = familyBasedCaseProfile.getBeneficiary();
    if (beneficiary == null) {
      beneficiary = InstanceUtils.createInstanceWithDefaults(Beneficiary.class);
    }
    CopyUtils.copyAllWithList(beneficiary.getMaritalInfo(), targetMaritalHistory, checkboxProperties, "maritalHistory");
    return targetMaritalHistory;
  }

  private ChildrenInfo convertChildrenInfo(FamilyBasedCaseProfile familyBasedCaseProfile) {
    ChildrenInfo targetChildrenInfo = new ChildrenInfo();
    Beneficiary beneficiary = familyBasedCaseProfile.getBeneficiary();
    if (beneficiary == null) {
      beneficiary = InstanceUtils.createInstanceWithDefaults(Beneficiary.class);
    }
    if (!beneficiary.getFamily().getChildren().isEmpty()) {
      int totalNumberOfChildren = beneficiary.getFamily().getChildren().size();
      targetChildrenInfo.setTotalNumberOfChildren(Integer.toString(totalNumberOfChildren));
      targetChildrenInfo.setChildren(new ArrayList<Child>());
      for (int i = 0; i < Math.min(totalNumberOfChildren, 2); i++) {
        Child child = new Child();
        child.setLastName(beneficiary.getFamily().getChildren().get(i).getLastName());
        child.setFirstName(beneficiary.getFamily().getChildren().get(i).getFirstName());
        child.setMiddleName(beneficiary.getFamily().getChildren().get(i).getMiddleName());
        child.setAlienNumber(beneficiary.getFamily().getChildren().get(i).getAlienNumber());
        child.setDateOfBirth(beneficiary.getFamily().getChildren().get(i).getDateOfBirth());
        child.setCountryOfBirth(beneficiary.getFamily().getChildren().get(i).getCountryOfBirth());
        child.setRelationship(beneficiary.getFamily().getChildren().get(i).getRelationship());
        if (beneficiary.getFamily().getChildren().get(i).isApplyingYesCheckbox()) {
          child.setApplyingYesCheckbox(checkboxProperties.getProperty(
              "childrenInfo.children." + Integer.toString(i) + ".applyingYesCheckbox"));
        }
        if (beneficiary.getFamily().getChildren().get(i).isApplyingNoCheckbox()) {
          child.setApplyingNoCheckbox(checkboxProperties.getProperty(
              "childrenInfo.children." + Integer.toString(i) + ".applyingNoCheckbox"));
        }
        targetChildrenInfo.getChildren().add(child);
      }
      if (totalNumberOfChildren > 2) {
        for (int i = 2; i < totalNumberOfChildren; i++) {
          Child child = new Child();
          child.setLastName(beneficiary.getFamily().getChildren().get(i).getLastName());
          child.setFirstName(beneficiary.getFamily().getChildren().get(i).getFirstName());
          child.setMiddleName(beneficiary.getFamily().getChildren().get(i).getMiddleName());
          child.setAlienNumber(beneficiary.getFamily().getChildren().get(i).getAlienNumber());
          child.setDateOfBirth(beneficiary.getFamily().getChildren().get(i).getDateOfBirth());
          child.setCountryOfBirth(beneficiary.getFamily().getChildren().get(i).getCountryOfBirth());
          child.setRelationship(beneficiary.getFamily().getChildren().get(i).getRelationship());
          AdditionalRecord additionalRecord = new AdditionalRecord();
          additionalRecord.setPageNumber("12");
          additionalRecord.setPartNumber("7");
          additionalRecord.setItemNumber("2");
          additionalRecord.setAdditionalInfo(
              "Child's Last Name: " + child.getLastName() + " " +
                  "Child's First Name: " + child.getFirstName() + " " +
                  "Child's Middle Name: " + child.getMiddleName() + " " +
                  "Child's Alien Number: " + child.getAlienNumber() + " " +
                  "Child's Date of Birth: " + child.getDateOfBirth() + " " +
                  "Child's Country of Birth: " + child.getCountryOfBirth() + "" +
                  "Child's Relationship to Me: " + child.getRelationship()
          );
          supplement.getAdditionalRecords().add(additionalRecord);
        }
      }
    }
    return targetChildrenInfo;
  }

  private Biographic convertBiographic(FamilyBasedCaseProfile familyBasedCaseProfile) {
    Biographic targetBiographic = new Biographic();
    Beneficiary beneficiary = familyBasedCaseProfile.getBeneficiary();
    if (beneficiary == null) {
      beneficiary = InstanceUtils.createInstanceWithDefaults(Beneficiary.class);
    }
    if (beneficiary.getBiographicInfo().isEthnicityHispanicCheckbox()) {
      targetBiographic.setEthnicityHispanicOrLatinoCheckbox(
          checkboxProperties.getProperty("biographic.ethnicityHispanicOrLatinoCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isEthnicityNotHispanicCheckbox()) {
      targetBiographic.setEthnicityNotHispanicOrLatinoCheckbox(
          checkboxProperties.getProperty("biographic.ethnicityNotHispanicOrLatinoCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isRaceWhiteCheckbox()) {
      targetBiographic.setRaceWhiteCheckbox(
          checkboxProperties.getProperty("biographic.raceWhiteCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isRaceAsianCheckbox()) {
      targetBiographic.setRaceAsianCheckbox(
          checkboxProperties.getProperty("biographic.raceAsianCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isRaceBlackCheckbox()) {
      targetBiographic.setRaceBlackOrAfricanAmericanCheckbox(
          checkboxProperties.getProperty("biographic.raceBlackOrAfricanAmericanCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isRaceAmericanIndianCheckbox()) {
      targetBiographic.setRaceAmericanIndianOrAlaskaNativeCheckbox(
          checkboxProperties.getProperty("biographic.raceAmericanIndianOrAlaskaNativeCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isRaceNativeHawaiianCheckbox()) {
      targetBiographic.setRaceNativeHawaiianOrPacificIslanderCheckbox(
          checkboxProperties.getProperty("biographic.raceNativeHawaiianOrPacificIslanderCheckbox"));
    }
    targetBiographic.setHeightFeetDropdown(beneficiary.getBiographicInfo().getHeightFeet());
    targetBiographic.setHeightInchesDropdown(beneficiary.getBiographicInfo().getHeightInches());
    targetBiographic.setWeightPound1(beneficiary.getBiographicInfo().getWeightPounds1());
    targetBiographic.setWeightPound2(beneficiary.getBiographicInfo().getWeightPounds2());
    targetBiographic.setWeightPound3(beneficiary.getBiographicInfo().getWeightPounds3());
    if (beneficiary.getBiographicInfo().isEyeColorBlackCheckbox()) {
      targetBiographic.setEyeColorBlackCheckbox(
          checkboxProperties.getProperty("biographic.eyeColorBlackCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isEyeColorBlueCheckbox()) {
      targetBiographic.setEyeColorBlueCheckbox(
          checkboxProperties.getProperty("biographic.eyeColorBlueCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isEyeColorBrownCheckbox()) {
      targetBiographic.setEyeColorBrownCheckbox(
          checkboxProperties.getProperty("biographic.eyeColorBrownCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isEyeColorGrayCheckbox()) {
      targetBiographic.setEyeColorGrayCheckbox(
          checkboxProperties.getProperty("biographic.eyeColorGrayCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isEyeColorGreenCheckbox()) {
      targetBiographic.setEyeColorGreenCheckbox(
          checkboxProperties.getProperty("biographic.eyeColorGreenCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isEyeColorHazelCheckbox()) {
      targetBiographic.setEyeColorHazelCheckbox(
          checkboxProperties.getProperty("biographic.eyeColorHazelCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isEyeColorMaroonCheckbox()) {
      targetBiographic.setEyeColorMaroonCheckbox(
          checkboxProperties.getProperty("biographic.eyeColorMaroonCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isEyeColorPinkCheckbox()) {
      targetBiographic.setEyeColorPinkCheckbox(
          checkboxProperties.getProperty("biographic.eyeColorPinkCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isEyeColorUnknownCheckbox()) {
      targetBiographic.setEyeColorUnknownOrOtherCheckbox(
          checkboxProperties.getProperty("biographic.eyeColorUnknownOrOtherCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isHairColorBaldCheckbox()) {
      targetBiographic.setHairColorBaldCheckbox(
          checkboxProperties.getProperty("biographic.hairColorBaldCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isHairColorBlackCheckbox()) {
      targetBiographic.setHairColorBlackCheckbox(
          checkboxProperties.getProperty("biographic.hairColorBlackCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isHairColorBlondCheckbox()) {
      targetBiographic.setHairColorBlondCheckbox(
          checkboxProperties.getProperty("biographic.hairColorBlondCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isHairColorBrownCheckbox()) {
      targetBiographic.setHairColorBrownCheckbox(
          checkboxProperties.getProperty("biographic.hairColorBrownCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isHairColorGrayCheckbox()) {
      targetBiographic.setHairColorGrayCheckbox(
          checkboxProperties.getProperty("biographic.hairColorGrayCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isHairColorRedCheckbox()) {
      targetBiographic.setHairColorRedCheckbox(
          checkboxProperties.getProperty("biographic.hairColorRedCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isHairColorSandyCheckbox()) {
      targetBiographic.setHairColorSandyCheckbox(
          checkboxProperties.getProperty("biographic.hairColorSandyCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isHairColorWhiteCheckbox()) {
      targetBiographic.setHairColorWhiteCheckbox(
          checkboxProperties.getProperty("biographic.hairColorWhiteCheckbox"));
    }
    if (beneficiary.getBiographicInfo().isHairColorUnknownCheckbox()) {
      targetBiographic.setHairColorUnknownOrOtherCheckbox(
          checkboxProperties.getProperty("biographic.hairColorUnknownOrOtherCheckbox"));
    }
    return targetBiographic;
  }

  private Eligibility convertEligibility(FamilyBasedCaseProfile familyBasedCaseProfile) {
    com.quick.immi.ai.entity.familyBased.businss.Eligibility eligibility = familyBasedCaseProfile.getBeneficiaryEligibility();
    if (eligibility == null) {
      eligibility = new com.quick.immi.ai.entity.familyBased.businss.Eligibility();
    }
    Eligibility targetEligibility = new Eligibility();
    CopyUtils.copyAllWithList(eligibility, targetEligibility, checkboxProperties, "eligibility");

    if (eligibility.getOrganizations().size() > 3) {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 3; i < eligibility.getOrganizations().size(); i++) {
        com.quick.immi.ai.entity.familyBased.businss.Organization organization = eligibility.getOrganizations()
            .get(i);
        String concatenatedOrganization = organization.getOrgName() +
            (organization.getCityTown() != null ? ", " + organization.getCityTown() : "") +
            (organization.getState() != null ? ", " + organization.getState() : "") +
            (organization.getCountry() != null ? ", " + organization.getCountry() : "") +
            (organization.getNatureOfGroup() != null ? ", Nature: "
                + organization.getNatureOfGroup() : "") +
            (organization.getDateFrom() != null ? ", From: " + organization.getDateFrom() : "") +
            (organization.getDateTo() != null ? ", To: " + organization.getDateTo() : "");

        // Append the concatenated organization info to the StringBuilder
        stringBuilder.append(concatenatedOrganization);
        stringBuilder.append("\n"); // Add a new line for each organization
      }
      AdditionalRecord newRecord = new AdditionalRecord();
      newRecord.setPageNumber("9"); // Set the appropriate page number
      newRecord.setPartNumber("8"); // Set the appropriate part number
      newRecord.setItemNumber("2-5"); // Set the appropriate item number
      newRecord.setAdditionalInfo(stringBuilder.toString()); // Add the concatenated organizations
      supplement.getAdditionalRecords().add(newRecord);
    }

    if (eligibility.getBenefitRecords().size() > 4) {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 4; i < eligibility.getBenefitRecords().size(); i++) {
        com.quick.immi.ai.entity.familyBased.businss.Benefit benefit = eligibility.getBenefitRecords()
            .get(i);
        String concatenatedBenefitInfo = benefit.getBenefitReceived() +
            (benefit.getStartDate() != null ? ", Start Date: " + benefit.getStartDate() : "") +
            (benefit.getEndDate() != null ? ", End Date: " + benefit.getEndDate() : "") +
            (benefit.getDollarAmount() != null ? ", Amount: $" + benefit.getDollarAmount() : "");

        stringBuilder.append(concatenatedBenefitInfo);
        stringBuilder.append("\n"); // Add a newline after each benefit
      }
      AdditionalRecord newRecord = new AdditionalRecord();
      Benefit targetBenefit = new Benefit();
      newRecord.setPageNumber(targetBenefit.getPageNumber()); // Set the appropriate page number
      newRecord.setPartNumber(targetBenefit.getPartNumber()); // Set the appropriate part number
      newRecord.setItemNumber(targetBenefit.getItemNumber()); // Set the appropriate item number
      newRecord.setAdditionalInfo(stringBuilder.toString()); // Add the concatenated benefit info
      supplement.getAdditionalRecords().add(newRecord); // Add the AdditionalRecord to the list
    }

    if (eligibility.getInstitutionalizationRecords().size() > 3) {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 3; i < eligibility.getInstitutionalizationRecords().size(); i++) {
        com.quick.immi.ai.entity.familyBased.businss.Institutionalization institutionalization = eligibility.getInstitutionalizationRecords()
            .get(i);

        String concatenatedInstitutionalizationInfo =
            institutionalization.getInstitutionNameCityState() +
                (institutionalization.getDateFrom() != null ? ", Date From: "
                    + institutionalization.getDateFrom() : "") +
                (institutionalization.getDateTo() != null ? ", Date To: "
                    + institutionalization.getDateTo() : "") +
                (institutionalization.getReason() != null ? ", Reason: "
                    + institutionalization.getReason() : "");
        stringBuilder.append(concatenatedInstitutionalizationInfo);
        stringBuilder.append("\n"); // Add a newline after each institutionalization record
      }
      AdditionalRecord newRecord = new AdditionalRecord();
      Institutionalization targetInstitutionalization = new Institutionalization();
      newRecord.setPageNumber(
          targetInstitutionalization.getPageNumber()); // Set the appropriate page number
      newRecord.setPartNumber(
          targetInstitutionalization.getPartNumber()); // Set the appropriate part number
      newRecord.setItemNumber(
          targetInstitutionalization.getItemNumber()); // Set the appropriate item number
      newRecord.setAdditionalInfo(
          stringBuilder.toString()); // Add the concatenated institutionalization info
      supplement.getAdditionalRecords().add(newRecord); // Add the AdditionalRecord to the list
    }

    return targetEligibility;
  }

  private ApplicantContact convertApplicantContact(FamilyBasedCaseProfile familyBasedCaseProfile) {
    ApplicantContact targetApplicantContact = new ApplicantContact();
    Beneficiary beneficiary = familyBasedCaseProfile.getBeneficiary();
    if (beneficiary == null) {
      beneficiary = InstanceUtils.createInstanceWithDefaults(Beneficiary.class);
    }
    targetApplicantContact.setApplicantDaytimePhoneNumber(beneficiary.getDaytimePhoneNumber());
    targetApplicantContact.setApplicantMobilePhoneNumber(beneficiary.getMobilePhoneNumber());
    targetApplicantContact.setApplicantEmailAddress(beneficiary.getEmailAddress());
    // Signature
    // Date of Signature
    return targetApplicantContact;
  }

  private Interpreter convertInterpreter() {
    Interpreter targetInterpreter = new Interpreter();
    return targetInterpreter;
  }

  private Preparer convertPreparer() {
    Preparer targetPreparer = new Preparer();
    return targetPreparer;
  }

  private Signature convertSignature() {
    Signature targetSignature = new Signature();
    return targetSignature;
  }
}
