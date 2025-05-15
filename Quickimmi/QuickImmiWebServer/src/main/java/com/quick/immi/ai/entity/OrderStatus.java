/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum OrderStatus {
  Created("Created"),
  Succeeded("Succeeded"),
  Uncaptured("Refunded"),
  Refunded("Refunded"),
  Failed("Failed");

  String value;

  OrderStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static OrderStatus fromValue(String value) {
    for (OrderStatus status : OrderStatus.values()) {
      if (status.getValue().equals(value)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Invalid OrderStatus value: " + value);
  }
}
