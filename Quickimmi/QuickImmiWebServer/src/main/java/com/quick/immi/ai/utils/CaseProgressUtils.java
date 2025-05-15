/* (C) 2024 */
package com.quick.immi.ai.utils;

import com.beust.jcommander.internal.Lists;
import com.google.gson.Gson;
import com.quick.immi.ai.entity.ApplicationCaseProgress;
import com.quick.immi.ai.entity.CaseProgressStep;
import com.quick.immi.ai.entity.StepStatus;
import com.quick.immi.ai.entity.asylum.AsylumType;
import java.util.ArrayList;
import java.util.List;

public class CaseProgressUtils {

  public static String getCurrentStep(ApplicationCaseProgress asylumCaseProgress) {
    List<ApplicationCaseProgress.Step> steps = asylumCaseProgress.getSteps();
    for (ApplicationCaseProgress.Step step : steps) {
      if (step.getStatus() != StepStatus.COMPLETED) {
        return step.getName().getName();
      }
    }
    return steps.get(steps.size() - 1).getName().getName();
  }

  public static ApplicationCaseProgress initNonImmigrantVisasCaseProgress() {
    ApplicationCaseProgress.Step fillingApplicationStep =
        getStep(CaseProgressStep.FILLING_APPLICATION, Lists.newArrayList("FILLING_DETAILS"));

    ApplicationCaseProgress.Step reviewAndSignStep =
        getStep(
            CaseProgressStep.REVIEW_AND_SIGN,
            Lists.newArrayList("LAWYER_REVIEW", "CLIENT_SIGNATURE"));

    ApplicationCaseProgress.Step submitApplication =
        getStep(
            CaseProgressStep.SUBMIT_APPLICATION,
            Lists.newArrayList("SUBMIT_APPLICATION", "NOTICE_RECEIPT"));

    ApplicationCaseProgress.Step finalResultStep =
        getStep(CaseProgressStep.FINAL_RESULT, Lists.newArrayList("FINAL_REVIEW", "RESULT"));

    return ApplicationCaseProgress.builder()
        .steps(
            Lists.newArrayList(
                fillingApplicationStep, reviewAndSignStep, submitApplication, finalResultStep))
        .build();
  }

  public static ApplicationCaseProgress initEmploymentBasedCaseProgress() {
    ApplicationCaseProgress.Step fillingApplicationStep =
        getStep(CaseProgressStep.FILLING_APPLICATION, Lists.newArrayList("FILLING_DETAILS"));

    ApplicationCaseProgress.Step reviewAndSignStep =
        getStep(
            CaseProgressStep.REVIEW_AND_SIGN,
            Lists.newArrayList("LAWYER_REVIEW", "CLIENT_SIGNATURE"));

    ApplicationCaseProgress.Step submitApplication =
        getStep(
            CaseProgressStep.SUBMIT_APPLICATION,
            Lists.newArrayList("SUBMIT_APPLICATION", "NOTICE_RECEIPT"));

    ApplicationCaseProgress.Step finalResultStep =
        getStep(CaseProgressStep.FINAL_RESULT, Lists.newArrayList("FINAL_REVIEW", "RESULT"));

    return ApplicationCaseProgress.builder()
        .steps(
            Lists.newArrayList(
                fillingApplicationStep, reviewAndSignStep, submitApplication, finalResultStep))
        .build();
  }

  public static ApplicationCaseProgress initAsylumCaseProgress(AsylumType asylumType) {
    if (asylumType == AsylumType.AFFIRMATIVE) {
      return initAffirmativeAsylumCaseProgress();
    } else {
      return initDefensiveAsylumCaseProgress();
    }
  }

  public static ApplicationCaseProgress initAffirmativeAsylumCaseProgress() {
    ApplicationCaseProgress.Step fillingApplicationStep =
        getStep(CaseProgressStep.FILLING_APPLICATION, Lists.newArrayList("FILLING_DETAILS"));

    ApplicationCaseProgress.Step reviewAndSignStep =
        getStep(
            CaseProgressStep.REVIEW_AND_SIGN,
            Lists.newArrayList("LAWYER_REVIEW", "CLIENT_SIGNATURE"));

    ApplicationCaseProgress.Step submitApplication =
        getStep(
            CaseProgressStep.SUBMIT_APPLICATION,
            Lists.newArrayList("SUBMIT_APPLICATION", "NOTICE_RECEIPT"));

    ApplicationCaseProgress.Step fingerprintInterviewStep =
        getStep(
            CaseProgressStep.FINGERPRINT_INTERVIEW,
            Lists.newArrayList("FINGERPRINT_COLLECTION", "INTERVIEW"));

    ApplicationCaseProgress.Step finalResultStep =
        getStep(CaseProgressStep.FINAL_RESULT, Lists.newArrayList("FINAL_REVIEW", "RESULT"));

    ApplicationCaseProgress asylumCaseProgress =
        ApplicationCaseProgress.builder()
            .steps(
                Lists.newArrayList(
                    fillingApplicationStep,
                    reviewAndSignStep,
                    submitApplication,
                    fingerprintInterviewStep,
                    finalResultStep))
            .build();
    return asylumCaseProgress;
  }

  public static ApplicationCaseProgress initFamilyBasedCaseProgress() {
    ApplicationCaseProgress.Step fillingApplicationStep =
        getStep(CaseProgressStep.FILLING_APPLICATION, Lists.newArrayList("FILLING_DETAILS"));

    ApplicationCaseProgress.Step reviewAndSignStep =
        getStep(
            CaseProgressStep.REVIEW_AND_SIGN,
            Lists.newArrayList("LAWYER_REVIEW", "CLIENT_SIGNATURE"));

    ApplicationCaseProgress.Step submitApplication =
        getStep(
            CaseProgressStep.SUBMIT_APPLICATION,
            Lists.newArrayList("SUBMIT_APPLICATION", "NOTICE_RECEIPT"));

    ApplicationCaseProgress.Step fingerprintInterviewStep =
        getStep(
            CaseProgressStep.FINGERPRINT_INTERVIEW,
            Lists.newArrayList("FINGERPRINT_COLLECTION", "INTERVIEW"));

    ApplicationCaseProgress.Step finalResultStep =
        getStep(CaseProgressStep.FINAL_RESULT, Lists.newArrayList("FINAL_REVIEW", "RESULT"));

    ApplicationCaseProgress asylumCaseProgress =
        ApplicationCaseProgress.builder()
            .steps(
                Lists.newArrayList(
                    fillingApplicationStep,
                    reviewAndSignStep,
                    submitApplication,
                    fingerprintInterviewStep,
                    finalResultStep))
            .build();
    return asylumCaseProgress;
  }

  private static ApplicationCaseProgress initDefensiveAsylumCaseProgress() {
    ApplicationCaseProgress.Step fillingApplicationStep =
        getStep(CaseProgressStep.FILLING_APPLICATION, Lists.newArrayList("FILLING_DETAILS"));

    ApplicationCaseProgress.Step reviewAndSignStep =
        getStep(
            CaseProgressStep.REVIEW_AND_SIGN,
            Lists.newArrayList("LAWYER_REVIEW", "CLIENT_SIGNATURE"));

    ApplicationCaseProgress.Step submitApplication =
        getStep(
            CaseProgressStep.SUBMIT_APPLICATION,
            Lists.newArrayList("SUBMIT_APPLICATION", "NOTICE_RECEIPT"));

    ApplicationCaseProgress.Step fingerprintInterviewStep =
        getStep(
            CaseProgressStep.FINGERPRINT_INTERVIEW,
            Lists.newArrayList(
                "FINGERPRINT_COLLECTION", "MASTER_CALENDAR_HEARING", "INDIVIDUAL_HEARING"));

    ApplicationCaseProgress.Step finalResultStep =
        getStep(CaseProgressStep.FINAL_RESULT, Lists.newArrayList("FINAL_REVIEW", "RESULT"));

    ApplicationCaseProgress asylumCaseProgress =
        ApplicationCaseProgress.builder()
            .steps(
                Lists.newArrayList(
                    fillingApplicationStep,
                    reviewAndSignStep,
                    submitApplication,
                    fingerprintInterviewStep,
                    finalResultStep))
            .build();
    return asylumCaseProgress;
  }

  private static ApplicationCaseProgress.Step getStep(
      CaseProgressStep caseProgressStep, List<String> subStepNames) {
    List<ApplicationCaseProgress.Substep> substeps = new ArrayList<>();
    for (String name : subStepNames) {
      ApplicationCaseProgress.Substep substep =
          ApplicationCaseProgress.Substep.builder()
              .name(name)
              .status(StepStatus.NOT_START)
              .startedAt(System.currentTimeMillis())
              .updatedAt(System.currentTimeMillis())
              .build();
      substeps.add(substep);
    }

    ApplicationCaseProgress.Step step =
        ApplicationCaseProgress.Step.builder()
            .name(caseProgressStep)
            .status(StepStatus.NOT_START)
            .startedAt(System.currentTimeMillis())
            .updatedAt(System.currentTimeMillis())
            .substeps(substeps)
            .build();

    return step;
  }

  public static void main(String[] args) {
    System.out.println(new Gson().toJson(CaseProgressUtils.initAffirmativeAsylumCaseProgress()));
  }
}
