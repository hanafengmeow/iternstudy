package com.quick.immi.ai.entity.familyBased.i864;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SponsoredImmigrants {
  // 1 - I am sponsoring the principal immigrant named in Part 2 (Checkbox)
  private String sponsoringPrincipalImmigrantYesCheckbox;
  private String sponsoringPrincipalImmigrantNoCheckbox;

  // 2 - I am sponsoring the following family members immigrating at the same time (Checkbox)
  private String sponsoringFamilyMembersAtSameTimeCheckbox;

  // 3 - I am sponsoring family members immigrating more than six months after the principal immigrant (Checkbox)
  private String sponsoringFamilyMembersAfterSixMonthsCheckbox;

  // List of family members
  private List<FamilyMember> familyMembers;

  // 226 - Total number of immigrants being sponsored
  private String totalNumberOfImmigrantsSponsored;

}
