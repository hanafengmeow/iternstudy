/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import lombok.Data;

@Data
public class CreateLawyerRequest {
  private String email;
  private String cognitoId;
  private String phoneNumber;
}
