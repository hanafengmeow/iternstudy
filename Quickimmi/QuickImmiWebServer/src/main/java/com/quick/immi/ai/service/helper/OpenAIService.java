/* (C) 2024 */
package com.quick.immi.ai.service.helper;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.quick.immi.ai.entity.ChatCompletionRequest;
import com.quick.immi.ai.entity.ChatCompletionResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OpenAIService {
  @Value("${com.quickimmi.open.ai.key}")
  private String apiKey;

  public String invoke(String query) {
    OkHttpClient client = new OkHttpClient();

    MediaType mediaType = MediaType.parse("application/json");

    ChatCompletionRequest.Content content = new ChatCompletionRequest.Content("text", query);
    ChatCompletionRequest.Message message =
        new ChatCompletionRequest.Message("user", Lists.newArrayList(content));
    ChatCompletionRequest chatCompletionRequest =
        ChatCompletionRequest.builder()
            .max_tokens(2000)
            .model("gpt-3.5-turbo")
            .messages(Lists.newArrayList(message))
            .build();

    RequestBody requestBody =
        RequestBody.create(new Gson().toJson(chatCompletionRequest), mediaType);

    Request request =
        new Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer " + apiKey)
            .build();

    try (Response response = client.newCall(request).execute()) {
      if (response.isSuccessful() && response.body() != null) {
        ChatCompletionResponse chatCompletion =
            new Gson().fromJson(response.body().string(), ChatCompletionResponse.class);
        String result = chatCompletion.getChoices().get(0).getMessage().getContent();
        System.out.println(result);
        log.info("OpenAI result" + result);
        return result;
      } else {
        throw new RuntimeException("fail to call openAI to get the result -- " + response);
      }
    } catch (IOException e) {
      throw new RuntimeException("fail to call openAI to get the result", e);
    }
  }

  public String invokeWithPrompt(String prompt, String data) {
    OkHttpClient client =
        new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    MediaType mediaType = MediaType.parse("application/json");

    ChatCompletionRequest.Content promptContent = new ChatCompletionRequest.Content("text", prompt);
    ChatCompletionRequest.Content dataContent = new ChatCompletionRequest.Content("text", data);

    ChatCompletionRequest.Message promptMessage =
        new ChatCompletionRequest.Message("system", Lists.newArrayList(promptContent));
    ChatCompletionRequest.Message dataMessage =
        new ChatCompletionRequest.Message("user", Lists.newArrayList(dataContent));

    ChatCompletionRequest chatCompletionRequest =
        ChatCompletionRequest.builder()
            .max_tokens(4096)
            .model("gpt-3.5-turbo")
            .messages(Lists.newArrayList(promptMessage, dataMessage))
            .build();

    RequestBody requestBody =
        RequestBody.create(new Gson().toJson(chatCompletionRequest), mediaType);

    Request request =
        new Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer " + apiKey)
            .build();

    try (Response response = client.newCall(request).execute()) {
      if (response.isSuccessful()) {
        ChatCompletionResponse chatCompletion =
            new Gson().fromJson(response.body().string(), ChatCompletionResponse.class);
        String result = chatCompletion.getChoices().get(0).getMessage().getContent();
        return result;
      } else {
        throw new RuntimeException(
            "fail to call openai to get the result -- " + response.body().string());
      }
    } catch (IOException e) {
      log.error("fail to invoke openai", e);
      throw new RuntimeException("fail to call openai to get the result", e);
    }
  }
}
