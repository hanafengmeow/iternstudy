/* (C) 2024 */
package com.quick.immi.ai.entity.familybased;

import java.util.List;
import lombok.Data;

@Data
public class SupplementDocument {
  private PetitionerIdentity petitionerIdentity;
  private BeneficiaryIdentity beneficiaryIdentity;
  private GenuineMarriageEvidence genuineMarriageEvidence;
  private PetitionerFinicalEvidence petitionerFinicalEvidence;

  @Data
  public static class PetitionerIdentity {
    private Long passportOrGreenCardDocument;
    private Long marriageCertificateDocument;
    private List<Long> previousMarriageDivorceDocument;
  }

  @Data
  public static class BeneficiaryIdentity {
    private Long validVisaDocument;
    private Long passportDocument;
    private Long birthCertificateDocument;
    private Long greenCardOtherCountryDocument;
  }

  @Data
  public static class GenuineMarriageEvidence {
    private List<Long> GenuineMarriageEvidences;
  }

  @Data
  public static class PetitionerFinicalEvidence {
    private List<Long> finicalDocuments;
  }

  @Data
  public static class JointSponsorFinicalEvidence {
    private List<Long> finicalDocuments;
  }
}
