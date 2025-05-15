/* (C) 2024 */
package com.quick.immi.ai.entity;

import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatCompletionResponse {
  private String id;
  private String object;
  private long created;
  private String model;
  private Usage usage;
  private List<Choice> choices;

  // Inner class for 'usage'
  @Data
  public static class Usage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;

    // Constructor, getters, and setters
    // You can generate them using your IDE or write them manually
  }

  // Inner class for 'choices'
  @Data
  @ToString
  public static class Choice {
    private Message message;
    private String finish_reason;
    private int index;

    // Constructor, getters, and setters
    // You can generate them using your IDE or write them manually
  }

  // Inner class for 'message'
  @Data
  public static class Message {
    private String role;
    private String content;
    // Constructor, getters, and setters
    // You can generate them using your IDE or write them manually
  }
}
