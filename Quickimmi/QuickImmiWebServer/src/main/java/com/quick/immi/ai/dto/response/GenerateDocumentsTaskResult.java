/* (C) 2024 */
package com.quick.immi.ai.dto.response;

import com.quick.immi.ai.dto.common.FormGenerationTaskDto;
import java.util.List;
import lombok.Data;

@Data
public class GenerateDocumentsTaskResult {
  List<FormGenerationTaskDto> tasks;
}
