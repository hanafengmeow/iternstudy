/* (C) 2024 */
package com.quick.immi.ai.entity.familybased;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdSize {
  // Persons you are sponsoring in this affidavit
  // 227 - Provide the number you entered in Part 3., Item Number 29.
  private String part3Item29; // todo HOW?

  // Persons NOT sponsored in this affidavit
  // 228 - Yourself
  private String yourself;

  // 229 - If you are currently married, enter "1" for your spouse
  private String spouse;

  // 230 - If you have dependent children, enter the number here
  private String dependentChildren;

  // 231 - If you have any other dependents, enter the number here
  private String otherDependents;

  // 232 - If you have sponsored any other persons on Form I-864 or I-864EZ who are now lawful
  // permanent residents
  private String sponsoredOtherPersons;

  // 233 - OPTIONAL: If you have siblings, parents, or adult children with the same principal
  // residence
  private String optionalSiblingsParentsAdultChildren;

  // 234 - Add together Part 5., Item Numbers 1. - 7. and enter the number here (Household Size)
  private String householdSize;
}
