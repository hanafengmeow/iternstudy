/* (C) 2024 */
package com.quick.immi.ai.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncThreadUtils {
  private static ExecutorService executorService = Executors.newFixedThreadPool(5);

  public static void execute(Runnable task) {
    executorService.submit(task);
  }
}
