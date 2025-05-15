package com.quick.immi.ai.entity;

public enum CaseType {
  Asylum("Asylum"),
  FamilyBased("FamilyBased"),
  H1B("H1B"),
  NIW("NIW");
  private String value;

  CaseType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static CaseType fromValue(String value) {
    for (CaseType type : CaseType.values()) {
      if (type.getValue().equals(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid CaseType value: " + value);
  }
}
