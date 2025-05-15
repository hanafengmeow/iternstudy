/* (C) 2024 */
package com.quick.immi.ai.entity.familyBased.businss;

public enum FamilyBasedType {
  IMMEDIATE_RELATIVES("IMMEDIATE_RELATIVES"),
  FAMILY_PREFERENCE("FAMILY_PREFERENCE");

  private String value;

  FamilyBasedType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static FamilyBasedType fromValue(String value) {
    for (FamilyBasedType asylumType : FamilyBasedType.values()) {
      if (asylumType.getValue().equals(value)) {
        return asylumType;
      }
    }
    throw new IllegalArgumentException("Invalid FamilyBasedType value: " + value);
  }
}
