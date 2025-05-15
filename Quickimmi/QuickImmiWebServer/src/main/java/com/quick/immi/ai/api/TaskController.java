/* (C) 2024 */
package com.quick.immi.ai.api;

import static com.quick.immi.ai.exception.Constant.SERVER_SIDE_ERROR;

import com.quick.immi.ai.annotation.Login;
import com.quick.immi.ai.dto.common.TaskDto;
import com.quick.immi.ai.dto.response.ResponseDto;
import com.quick.immi.ai.entity.GenerationType;
import com.quick.immi.ai.service.TaskService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
@Slf4j
public class TaskController {
  @Autowired private TaskService taskService;

  @GetMapping("/list/{caseId}")
  @Login
  public ResponseEntity<ResponseDto<List<TaskDto>>> list(@RequestParam("caseId") Long caseId) {
    try {
      return ResponseEntity.ok().body(ResponseDto.newInstance(new ArrayList<>()));
    } catch (Exception e) {
      log.error(String.format("fail to get tasks for case: %s", caseId), e);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR, String.format("Failed to get tasks due to", e.getMessage())));
    }
  }

  @GetMapping("/listLatestTasksByGenerationType")
  @Login
  public ResponseEntity<ResponseDto<List<TaskDto>>> listLatestTasksByGenerationType(
      @RequestParam("caseId") Long caseId,
      @RequestParam("generationType") GenerationType generationType) {
    try {
      List<TaskDto> result =
          taskService.listLatestTasksByGenerationType(caseId, generationType.getValue());
      return ResponseEntity.ok().body(ResponseDto.newInstance(result));
    } catch (Exception exp) {
      log.error(String.format("fail to list tasks for case %s", caseId), exp);
      return ResponseEntity.status(500)
          .body(
              ResponseDto.newInstance(
                  SERVER_SIDE_ERROR,
                  String.format("Fail to list tasks for case %s", exp.getMessage())));
    }
  }
}
