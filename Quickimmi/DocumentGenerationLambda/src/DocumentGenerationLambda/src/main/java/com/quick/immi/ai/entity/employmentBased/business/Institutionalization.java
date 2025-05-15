/* (C) 2024 */
package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

@Data
public class Institutionalization {
  private String pageNumber = "14";
  private String partNumber = "8";
  private String itemNumber = "68d";

  private String institutionNameCityState;
  private String dateFrom;
  private String dateTo;
  private String reason;
  private boolean isExemptFromPublicChargeYes;
  private boolean isExemptFromPublicChargeNo;
}
