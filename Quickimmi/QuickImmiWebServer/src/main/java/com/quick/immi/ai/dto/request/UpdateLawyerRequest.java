/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.LawyerProfile;
import lombok.Data;

@Data
public class UpdateLawyerRequest {
  private Integer id;
  private String firstName;
  private String lastName;
  private String middleName;
  private String phoneNumber;
  private String specialization;
  private String lawFirm;
  private LawyerProfile profile;
  private Integer experienceYears;
  private Integer status;
  private Integer priority;
  private Integer maxCapacity;
}
