/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum FileType {
  PDF("pdf"),
  IMAGE("image"),
  TEXT("text"),
  VIDEO("video"),
  AUDIO("audio");

  private String value;

  FileType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
