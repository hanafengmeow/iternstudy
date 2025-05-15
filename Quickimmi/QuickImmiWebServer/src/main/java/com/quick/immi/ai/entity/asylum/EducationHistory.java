/* (C) 2024 */
package com.quick.immi.ai.entity.asylum;

import lombok.Data;

@Data
public class EducationHistory {
  private String part = "Part A.III";
  // can be Q1 or Q2
  private String question = "Q3";
  private String schoolName;
  private String schoolType;
  private String location;
  private String startDate;
  private String endDate;
}
