package com.quick.immi.ai.dao;

import com.quick.immi.ai.entity.FormGenerationTask;
import org.apache.ibatis.annotations.Param;

public interface FormGenerationTaskMapper {

  FormGenerationTask get(@Param("id") Long id);

  void update(FormGenerationTask task);
}
