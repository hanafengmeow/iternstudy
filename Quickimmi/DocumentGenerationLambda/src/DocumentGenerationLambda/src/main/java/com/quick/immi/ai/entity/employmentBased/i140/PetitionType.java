package com.quick.immi.ai.entity.employmentBased.i140;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetitionType {
// PART 2: Petition Type Checkboxes (from the images)

  // 130: 1.e. A professional with a bachelor's degree or equivalent
  private String petitionType1e;

  // 131: 1.a. An alien of extraordinary ability
  private String petitionType1a;

  // 132: 1.b. An outstanding professor or researcher
  private String petitionType1b;

  // 133: 1.c. A multinational executive or manager
  private String petitionType1c;

  // 134: 1.d. A member of the professions holding an advanced degree or an alien of exceptional ability (Not seeking NIW)
  private String petitionType1d;

  // 177: 1.g. Any other worker (requiring less than two years of training or experience)
  private String petitionType1g;

  // 178: 1.h. An alien applying for NIW (National Interest Waiver)
  private String petitionType1h;

  // 179: 1.f. A skilled worker (requiring at least two years of specialized training or experience)
  private String petitionType1f;

  // 140: 2.a. To amend a previously filed petition (checkbox)
  private String petitionType2a;

  // 141: 2.b. For the Schedule A, Group I or II designation (checkbox)
  private String petitionType2b;

  // 142: Previous Petition Receipt Number (text field)
  private String receiptNumber;

}
