/* (C) 2024 */
package com.quick.immi.ai.service;

import com.quick.immi.ai.dao.TaskMapper;
import com.quick.immi.ai.dto.common.TaskDto;
import com.quick.immi.ai.entity.Task;
import com.quick.immi.ai.entity.TaskStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
  @Autowired private TaskMapper taskMapper;

  public List<TaskDto> listTasks(Long caseId, TaskStatus status) {
    List<Task> tasks = taskMapper.listByStatus(caseId, status.getValue());
    if (tasks == null) {
      return new ArrayList<>();
    }
    List<TaskDto> taskDtos = new ArrayList<>();
    for (Task task : tasks) {
      TaskDto taskDto = new TaskDto();
      BeanUtils.copyProperties(task, taskDto);
      taskDtos.add(taskDto);
    }

    return taskDtos;
  }

  public List<TaskDto> listLatestTasksByGenerationType(Long caseId, String generationType) {

    List<Task> tasks = taskMapper.listByCaseId(caseId);

    System.out.println("tasks: " + tasks);

    if (generationType == "system_merged") {
      List<Task> mergedTasks = new ArrayList<>();
      for (Task task : tasks) {
        if (task.getFormName().startsWith("merged_")) {
          mergedTasks.add(task);
        }
      }
      tasks = mergedTasks;
    } else {
      List<Task> filteredTasks = new ArrayList<>();
      for (Task task : tasks) {
        if (!task.getFormName().startsWith("merged_")) {
          filteredTasks.add(task);
        }
      }
      tasks = filteredTasks;
    }

    System.out.println("filtered tasks: " + tasks);

    if (tasks == null) {
      return new ArrayList<>();
    }

    // Use Collectors.toMap to filter tasks with the same formName and keep the one with the largest
    // createdAt
    Map<String, Task> latestTasksByFormName =
        tasks.stream()
            .collect(
                Collectors.toMap(
                    Task::getFormName, // Key mapper: formName
                    task -> task, // Value mapper: task itself
                    (existing, replacement) ->
                        existing.getCreatedAt() > replacement.getCreatedAt()
                            ? existing
                            : replacement // Merge function: keep the task with the largest
                    // createdAt
                    ));

    List<Task> latestTasks = new ArrayList<>(latestTasksByFormName.values());

    System.out.println("lastest tasks: " + latestTasks);

    List<TaskDto> taskDtos = new ArrayList<>();
    for (Task task : latestTasks) {
      TaskDto taskDto = new TaskDto();
      BeanUtils.copyProperties(task, taskDto);
      taskDtos.add(taskDto);
    }

    System.out.println("taskDtos: " + taskDtos);
    return taskDtos;
  }
}
