/* (C) 2024 */
package com.quick.immi.ai.entity.familyBased.businss;

import lombok.Data;

@Data
public class Organization {
  private String orgName; // Used in P9Q2, P9Q6, P10Q10
  private String cityTown; // Used in P9Q3a, P9Q7a, P10Q11a
  private String state; // Used in P9Q3b, P9Q7b, P10Q11b
  private String country; // Used in P9Q3c, P9Q7c, P10Q11c
  private String natureOfGroup; // Used in P9Q4, P9Q8, P10Q12
  private String natureOfInvolvement;
  private String dateFrom; // Used in P9Q5a, P10Q9a, P10Q13a
  private String dateTo; // Used in P9Q5b, P10Q9b, P10Q13b
}
