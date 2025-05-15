/* (C) 2024 */
package com.quick.immi.ai.entity.familybased;

import lombok.Data;

@Data
public class DisabilityAccommodation {
  // Page 16, Question 1 - Requesting Accommodation
  private boolean requestingAccommodationYesCheckbox;
  private boolean requestingAccommodationNoCheckbox;

  // Page 16, Question 2a - Deaf or Hard of Hearing Accommodation
  private boolean deafOrHardOfHearingYesCheckbox;
  private String deafOrHardOfHearingAccommodationDetails;

  // Page 16, Question 2b - Blind or Low Vision Accommodation
  private boolean blindOrLowVisionYesCheckbox;
  private String blindOrLowVisionAccommodationDetails;

  // Page 16, Question 2c - Other Disability or Impairment Accommodation
  private boolean otherDisabilityOrImpairmentYesCheckbox;
  private String otherDisabilityOrImpairmentAccommodationDetails;
}
