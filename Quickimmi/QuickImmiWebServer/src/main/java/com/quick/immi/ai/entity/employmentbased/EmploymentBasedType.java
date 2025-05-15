/* (C) 2024 */
package com.quick.immi.ai.entity.employmentbased;

public enum EmploymentBasedType {
  EB1("EB1"),
  EB2("EB2"),
  EB2_NIW("EB2_NIW"),
  EB3("EB3"),
  EB5("EB5");

  private String value;

  EmploymentBasedType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static EmploymentBasedType fromValue(String value) {
    for (EmploymentBasedType caseType : EmploymentBasedType.values()) {
      if (caseType.getValue().equals(value)) {
        return caseType;
      }
    }
    throw new IllegalArgumentException("Invalid EmploymentBasedType value: " + value);
  }
}
