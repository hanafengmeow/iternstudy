/* (C) 2024 */
package com.quick.immi.ai.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
  private String id;
  private String name;
  private String price;
  private String description;
}
