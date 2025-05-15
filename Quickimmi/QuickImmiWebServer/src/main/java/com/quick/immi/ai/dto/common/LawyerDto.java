/* (C) 2024 */
package com.quick.immi.ai.dto.common;

import com.quick.immi.ai.entity.LawyerProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawyerDto {
  private Integer id;
  private String username;
  private String cognitoId;
  private String firstName;
  private String lastName;
  private String middleName;
  private String email;
  private String phoneNumber;
  private String specialization;
  private String lawFirm;
  // Address JSON field
  private LawyerProfile profile;
  private Integer experienceYears;
  private Long createdAt;
  private Long updatedAt;
}
