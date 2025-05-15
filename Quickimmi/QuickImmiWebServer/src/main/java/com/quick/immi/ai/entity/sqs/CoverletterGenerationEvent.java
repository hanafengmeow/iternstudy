/* (C) 2024 */
package com.quick.immi.ai.entity.sqs;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoverletterGenerationEvent {
  private String documentName;
  private String documentType;
  private String document;
  private Long caseId;
  private Integer userId;
  private Client client;
  private Attorney attorney;
  private CaseInfo caseInfo;
  private MarriageCertificate marriageCertificate;
  private List<String> documents;
  private String supportingDocuments;
  private String shipping_method;
  private String personalStatement;
  private String personalStatementInOriginalLanguage;
  private String coverLetter;
  private String language;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Client {
    private String lastName;
    private String firstName;
    private String dateOfBirth;
    private String countryOfOrigin;
    private String gender;
    private String caseSummary;
    private String residential_address;
    private String alienNumber;
    private String additionalApplicants;
    private String additionalApplicantAlienNumber;
    private String personalStatement;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Attorney {
    private String lastName;
    private String firstName;
    private String companyName;
    private String attorneyBarNo;
    private String attorneyBarState;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String state;
    private String zip;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CaseInfo {
    private String courtAddress;
    private String courtCity;
    private String courtState;
    private String courtZip;
    private String judgeName;
    private String hearingDate;
    private String hearingTime;
    private String hearingType;
    private String hearingMedium;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class MarriageCertificate {
    private String licenseHolder;
    private String registrationDate;
    private String licenseNumber;

    private String holderName;
    private String gender;
    private String nationality;
    private String birthDate;
    private String idNumber;

    private String spouseName;
    private String spouseGender;
    private String spouseNationality;
    private String spouseBirthDate;
    private String spouseIdNumber;
    private String seal;
  }
}
