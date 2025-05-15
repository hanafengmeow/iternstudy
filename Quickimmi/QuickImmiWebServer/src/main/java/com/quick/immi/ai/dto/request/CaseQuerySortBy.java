/* (C) 2024 */
package com.quick.immi.ai.dto.request;

public enum CaseQuerySortBy {
  UPDATED_AT("updatedAt"),
  STATUS("id"),
  CaseName("caseName");

  private String value;

  CaseQuerySortBy(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
