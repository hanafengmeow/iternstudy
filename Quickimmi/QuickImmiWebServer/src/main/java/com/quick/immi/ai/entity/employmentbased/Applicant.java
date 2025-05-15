/* (C) 2024 */
package com.quick.immi.ai.entity.employmentbased;

import com.quick.immi.ai.entity.NameEntity;
import java.util.List;
import lombok.Data;

@Data
public class Applicant {
  // TODO: This is the placeholder applicant class. Need to add more fields
  private Long passportDocumentId;
  private List<Long> passportStampPageDocumentIds;
  private String alienNumber;
  private String ssn;
  private String lastName;
  private String firstName;
  private String middleName;
  private List<NameEntity> nameEntitiesUsedBefore;
  private String city;
  private String state;
  private String zipCode;
  private String country;
}
