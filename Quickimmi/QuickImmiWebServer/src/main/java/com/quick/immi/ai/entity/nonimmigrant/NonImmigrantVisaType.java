/* (C) 2024 */
package com.quick.immi.ai.entity.nonimmigrant;

public enum NonImmigrantVisaType {
  H1B("H1B"),
  L1("L1"),
  O1("O1");

  private String value;

  NonImmigrantVisaType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static NonImmigrantVisaType fromValue(String value) {
    for (NonImmigrantVisaType caseType : NonImmigrantVisaType.values()) {
      if (caseType.getValue().equals(value)) {
        return caseType;
      }
    }
    throw new IllegalArgumentException("Invalid NonImmigrantVisaType value: " + value);
  }
}
