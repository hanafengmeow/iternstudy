/* (C) 2024 */
package com.quick.immi.ai.utils.asylum;

import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quick.immi.ai.entity.Language;
import com.quick.immi.ai.entity.asylum.*;
import com.quick.immi.ai.entity.sqs.PersonalStatementGenerationEvent;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsylumPersonalStatementUtils {

  public static PersonalStatementGenerationEvent createPersonalStatementInputData(
      Integer userId, Long caseId, AsylumCaseProfile profile) {
    Applicant applicant = profile.getApplicant();
    Family family = profile.getFamily();
    Background background = profile.getBackground();
    ApplicationDetails applicationDetails = profile.getApplicationDetails();

    return PersonalStatementGenerationEvent.builder()
        .documentType("personal_statement")
        .caseId(caseId)
        .userId(userId)
        .applicant(applicant)
        .spouse(family.getSpouse())
        .children(family.getChildren())
        .educationHistories(background.getEducationHistories())
        .employmentHistories(background.getEmploymentHistories())
        .entryRecords(applicant.getEntryRecords())
        .asylumBasedOnRaceCheckbox(applicationDetails.isAsylumBasedOnRaceCheckbox())
        .asylumBasedOnReligionCheckbox(applicationDetails.isAsylumBasedOnReligionCheckbox())
        .asylumBasedOnNationalityCheckbox(applicationDetails.isAsylumBasedOnNationalityCheckbox())
        .asylumBasedOnPoliticalOptionCheckbox(
            applicationDetails.isAsylumBasedOnPoliticalOptionCheckbox())
        .asylumBasedOnParticularMembershipCheckbox(
            applicationDetails.isAsylumBasedOnParticularMembershipCheckbox())
        .asylumBasedOnTortureConventionCheckbox(
            applicationDetails.isAsylumBasedOnTortureConventionCheckbox())
        .explainHaveBeenHarmedYes(applicationDetails.getExplainHaveBeenHarmedYes())
        .explainFearReturnYes(applicationDetails.getExplainFearReturnYes())
        .explainFamilyMembersBeenChargedYes(
            applicationDetails.getExplainFamilyMembersBeenChargedYes())
        .explainYouOrFamilyBelongAnyOrganizationYes(
            applicationDetails.getExplainYouOrFamilyBelongAnyOrganizationYes())
        .explainYouOrFamilyContinueBelongAnyOrganizationYes(
            applicationDetails.getExplainYouOrFamilyContinueBelongAnyOrganizationYes())
        .explainAfraidOfReturnYes(applicationDetails.getExplainAfraidOfReturnYes())
        .build();
  }

  public static PersonalStatementsInMultipleLanguages
      convertToPersonalStatementsInMultipleLanguagesJSON(
          final String personalStatementsInMultipleLanguagesStr) {
    if (StringUtils.isNullOrEmpty(personalStatementsInMultipleLanguagesStr)) {
      return PersonalStatementsInMultipleLanguages.builder().build();
    }
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(
          personalStatementsInMultipleLanguagesStr, PersonalStatementsInMultipleLanguages.class);
    } catch (JsonProcessingException e) {
      log.error(
          String.format(
              "Fail to convert the personalStatementsInMultipleLanguagesStr to Json: %s",
              personalStatementsInMultipleLanguagesStr),
          e);
      return PersonalStatementsInMultipleLanguages.builder().build();
    }
  }

  public static String getPersonalStatementForLanguage(
      PersonalStatementsInMultipleLanguages personalStatements, Language language) {
    List<PersonalStatement> statements = personalStatements.getPersonalStatements();
    System.out.println("statements: " + statements);
    if (statements == null) {
      log.warn("Warning: Personal statement not found. Language:" + language);
      return "";
    }
    for (PersonalStatement statement : statements) {
      if (statement.getLanguage() == language) {
        return statement.getContent();
      }
    }
    return "";
  }

  public static String getPersonalStatementForOriginalLanguage(
      PersonalStatementsInMultipleLanguages personalStatements) {
    // By default, the original language PS should be the version which is not english
    List<PersonalStatement> statements = personalStatements.getPersonalStatements();
    if (statements == null) {
      log.warn("Warning: Original language personal statement not found.");
      return "";
    }
    for (PersonalStatement statement : statements) {
      if (statement.getLanguage() != Language.ENGLISH) {
        return statement.getContent();
      }
    }

    // If original language statement (not English) is not found
    log.warn("Warning: Original language personal statement not found.");
    return "";
  }
}
