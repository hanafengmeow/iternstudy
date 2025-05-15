/* (C) 2024 */
package com.quick.immi.ai.dao;

import com.quick.immi.ai.entity.FormGenerationTask;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormGenerationTaskMapper {
  Long create(FormGenerationTask task);

  FormGenerationTask get(@Param("id") Long id);

  List<FormGenerationTask> listByIds(List<Long> ids);

  void delete(@Param("id") Long id);

  void update(FormGenerationTask task);

  List<FormGenerationTask> listByCaseId(Long caseId);
}
