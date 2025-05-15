/* (C) 2024 */
package com.quick.immi.ai.entity;

import lombok.Data;

@Data
public class MasterHearingDetail {
  private String noticeOfAppearDate;
  private String courtAddress;
  private String courtZipCode;
  private String courtCity;
  private String courtState;
  private String judgeName;
  // October 14 , 2025
  private String hearingDate;
  private String hearingTime;
  private String hearingType;
  private String hearingMedium;
}
