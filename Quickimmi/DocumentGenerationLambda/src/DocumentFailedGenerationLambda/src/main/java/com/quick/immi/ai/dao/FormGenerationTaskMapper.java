package com.quick.immi.ai.dao;

import com.quick.immi.ai.entity.FormGenerationTask;
import org.apache.ibatis.annotations.Param;

public interface FormGenerationTaskMapper {
  void update(FormGenerationTask task);
}
