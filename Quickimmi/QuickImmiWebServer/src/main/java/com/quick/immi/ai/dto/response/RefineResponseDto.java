/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class RefineResponseDto {
  private String result;
  private List<String> tips;
}
