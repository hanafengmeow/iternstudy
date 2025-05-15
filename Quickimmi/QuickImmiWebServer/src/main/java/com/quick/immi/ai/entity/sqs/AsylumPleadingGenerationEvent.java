/* (C) 2024 */
package com.quick.immi.ai.entity.sqs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AsylumPleadingGenerationEvent {
  private String caseId;
  private String userId;
  private String documentType;
  private String documentName;
  private Client client;
  private Attorney attorney;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Client {
    private String lastName;
    private String firstName;
    private String middleName;

    private String noticeOfAppearDate;
    private String presentNationality;
    private String nativeLanguage;
    private String mostRecentEntryDate;
    private String mostRecentEntryCity;
    private String mostRecentEntryState;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Attorney {
    private String lastName;
    private String firstName;
  }
}
