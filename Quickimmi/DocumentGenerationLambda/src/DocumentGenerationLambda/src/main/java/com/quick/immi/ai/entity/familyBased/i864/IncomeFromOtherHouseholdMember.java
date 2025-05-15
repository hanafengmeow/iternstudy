package com.quick.immi.ai.entity.familyBased.i864;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeFromOtherHouseholdMember {

  private String name;

  // 242 - Relationship
  private String relationship;

  // 243 - Current Income
  private String currentIncome;
}
