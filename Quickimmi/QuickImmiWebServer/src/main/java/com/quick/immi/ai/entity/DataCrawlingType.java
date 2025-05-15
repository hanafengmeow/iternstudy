/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum DataCrawlingType {
  EOIR_HEARING_DETAILS("eoir_hearing_details");

  private String name;

  DataCrawlingType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static DataCrawlingType fromName(String name) {
    for (DataCrawlingType type : DataCrawlingType.values()) {
      if (type.name.equalsIgnoreCase(name)) {
        return type;
      }
    }
    throw new IllegalArgumentException("No enum constant with name " + name);
  }
}
