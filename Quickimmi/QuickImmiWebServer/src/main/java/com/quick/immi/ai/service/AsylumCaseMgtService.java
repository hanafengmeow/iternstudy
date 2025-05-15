/* (C) 2024 */
package com.quick.immi.ai.service;

import static com.quick.immi.ai.constant.Constants.*;
import static com.quick.immi.ai.utils.asylum.AsylumFormGenerationTaskUtils.*;
import static com.quick.immi.ai.utils.asylum.AsylumPersonalStatementUtils.*;

import com.amazonaws.util.StringUtils;
import com.beust.jcommander.internal.Lists;
import com.google.gson.Gson;
import com.quick.immi.ai.dto.request.*;
import com.quick.immi.ai.dto.response.*;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.asylum.*;
import com.quick.immi.ai.entity.document.MarriageCertificate;
import com.quick.immi.ai.entity.sqs.CoverletterGenerationEvent;
import com.quick.immi.ai.entity.sqs.PersonalStatementGenerationEvent;
import com.quick.immi.ai.utils.*;
import com.quick.immi.ai.utils.asylum.AsylumFormGenerationTaskUtils;
import com.quick.immi.ai.utils.asylum.AsylumPersonalStatementUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsylumCaseMgtService extends CaseMgtBaseService {
  private static final CaseType CURRENT_CASE_TYPE = CaseType.Asylum;

  @Override
  public <T extends CreateCaseByCustomerRequestDto> Long createByCustomer(T createCaseRequestDto) {
    CreateAsylumCaseByCustomerRequestDto caseRequestDto =
        (CreateAsylumCaseByCustomerRequestDto) createCaseRequestDto;

    Customer customer = customerMgtService.get(caseRequestDto.getUserId());

    ApplicationCase applicationCase =
        ApplicationCase.builder()
            .status(CaseStatus.IN_PROGRESS.getValue())
            .progress(new Gson().toJson(CaseProgressUtils.initAffirmativeAsylumCaseProgress()))
            .currentStep(CaseProgressStep.FILLING_APPLICATION)
            .userId(caseRequestDto.getUserId())
            .email(customer.getEmail())
            .type(CaseType.Asylum.getValue())
            .createdBy(caseRequestDto.getUserId())
            .createdByLawyer(false)
            .createdAt(System.currentTimeMillis())
            .build();
    caseMapper.create(applicationCase);
    return applicationCase.getId();
  }

  @Override
  public <T extends CreateCaseByLawyerRequestDto> Long createByLawyer(T requestDto) {
    CreateAsylumCaseByLawyerRequestDto caseRequestDto =
        (CreateAsylumCaseByLawyerRequestDto) requestDto;
    Customer customer = customerMgtService.getByUsername(caseRequestDto.getProvidedCustomerEmail());
    if (customer == null) {
      customer =
          customerMgtService.createCustomerByEmail(caseRequestDto.getProvidedCustomerEmail());
    }

    String caseName =
        StringUtils.isNullOrEmpty(caseRequestDto.getCaseName())
            ? getDefaultCaseName(caseRequestDto.getApplicantName(), CURRENT_CASE_TYPE)
            : caseRequestDto.getCaseName();

    ApplicationCase applicationCase =
        ApplicationCase.builder()
            .applicantName(caseRequestDto.getApplicantName())
            .status(CaseStatus.IN_PROGRESS.getValue())
            .caseName(caseName)
            .progress(
                new Gson()
                    .toJson(
                        CaseProgressUtils.initAsylumCaseProgress(caseRequestDto.getAsylumType())))
            .currentStep(CaseProgressStep.FILLING_APPLICATION)
            .userId(customer.getId())
            .email(customer.getEmail())
            .type(CaseType.Asylum.getValue())
            .subType(caseRequestDto.getAsylumType().getValue())
            .assignedLawyer(caseRequestDto.getLawyerId())
            .createdBy(caseRequestDto.getLawyerId())
            .createdByLawyer(true)
            .createdAt(System.currentTimeMillis())
            .updatedAt(System.currentTimeMillis())
            .build();

    caseMapper.create(applicationCase);
    return applicationCase.getId();
  }

  @Override
  public <T extends CaseSummaryResponseDto> T getCaseSummary(Long id) {
    // Retrieve the application case
    ApplicationCase applicationCase = get(id);
    // Retrieve the profile from the application case
    String profile = applicationCase.getProfile();
    // Parse the profile to AsylumCaseProfile object
    AsylumCaseProfile caseProfile = new Gson().fromJson(profile, AsylumCaseProfile.class);

    String maritalStatus = getMaritalStatus(caseProfile);

    boolean applyWithSpouse = false;
    int numberOfApplyingChildren = 0;
    int numberOfChildren = 0;
    if (caseProfile != null && caseProfile.getFamily() != null) {
      // Check if applying with spouse
      applyWithSpouse =
          caseProfile.getFamily().getSpouse() != null
              && caseProfile.getFamily().getSpouse().isInThisApplicationYesCheckbox();

      // Count the number of applying children
      if (caseProfile.getFamily().getChildren() != null) {
        numberOfChildren = caseProfile.getFamily().getChildren().size();
        for (Child child : caseProfile.getFamily().getChildren()) {
          if (child != null && child.isInThisApplicationYesCheckbox()) {
            numberOfApplyingChildren++;
          }
        }
      }
    }

    return (T)
        AsylumCaseSummaryResponseDto.builder()
            .id(applicationCase.getId())
            .caseName(applicationCase.getCaseName())
            .applicantName(applicationCase.getApplicantName())
            .caseType(CaseType.fromValue(applicationCase.getType()))
            .currentStep(applicationCase.getCurrentStep())
            .progress(
                new Gson().fromJson(applicationCase.getProgress(), ApplicationCaseProgress.class))
            .asylumType(AsylumType.fromValue(applicationCase.getSubType()))
            .maritalStatus(maritalStatus)
            .applyWithSpouse(applyWithSpouse)
            .numberOfChildren(numberOfChildren)
            .numberOfApplyingChildren(numberOfApplyingChildren)
            .updatedAt(applicationCase.getUpdatedAt())
            .createdAt(applicationCase.getCreatedAt())
            .build();
  }

  @NotNull
  private String getMaritalStatus(AsylumCaseProfile caseProfile) {
    // Determine marital status
    String maritalStatus = "";
    if (caseProfile != null && caseProfile.getApplicant() != null) {
      if (caseProfile.getApplicant().isMaritalStatusSingleCheckbox()) {
        maritalStatus = "Single";
      } else if (caseProfile.getApplicant().isMaritalStatusMarriedCheckbox()) {
        maritalStatus = "Married";
      } else if (caseProfile.getApplicant().isMaritalStatusDivorcedCheckbox()) {
        maritalStatus = "Divorced";
      } else if (caseProfile.getApplicant().isMaritalStatusWidowedCheckbox()) {
        maritalStatus = "Widowed";
      }
    }
    return maritalStatus;
  }

  @Override
  public EligibilityCheckResultDto checkEligibilityForDocumentGeneration(Long caseId) {
    EligibilityCheckResultDto result = new EligibilityCheckResultDto();
    result.setStatus(true);
    List<String> missingFields = new ArrayList<>();

    ApplicationCase applicationCase = get(caseId);
    AsylumCaseProfile profile =
        new Gson().fromJson(applicationCase.getProfile(), AsylumCaseProfile.class);
    Boolean married = !profile.getFamily().getSpouse().isNotMarriedCheckbox();
    Lawyer lawyer = getLawyer(applicationCase.getAssignedLawyer());

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
      if (spouse != null && child.isInThisApplicationYesCheckbox()) {
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

    CoverletterGenerationEvent event =
        CoverletterGenerationEvent.builder()
            .caseId(applicationCase.getId())
            .userId(applicationCase.getUserId())
            .personalStatement(personalStatement)
            .personalStatementInOriginalLanguage(personalStatementInOriginalLanguage)
            .client(client)
            .documents(documents)
            .attorney(attorney)
            .caseInfo(caseInfo)
            .marriageCertificate(marriageCertificateDto)
            .shipping_method("USPS")
            .build();

    //        String eventStr = new Gson().toJson(event);
    //        System.out.println("event: " + eventStr);
    if (event.getPersonalStatement() == null || event.getPersonalStatement().isEmpty()) {
      missingFields.add("personalStatement");
      result.setStatus(false);
    }

    if (event.getPersonalStatementInOriginalLanguage() == null
        || event.getPersonalStatementInOriginalLanguage().isEmpty()) {
      missingFields.add("personalStatementInOriginalLanguage");
      result.setStatus(false);
    }

    if (married
        && (marriageCertificate == null
            || marriageCertificate.getLicenseHolder() == null
            || marriageCertificate.getLicenseHolder().isEmpty())) {
      missingFields.add("marriageCertificate");
      result.setStatus(false);
    }

    Optional<Document> existingPassport =
        documentService.getExistDocument(
            caseId, DocumentType.PASSPORT_MAIN.getName(), Identify.Applicant);
    if (existingPassport.isEmpty()) {
      missingFields.add(DocumentType.PASSPORT_MAIN.getName());
      result.setStatus(false);
    }

    Optional<Document> existingPassportStamp =
        documentService.getExistDocument(
            caseId, DocumentType.PASSPORT_STAMP_PAGES.getName(), Identify.Applicant);
    if (existingPassport.isEmpty()) {
      missingFields.add(DocumentType.PASSPORT_STAMP_PAGES.getName());
      result.setStatus(false);
    }

    result.setMissingFields(missingFields);
    return result;
  }

  @Override
  public EligibilityCheckResultDto checkEligibilityForDocumentMerge(Long caseId) {
    EligibilityCheckResultDto result = new EligibilityCheckResultDto();
    result.setStatus(true);
    List<String> missingFields = new ArrayList<>();

    ApplicationCase applicationCase = get(caseId);
    AsylumCaseProfile profile =
        new Gson().fromJson(applicationCase.getProfile(), AsylumCaseProfile.class);

    Boolean married = !profile.getApplicant().isNotMarriedCheckbox();

    List<DocumentType> requiredDocuments =
        new ArrayList<>(
            List.of(
                DocumentType.I589, DocumentType.CERTIFICATE_OF_TRANSLATION_FOR_PERSONAL_STATEMENT));

    if (married) {
      requiredDocuments.add(DocumentType.CERTIFICATE_OF_TRANSLATION_FOR_MARRIAGE_CERTIFICATE);
      requiredDocuments.add(DocumentType.MARRIAGE_CERTIFICATE_ENGLISH);
    }

    if (applicationCase.getSubType().equals(AsylumType.DEFENSIVE.getValue())) {
      requiredDocuments.add(DocumentType.EOIR28);
      requiredDocuments.add(DocumentType.ASYLUM_PLEADING);
      requiredDocuments.addAll(SUPPORTED_DEFENSIVE_COVER_LETTER_DOCUMENTS);
    }

    if (applicationCase.getSubType().equals(AsylumType.AFFIRMATIVE)) {
      requiredDocuments.add(DocumentType.G28);
      requiredDocuments.addAll(SUPPORTED_AFFIRMATIVE_COVER_LETTER_DOCUMENTS);
    }

    for (DocumentType documentType : requiredDocuments) {
      Optional<Document> existingDocument =
          documentService.getExistDocument(caseId, documentType.getName(), Identify.Applicant);
      if (existingDocument.isEmpty()
          || !(existingDocument.get().getStatus().equals("Success")
              || existingDocument.get().getStatus().equals("Skipped"))) {
        missingFields.add(documentType.getName());
        result.setStatus(false);
      }
    }
    result.setMissingFields(missingFields);
    return result;
  }

  public TriggerDataCrawlingResultDto triggerEOIRCrawlingJob(Long caseId, String aNumber) {
    ApplicationCase applicationCase = get(caseId);
    Long crawlHearingTaskId =
        AsylumFormGenerationTaskUtils.generateCrawlerTask(
            applicationCase.getUserId(),
            caseId,
            aNumber,
            DataCrawlingType.EOIR_HEARING_DETAILS.getName(),
            sqsService,
            formGenerationTaskMapper);
    return TriggerDataCrawlingResultDto.builder().taskId(crawlHearingTaskId).build();
  }

  @Override
  public void generateDocumentsByDocumentType(Long caseId, DocumentType documentType) {
    // prepare basic data
    ApplicationCase applicationCase = get(caseId);
    AsylumType asylumType = AsylumType.fromValue(applicationCase.getSubType());
    AsylumCaseProfile profile =
        new Gson().fromJson(applicationCase.getProfile(), AsylumCaseProfile.class);
    Lawyer assignedLawyer = getLawyer(applicationCase.getAssignedLawyer());
    boolean married = !profile.getApplicant().isNotMarriedCheckbox();
    boolean spouseIncluded = profile.getFamily().getSpouse().isInThisApplicationYesCheckbox();
    Integer childrenCount = profile.getFamily().getChildren().size();
    List<Integer> childrenIncluded = new ArrayList<>();
    for (int i = 0; i < childrenCount; i++) {
      Child child = profile.getFamily().getChildren().get(i);
      if (child != null && child.isInThisApplicationYesCheckbox()) {
        childrenIncluded.add(i + 1);
      }
    }

    // create document generation tasks
    // 1. main form table generation --- the table provided by USCIS
    if (MAIN_FROM_SET.contains(documentType)) {
      buildMainFormGenerationTaskAndSendToSQS(
          applicationCase.getUserId(),
          applicationCase.getId(),
          documentType,
          assignedLawyer,
          sqsService,
          formGenerationTaskMapper,
          documentService,
          false);
    } else if (AsylumFormGenerationTaskUtils.isSupplementForm(documentType)) {
      buildSupplementFormGenerationTaskAndSendToSQS(
          applicationCase.getUserId(),
          applicationCase.getId(),
          documentType,
          profile,
          assignedLawyer,
          sqsService,
          formGenerationTaskMapper,
          documentService,
          false);
    } else if (documentType == DocumentType.ALL) {
      buildAllFormGenerationTaskAndSendToSQS(
          applicationCase.getUserId(),
          applicationCase.getId(),
          married,
          spouseIncluded,
          childrenCount,
          childrenIncluded,
          asylumType,
          profile,
          assignedLawyer,
          sqsService,
          formGenerationTaskMapper,
          documentService);
    } else {
      throw new RuntimeException("not supported formType: " + documentType);
    }
  }

  private String translateProfile(String profile) {
    StringBuilder builder = new StringBuilder();
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(
                getClass()
                    .getClassLoader()
                    .getResourceAsStream("prompts/PromptTranslateProfile.txt"),
                "UTF-8"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line).append("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return openAIService.invokeWithPrompt(builder.toString(), profile);
  }

  public String generatePersonalStatement(Long caseId, Language language) {
    ApplicationCase applicationCase = get(caseId);
    if (language == Language.ENGLISH) {

      PersonalStatementGenerationEvent personalStatementInputData =
          AsylumPersonalStatementUtils.createPersonalStatementInputData(
              applicationCase.getUserId(),
              applicationCase.getId(),
              new Gson().fromJson(applicationCase.getProfile(), AsylumCaseProfile.class));

      String prompt = FileUtils.getContent("prompts/PromptAsylumPersonalStatement.txt");
      return openAIService.invokeWithPrompt(prompt, new Gson().toJson(personalStatementInputData));
    } else {
      /*
       * TODO: translation is moving to the translatePersonalStatementToOriginalLanguage API.
       * This logic should not be used now.
       * Update this function to generate PS based on the input data and the target language.
       */
      String EnglishPersonalStatement = getPersonalStatement(caseId, Language.ENGLISH);
      return translatePersonalStatementToOriginalLanguage(EnglishPersonalStatement, language);
    }
  }

  public RefinePSResponseDto generatePSWithAIForCase(Long caseId, Language originalLanguage) {
    ApplicationCase applicationCase = get(caseId);
    PersonalStatementGenerationEvent personalStatementInputData =
        AsylumPersonalStatementUtils.createPersonalStatementInputData(
            applicationCase.getUserId(),
            applicationCase.getId(),
            new Gson().fromJson(applicationCase.getProfile(), AsylumCaseProfile.class));

    String prompt = FileUtils.getContent("prompts/PromptAsylumPersonalStatement.txt");
    String promptWithLanguage = String.format(prompt, originalLanguage);
    String openAIResponse =
        openAIService.invokeWithPrompt(
            promptWithLanguage, new Gson().toJson(personalStatementInputData));

    RefinePSResponseDto generatePSResponse =
        new Gson().fromJson(openAIResponse, RefinePSResponseDto.class);

    return generatePSResponse;
  }

  public RefinePSResponseDto refinePSWithPrompt(RefinePSRequestDto refinePSRequestDto) {
    String defaultPrompt = FileUtils.getContent("prompts/PromptRefinePersonalStatement.txt");
    String inputPrompt = refinePSRequestDto.getPrompt();
    Language originalLanguage = refinePSRequestDto.getOriginalLanguage();
    String promptWithLanguage = String.format(defaultPrompt, inputPrompt, originalLanguage);

    String englishPS = refinePSRequestDto.getEnglishPS();
    String openAIResponse = openAIService.invokeWithPrompt(promptWithLanguage, englishPS);

    RefinePSResponseDto refinePSResponse =
        new Gson().fromJson(openAIResponse, RefinePSResponseDto.class);

    return refinePSResponse;
  }

  public RefinePSResponseDto translatePersonalStatementToEnglishAndOriginalLanguage(
      String content, Language OriginalLanguage) {
    String prompt =
        FileUtils.getContent(
            "prompts/PromptTranslatePersonalStatementToEnglish&OriginalLanguage.txt");
    String promptWithLanguage = String.format(prompt, OriginalLanguage);

    String openAIResponse =
        openAIService.invokeWithPrompt(promptWithLanguage, new Gson().toJson(content));
    RefinePSResponseDto translatePSResponse =
        new Gson().fromJson(openAIResponse, RefinePSResponseDto.class);

    return translatePSResponse;
  }

  public String translatePersonalStatementToOriginalLanguage(
      String content, Language targetLanguage) {
    String prompt =
        FileUtils.getContent("prompts/PromptTranslatePersonalStatementToOriginalLanguage.txt");
    String promptWithLanguage = String.format(prompt, targetLanguage);
    return openAIService.invokeWithPrompt(promptWithLanguage, new Gson().toJson(content));
  }

  public String getPersonalStatement(Long caseId, Language language) {
    ApplicationCase applicationCase = get(caseId);
    AsylumCaseProfile caseProfile =
        new Gson().fromJson(applicationCase.getProfile(), AsylumCaseProfile.class);
    SupplementDocument supplementDocument = caseProfile.getSupplementDocument();
    if (supplementDocument == null) {
      return "";
    }
    PersonalStatementsInMultipleLanguages personalStatementsInMultipleLanguages =
        convertToPersonalStatementsInMultipleLanguagesJSON(
            supplementDocument.getPersonalStatement());
    return getPersonalStatementForLanguage(personalStatementsInMultipleLanguages, language);
  }

  public void update(AsylumApplicationCaseDto applicationCaseDto) {
    sortApplicationInfo(applicationCaseDto);
    cacheService.saveApplicationCase(applicationCaseDto.getId(), applicationCaseDto);
    String caseProfile = new Gson().toJson(applicationCaseDto.getProfile());
    ApplicationCase applicationCase =
        ApplicationCase.builder()
            .id(applicationCaseDto.getId())
            .currentStep(applicationCaseDto.getCurrentStep())
            .progress(new Gson().toJson(applicationCaseDto.getProgress()))
            .status(applicationCaseDto.getStatus())
            .profile(caseProfile)
            .updatedAt(System.currentTimeMillis())
            .build();
    caseMapper.update(applicationCase);
    this.update(applicationCase);
    log.debug(
        String.format(
            "update caseId %s successfully given caseProfile",
            caseProfile, applicationCaseDto.getId()));
  }

  private void sortApplicationInfo(AsylumApplicationCaseDto applicationCaseDto) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

    if (applicationCaseDto.getProfile() != null
        && applicationCaseDto.getProfile().getBackground() != null) {
      Background background = applicationCaseDto.getProfile().getBackground();

      Comparator<String> dateComparator =
          (date1, date2) -> {
            LocalDate d1 = parseDate(date1, formatter);
            LocalDate d2 = parseDate(date2, formatter);
            return d2.compareTo(d1); // Reverse order to get the most recent date first
          };

      if (background.getAddressHistoriesBeforeUS() != null) {
        background
            .getAddressHistoriesBeforeUS()
            .sort(Comparator.comparing(AddressHistory::getEndDate, dateComparator));
      }

      if (background.getUsAddressHistoriesPast5Years() != null) {
        background
            .getUsAddressHistoriesPast5Years()
            .sort(Comparator.comparing(AddressHistory::getEndDate, dateComparator));
      }

      if (background.getEducationHistories() != null) {
        background
            .getEducationHistories()
            .sort(Comparator.comparing(EducationHistory::getEndDate, dateComparator));
      }

      if (background.getEmploymentHistories() != null) {
        background
            .getEmploymentHistories()
            .sort(Comparator.comparing(EmploymentHistory::getEndDate, dateComparator));
      }
    }
  }

  private LocalDate parseDate(String date, DateTimeFormatter formatter) {
    try {
      return LocalDate.parse(date, formatter);
    } catch (DateTimeParseException e) {
      // Handle invalid date format by returning a very old date
      return LocalDate.MIN;
    }
  }

  public String refine(RefineRequestDto refineRequestDto) {
    String promptFilePath =
        "prompts/PromptAsylumQuestionsRefine/PromptAsylumQuestionsRefine"
            + refineRequestDto.getQuestion()
            + ".txt";
    System.out.println("promptFilePath: " + promptFilePath);
    String prompt = FileUtils.getContent(promptFilePath);
    return openAIService.invokeWithPrompt(prompt, refineRequestDto.getContent());
  }

  public RefineResponseDto refineWithPrompt(RefineRequestDto refineRequestDto) {
    String promptFilePath =
        "prompts/PromptAsylumQuestionsRefine/PromptAsylumQuestionsRefine"
            + refineRequestDto.getQuestion()
            + ".txt";
    log.info(promptFilePath);
    String defaultPrompt = FileUtils.getContent(promptFilePath);
    String inputPrompt = refineRequestDto.getPrompt();
    String finalPrompt =
        String.format(
            "%s\n%s\nPlease provide the response in the following JSON format:\n{ result: \"\", tips: [\"\", \"\", \"\"] }",
            defaultPrompt, inputPrompt);
    String openAIResponse =
        openAIService.invokeWithPrompt(finalPrompt, refineRequestDto.getContent());

    RefineResponseDto responseDto = new Gson().fromJson(openAIResponse, RefineResponseDto.class);
    return responseDto;
  }

  public String generateCoverLetter(Long caseId) {
    String ps = getPersonalStatement(caseId, Language.ENGLISH);
    String prompt = FileUtils.getContent("prompts/PromptAsylumCoverLetter.txt");
    return openAIService.invokeWithPrompt(prompt, ps);
  }
}
