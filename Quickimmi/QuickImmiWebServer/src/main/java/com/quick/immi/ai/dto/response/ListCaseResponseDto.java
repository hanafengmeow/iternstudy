/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import com.quick.immi.ai.entity.CaseProgressStep;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListCaseResponseDto {
  private Metadata metadata;
  private List<Case> cases;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Metadata {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalItems;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Case {
    private Long id;
    private Integer userId;
    private String applicantName;
    private String caseName;
    private String type;
    private String subType;
    @Deprecated private String asylumType;
    private CaseProgressStep currentStep;
    private Long overallPercentage;
    private Integer submitted;
    private Long updatedAt;
  }
}
