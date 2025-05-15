/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum FileGenerationType {
  USER("user"),
  SYSTEM("system");

  private String value;

  FileGenerationType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
