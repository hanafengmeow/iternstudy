/* (C) 2024 */
package com.quick.immi.ai.utils;

import com.google.gson.Gson;
import com.quick.immi.ai.dto.request.UpdateApplicationCaseRequestDto;
import com.quick.immi.ai.entity.ApplicationCase;

public class ApplicationCaseConvertor {
  public static ApplicationCase convert(
      UpdateApplicationCaseRequestDto updateApplicationCaseRequestDto) {
    Gson gson = new Gson();
    return ApplicationCase.builder()
        .id(updateApplicationCaseRequestDto.getId())
        .currentStep(updateApplicationCaseRequestDto.getCurrentStep())
        .progress(gson.toJson(updateApplicationCaseRequestDto.getProgress()))
        .profile(gson.toJson(updateApplicationCaseRequestDto.getProfile()))
        .build();
  }
}
