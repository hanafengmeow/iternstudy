/* (C) 2024 */
package com.quick.immi.ai.dao;

import com.quick.immi.ai.entity.Task;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskMapper {
  List<Task> listByCaseId(@Param("caseId") Long caseId);

  List<Task> listByStatus(@Param("caseId") Long caseId, @Param("status") String status);

  List<Task> listByUserId(@Param("userId") Long userId);

  List<Task> listByCreator(@Param("caseId") Long caseId, @Param("createdBy") String createdBy);

  Task get(Long id);

  void create(Task task);

  void update(Task task);
}
