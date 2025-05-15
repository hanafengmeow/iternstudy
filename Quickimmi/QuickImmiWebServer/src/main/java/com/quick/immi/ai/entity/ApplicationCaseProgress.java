/* (C) 2024 */
package com.quick.immi.ai.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCaseProgress {
  private List<Step> steps;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Step {
    private CaseProgressStep name;
    private StepStatus status;
    private List<Substep> substeps;
    private Long startedAt;
    private Long updatedAt;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Substep {
    private String name;
    private StepStatus status;
    private String metadata;
    private Long startedAt;
    private Long updatedAt;
  }
}
