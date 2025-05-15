package com.quick.immi.ai.entity;

public enum AsylumType {
  AFFIRMATIVE("AFFIRMATIVE"), DEFENSIVE("DEFENSIVE");

  private String value;

  AsylumType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}