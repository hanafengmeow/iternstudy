package com.quick.immi.ai.entity.familyBased.i864;

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
  // 1a - 344 (Last Name)
  private String lastName;

  // 1b - 345 (First Name)
  private String firstName;

  // 1c - 346 (Middle Name)
  private String middleName;

  // 2 - 347 (A-Number)
  private String aNumber;

  // 3-7 - List of forms (additional information)
  private List<Information> information;

  @Data
  public static class Information {
    private String pageNumber;   // Page number of the form
    private String partNumber;   // Part number of the form
    private String itemNumber;   // Item number of the form
    private String additionalInfo; // Additional information details
  }
}
