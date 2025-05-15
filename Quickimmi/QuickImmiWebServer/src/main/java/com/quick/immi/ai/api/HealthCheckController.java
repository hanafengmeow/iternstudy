/* (C) 2024 */
package com.quick.immi.ai.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
  /** don't delete. this is for health check */
  @GetMapping("/")
  public String index() {
    return "Success!!!";
  }
}
