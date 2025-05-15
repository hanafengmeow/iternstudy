/* (C) 2024 */
package com.quick.immi.ai.entity.familyBased.businss;

import lombok.Data;

@Data
public class Relationship {
  // Part 1-1
  private boolean petitionForSpouseCheckbox;
  // Part 1-2
  private boolean petitionForParentCheckbox;
  // Part 1-3
  private boolean petitionForSiblingCheckbox;
  // Part 1-4
  private boolean petitionForChildCheckbox;

  // Part 1-Q2-1
  private boolean petitionForChildBornInWedlockCheckbox;
  // Part 1-Q2-2
  private boolean petitionForStepchildAndStepparentCheckbox;
  // Part 1-Q2-3
  private boolean petitionForChildNotBornInWedlockCheckbox;
  // Part 1-Q2-4
  private boolean petitionForChildAdoptedCheckbox;

  // Part 1-Q3
  private boolean siblingAdoptionRelationYesCheckbox;
  private boolean siblingAdoptionRelationNoCheckbox;

  // Part 1-Q4
  private boolean gainedStatusThroughAdoptionYesCheckbox;
  private boolean gainedStatusThroughAdoptionNoCheckbox;
}
