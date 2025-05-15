/* (C) 2024 */
package com.quick.immi.ai.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lawyer {
  private Integer id;
  private String username;
  private String cognitoUsername;
  private String firstName;
  private String lastName;
  private String middleName;
  private String email;
  private String phoneNumber;
  private String specialization;
  private String lawFirm;
  // Address JSON field
  private String profile;
  private Integer experienceYears;
  private Integer status;
  private Integer priority;
  private Integer occupiedCapability;
  private Integer maxCapacity;
  private Long createdAt;
  private Long updatedAt;
}
