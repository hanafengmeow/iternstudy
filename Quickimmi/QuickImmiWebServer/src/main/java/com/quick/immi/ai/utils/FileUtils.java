/* (C) 2024 */
package com.quick.immi.ai.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileUtils {

  public static String getContent(String resourcePath) {
    StringBuilder builder = new StringBuilder();
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(
                FileUtils.class.getClassLoader().getResourceAsStream(resourcePath), "UTF-8"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line).append("\n");
      }
      return builder.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
