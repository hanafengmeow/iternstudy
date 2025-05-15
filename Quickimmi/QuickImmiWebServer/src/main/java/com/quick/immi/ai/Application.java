/* (C) 2024 */
package com.quick.immi.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.quick.immi.ai.dao")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
