/* (C) 2024 */
package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

@Data
public class Benefit {
  private String pageNumber = "14";
  private String partNumber = "8";
  private String itemNumber = "68c";

  private String benefitReceived;
  private String startDate;
  private String endDate;
  private String dollarAmount;
  private boolean isExemptFromPublicChargeYes;
  private boolean isExemptFromPublicChargeNo;
}
