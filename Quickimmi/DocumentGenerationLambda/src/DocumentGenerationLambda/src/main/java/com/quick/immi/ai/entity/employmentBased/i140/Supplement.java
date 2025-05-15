package com.quick.immi.ai.entity.employmentBased.i140;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Supplement {
  // Part 11 -- Supplement
  private String lastName;

  private String firstName;

  private String middleName;

  private String aNumber;

  private List<Information> information;

  @Data
  public static class Information {
    private String pageNumber;   // Page number of the form
    private String partNumber;   // Part number of the form
    private String itemNumber;   // Item number of the form
    private String additionalInfo; // Additional information details
  }
}
