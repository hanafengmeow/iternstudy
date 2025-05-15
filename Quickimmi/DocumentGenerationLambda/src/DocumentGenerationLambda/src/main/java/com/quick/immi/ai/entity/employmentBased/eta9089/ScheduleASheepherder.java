package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleASheepherder {
    // Section B1 - Is this application in support of a Schedule A or Sheepherder Occupation?
    private String isScheduleASheepherder;
}
