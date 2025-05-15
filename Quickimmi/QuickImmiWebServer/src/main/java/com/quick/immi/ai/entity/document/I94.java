/* (C) 2024 */
package com.quick.immi.ai.entity.document;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class I94 {
  private String recordNumber;
  private String mostRecentEntryDate;
  private String admissionClass;
  private String admitUntilDate;

  private String lastName;
  private String firstName;
  private String birthDate;
  private String documentNumber;
  private String citizenship;

  private List<String> travelHistory;
}
