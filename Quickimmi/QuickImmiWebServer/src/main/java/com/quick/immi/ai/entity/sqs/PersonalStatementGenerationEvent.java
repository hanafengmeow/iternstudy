/* (C) 2024 */
package com.quick.immi.ai.entity.sqs;

import com.quick.immi.ai.entity.asylum.Applicant;
import com.quick.immi.ai.entity.asylum.Child;
import com.quick.immi.ai.entity.asylum.EducationHistory;
import com.quick.immi.ai.entity.asylum.EmploymentHistory;
import com.quick.immi.ai.entity.asylum.EntryRecord;
import com.quick.immi.ai.entity.asylum.Spouse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalStatementGenerationEvent {
  //  private String documentType;
  //  private Long caseId;
  //  private Integer userId;
  private Applicant applicant;
  private Spouse spouse;
  private List<Child> children;
  private List<EducationHistory> educationHistories;
  private List<EmploymentHistory> employmentHistories;
  private List<EntryRecord> entryRecords;

  private boolean asylumBasedOnRaceCheckbox;
  private boolean asylumBasedOnReligionCheckbox;
  private boolean asylumBasedOnNationalityCheckbox;
  private boolean asylumBasedOnPoliticalOptionCheckbox;
  private boolean asylumBasedOnParticularMembershipCheckbox;
  private boolean asylumBasedOnTortureConventionCheckbox;

  private String explainHaveBeenHarmedYes;
  private String explainFearReturnYes;
  private String explainFamilyMembersBeenChargedYes;
  private String explainYouOrFamilyBelongAnyOrganizationYes;
  private String explainYouOrFamilyContinueBelongAnyOrganizationYes;
  private String explainAfraidOfReturnYes;

  private String documentType;
  private Long caseId;
  private Integer userId;
  private String personalStatement;
  private String personalStatementInOriginalLanguage;
  private String language;
}
