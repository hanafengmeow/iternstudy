/* (C) 2024 */
package com.quick.immi.ai.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatCompletionRequest {
  private String model;
  private List<Message> messages;
  private int max_tokens;

  @Data
  @AllArgsConstructor
  public static class Message {
    private String role;
    private List<Content> content;
  }

  @Data
  @AllArgsConstructor
  public static class Content {
    private String type;
    private String text;
  }
}
