/* (C) 2024 */
package com.quick.immi.ai.entity.asylum;

import com.quick.immi.ai.entity.MasterHearingDetail;
import com.quick.immi.ai.entity.document.MarriageCertificate;
import java.util.List;
import lombok.Data;

@Data
public class SupplementDocument {
  /*
   * personalStatement is a string for a json and the json will follow this schema:
   * {
   *     "personalStatements": [
   *       {
   *         "language": "english",
   *         "content": "My name is Brandon"
   *       },
   *       {
   *         "language": "chinese",
   *         "content": "我的名字叫布兰登"
   *       }
   *     ]
   *   }
   */
  private String personalStatement;
  private String coverLetter;

  private List<Long> explainHaveBeenHarmedYesSupportDocuments;
  private List<Long> explainFearReturnYesSupportDocuments;
  private List<Long> explainFamilyMembersBeenChargedYesSupportDocuments;
  private List<Long> explainYouOrFamilyContinueBelongAnyOrganizationYesSupportDocuments;
  private List<Long> explainAfraidOfReturnYesSupportDocuments;
  private List<Long> explainAppliedBeforeYesSupportDocuments;
  private List<Long> explainAnyLawfulStatusAnyCountryYesSupportDocuments;
  private List<Long> explainHaveYouHarmOthersYesSupportDocuments;
  private List<Long> explainYouOrFamilyBelongAnyOrganizationYesSupportDocuments;
  private List<Long> explainReturnCountryYesSupportDocuments;
  private List<Long> explainMoreThanOneYearAfterArrivalYesSupportDocuments;
  private List<Long> explainHaveCommittedCrimeYesSupportDocuments;
  private Long marriageCertificateDocument;
  private Long currentVisaDocument;
  private MarriageCertificate marriageCertificate;
  private Long i94Document;
  private MasterHearingDetail masterHearingDetail;
}
