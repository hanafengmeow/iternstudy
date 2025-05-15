/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import com.quick.immi.ai.entity.ApplicationCaseProgress;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseProfileResponseDto<T> {
  private T profile;
  private ApplicationCaseProgress progress;
}
