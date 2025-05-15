package com.quick.immi.ai.entity;

public enum GenerationType {
  USER_UPLOADED("user_uploaded"),
  SYSTEM_AUTO_GENERATED("system_auto_generated"),
  SYSTEM_MERGED("system_merged"),
  USER_SIGNED("user_signed"),
  OTHER("other");

  GenerationType(String value) {
    this.value = value;
  }

  private String value;

  public String getValue() {
    return value;
  }
}
