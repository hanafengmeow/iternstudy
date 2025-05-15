/* (C) 2024 */
package com.quick.immi.ai.utils.asylum;

import static com.quick.immi.ai.utils.asylum.AsylumPersonalStatementUtils.*;

import com.beust.jcommander.internal.Lists;
import com.google.gson.Gson;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.asylum.*;
import com.quick.immi.ai.entity.document.MarriageCertificate;
import com.quick.immi.ai.entity.sqs.CoverletterGenerationEvent;
import java.util.List;

public class AsylumCoverLetterUtils {

  public static CoverletterGenerationEvent createCoverLetterGenerationEvent(
      Integer userId, Long caseId, AsylumCaseProfile profile, Lawyer lawyer, String documentType) {
    Applicant applicant = profile.getApplicant();
    Family family = profile.getFamily();
    Spouse spouse = null;
    List<Child> children = Lists.newArrayList();
    if (family != null) {
      spouse = family.getSpouse();
      children = family.getChildren();
    }
    List<String> additionalApplicants = Lists.newArrayList();
    List<String> additionalApplicantAlienNumber = Lists.newArrayList();
    if (spouse != null && spouse.isInThisApplicationYesCheckbox()) {
      additionalApplicants.add(spouse.getFirstName() + " " + spouse.getLastName());
      additionalApplicantAlienNumber.add(spouse.getAlienNumber());
    }
    for (Child child : children) {
      if (child != null && child.isInThisApplicationYesCheckbox()) {
        additionalApplicants.add(child.getFirstName() + " " + child.getLastName());
        additionalApplicantAlienNumber.add(child.getAlienNumber());
      }
    }

    String personalStatement = "";
    String personalStatementInOriginalLanguage = "";
    if (profile.getSupplementDocument() != null) {
      String personalStatementsInMultipleLanguagesStr =
          profile.getSupplementDocument().getPersonalStatement();
      PersonalStatementsInMultipleLanguages personalStatementsInMultipleLanguages =
          convertToPersonalStatementsInMultipleLanguagesJSON(
              personalStatementsInMultipleLanguagesStr);
      System.out.println(
          "personalStatementsInMultipleLanguages: " + personalStatementsInMultipleLanguages);
      personalStatement =
          getPersonalStatementForLanguage(personalStatementsInMultipleLanguages, Language.ENGLISH);
      personalStatementInOriginalLanguage =
          getPersonalStatementForOriginalLanguage(personalStatementsInMultipleLanguages);
    }

    String coverLetter = "";
    if (profile.getSupplementDocument() != null) {
      coverLetter = profile.getSupplementDocument().getCoverLetter();
    }

    LawyerProfile lawyerProfile = new Gson().fromJson(lawyer.getProfile(), LawyerProfile.class);
    LawyerBasicInfo lawyerBasicInfo = lawyerProfile.getBasicInfo();
    LawyerEligibility lawyerEligibility = lawyerProfile.getEligibility();
    String passportIssueCountry = applicant.getPassportIssueCountry();
    String gender = "";
    if (applicant.isGenderFemaleCheckbox()) {
      gender = "Female";
    } else if (applicant.isGenderMaleCheckbox()) {
      gender = "Male";
    }
    String aNumber = applicant.getAlienNumber();
    CoverletterGenerationEvent.Client client =
        CoverletterGenerationEvent.Client.builder()
            .firstName(applicant.getFirstName())
            .dateOfBirth(applicant.getBirthDate())
            .lastName(applicant.getLastName())
            .gender(gender)
            .alienNumber(aNumber)
            .additionalApplicants(String.join(", ", additionalApplicants))
            .additionalApplicantAlienNumber(String.join(", ", additionalApplicantAlienNumber))
            .personalStatement(personalStatement)
            //                .caseSummary("I, Tianjin, China, in March 2021, I was forcibly taken
            // into custody by the Public Security Bureau for participating in a Christian
            // gathering. While I was organizing a group activity within ym own factory, I was
            // detained unlawfully for 2 hours. During this time, I was subjected to torture and
            // interrogation. Due to the lack of evidence for any charges, I was eventually released
            // and allowed to return home. However, as a result of this detention, my factory was
            // forcibly shut down, and the assets within my company were all damaged.")
            .residential_address(
                applicant.getStreetNumberAndName()
                    + "  "
                    + applicant.getCity()
                    + ", "
                    + applicant.getState()
                    + " "
                    + applicant.getZipCode())
            .countryOfOrigin(passportIssueCountry)
            .build();

    CoverletterGenerationEvent.Attorney attorney =
        CoverletterGenerationEvent.Attorney.builder()
            .firstName(lawyer.getFirstName())
            .lastName(lawyer.getLastName())
            .companyName(lawyer.getLawFirm())
            .attorneyBarNo(lawyerEligibility.getBarNumber())
            .attorneyBarState(
                lawyerBasicInfo.getStateDropdown()) // need to change to the state of the hearing
            .phone(lawyer.getPhoneNumber())
            .email(lawyer.getEmail())
            .address(
                lawyerBasicInfo.getAptSteFlrNumber()
                    + " "
                    + lawyerBasicInfo.getStreetNumberAndName())
            .city(lawyerBasicInfo.getCity())
            .state(lawyerBasicInfo.getStateDropdown())
            .zip(lawyerBasicInfo.getZipCode())
            .build();

    SupplementDocument supplementDocument = profile.getSupplementDocument();

    MasterHearingDetail masterHearingDetail = null;
    if (supplementDocument != null && supplementDocument.getMasterHearingDetail() != null) {
      masterHearingDetail = supplementDocument.getMasterHearingDetail();
    }
    CoverletterGenerationEvent.CaseInfo caseInfo = new CoverletterGenerationEvent.CaseInfo();
    if (masterHearingDetail != null) {
      caseInfo =
          CoverletterGenerationEvent.CaseInfo.builder()
              .courtAddress(masterHearingDetail.getCourtAddress())
              .courtCity(masterHearingDetail.getCourtCity())
              .courtState(masterHearingDetail.getCourtState())
              .courtZip(masterHearingDetail.getCourtZipCode())
              .judgeName(masterHearingDetail.getJudgeName())
              .hearingDate(masterHearingDetail.getHearingDate())
              .hearingTime(masterHearingDetail.getHearingTime())
              .hearingType(masterHearingDetail.getHearingType())
              .hearingMedium(masterHearingDetail.getHearingMedium())
              .build();
    }

    MarriageCertificate marriageCertificate = null;
    if (supplementDocument != null && supplementDocument.getMarriageCertificate() != null) {
      marriageCertificate = supplementDocument.getMarriageCertificate();
    }

    CoverletterGenerationEvent.MarriageCertificate marriageCertificateDto =
        new CoverletterGenerationEvent.MarriageCertificate();
    if (marriageCertificate != null) {
      marriageCertificateDto =
          CoverletterGenerationEvent.MarriageCertificate.builder()
              .licenseHolder(marriageCertificate.getLicenseHolder())
              .registrationDate(marriageCertificate.getRegistrationDate())
              .licenseNumber(marriageCertificate.getLicenseNumber())
              .holderName(marriageCertificate.getHolderName())
              .gender(marriageCertificate.getGender())
              .nationality(marriageCertificate.getNationality())
              .birthDate(marriageCertificate.getBirthDate())
              .idNumber(marriageCertificate.getIdNumber())
              .spouseName(marriageCertificate.getSpouseName())
              .spouseGender(marriageCertificate.getSpouseGender())
              .spouseNationality(marriageCertificate.getSpouseNationality())
              .spouseBirthDate(marriageCertificate.getSpouseBirthDate())
              .spouseIdNumber(marriageCertificate.getSpouseIdNumber())
              .seal(marriageCertificate.getSeal())
              .build();
    }

    List<String> documents =
        Lists.newArrayList("Ex.1 Attorney Cover Letter", "Ex.2 Form G-28", "Ex.3 1-589");
    String documentTypeMessage = "";
    String document = "";
    if (documentType.equals(DocumentType.COVER_LETTER_FOR_AFFIRMATIVE_ASYLUM.getName())) {
      documentTypeMessage = documentType;
    } else if (documentType.endsWith("i589_form")) {
      String[] parts = documentType.split("_");
      documentTypeMessage = parts[0] + "_" + parts[1];
      ;
      document = "I-589 Form";
    } else if (documentType.endsWith("personal_statement")) {
      String[] parts = documentType.split("_");
      documentTypeMessage = parts[0] + "_" + parts[1];
      ;
      document = "Personal Statement";
    } else if (documentType.endsWith("written_pleading")) {
      String[] parts = documentType.split("_");
      documentTypeMessage = parts[0] + "_" + parts[1];
      ;
      document = "Written Pleading";
    } else if (documentType.endsWith("supporting_documents")) {
      String[] parts = documentType.split("_");
      documentTypeMessage = parts[0] + "_" + parts[1];
      ;
      document = "Supporting Documents";
    }
    if (documentType.equals(DocumentType.MARRIAGE_CERTIFICATE_ENGLISH.getName())) {
      documentTypeMessage = "marriage_license_english";
    }
    String language = "English";
    if (documentType.equals(DocumentType.PERSONAL_STATEMENT_ENGLISH.getName())) {
      documentTypeMessage = "personal_statement";
    }
    if (documentType.equals(DocumentType.PERSONAL_STATEMENT_ORIGINAL.getName())) {
      documentTypeMessage = "personal_statement";
      language = "Original";
    }
    if (documentType.equals(
        DocumentType.CERTIFICATE_OF_TRANSLATION_FOR_MARRIAGE_CERTIFICATE.getName())) {
      documentTypeMessage = "certificate_of_translation";
      document = "marriage_certificate";
      language = "Chinese";
    }
    if (documentType.equals(
        DocumentType.CERTIFICATE_OF_TRANSLATION_FOR_PERSONAL_STATEMENT.getName())) {
      documentTypeMessage = "certificate_of_translation";
      document = "personal_statement";
      language = "Chinese";
    }

    CoverletterGenerationEvent event =
        CoverletterGenerationEvent.builder()
            .documentName(documentType)
            .documentType(documentTypeMessage)
            .document(document)
            .caseId(caseId)
            .userId(userId)
            .personalStatement(personalStatement)
            .personalStatementInOriginalLanguage(personalStatementInOriginalLanguage)
            .coverLetter(coverLetter)
            .client(client)
            .documents(documents)
            .attorney(attorney)
            .caseInfo(caseInfo)
            .language(language)
            .marriageCertificate(marriageCertificateDto)
            .shipping_method("USPS")
            .build();

    String eventStr = new Gson().toJson(event);
    System.out.println("event: " + eventStr);
    return event;
  }
}
