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
public class G28FormMetadata {
  private String mainForm; // i-589, i-765
  private String receiptNumber; //
  private Identify identify;
  private Integer sponsorIndex;
}
