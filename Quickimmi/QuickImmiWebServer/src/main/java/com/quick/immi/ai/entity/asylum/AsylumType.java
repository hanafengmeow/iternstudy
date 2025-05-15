/* (C) 2024 */
package com.quick.immi.ai.entity.asylum;

public enum AsylumType {
  AFFIRMATIVE("AFFIRMATIVE"),
  DEFENSIVE("DEFENSIVE");

  private String value;

  AsylumType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static AsylumType fromValue(String value) {
    for (AsylumType asylumType : AsylumType.values()) {
      if (asylumType.getValue().equals(value)) {
        return asylumType;
      }
    }
    throw new IllegalArgumentException("Invalid asylumType value: " + value);
  }
}
