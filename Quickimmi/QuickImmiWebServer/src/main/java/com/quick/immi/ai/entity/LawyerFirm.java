/* (C) 2024 */
package com.quick.immi.ai.entity;

import lombok.Data;

@Data
public class LawyerFirm {
  private Integer id;
  private String name;
  private String address;
  private Long createdAt;
  private Long updatedAt;
}
