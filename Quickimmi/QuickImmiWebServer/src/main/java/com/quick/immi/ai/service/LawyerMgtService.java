/* (C) 2024 */
package com.quick.immi.ai.service;

import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.quick.immi.ai.dao.LawyerMapper;
import com.quick.immi.ai.dto.common.LawyerDto;
import com.quick.immi.ai.dto.request.CreateLawyerRequest;
import com.quick.immi.ai.dto.request.UpdateLawyerRequest;
import com.quick.immi.ai.entity.Lawyer;
import com.quick.immi.ai.entity.LawyerProfile;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LawyerMgtService {
  @Autowired private LawyerMapper lawyerMapper;

  public Integer create(CreateLawyerRequest request) {
    String username =
        StringUtils.isNullOrEmpty(request.getEmail())
            ? request.getPhoneNumber()
            : request.getEmail();
    Lawyer lawyer =
        Lawyer.builder()
            .email(request.getEmail())
            .phoneNumber(request.getPhoneNumber())
            .cognitoUsername(request.getCognitoId())
            .username(username)
            .createdAt(System.currentTimeMillis())
            .build();

    this.lawyerMapper.create(lawyer);

    return lawyer.getId();
  }

  public void delete(Long id) {
    this.lawyerMapper.delete(id);
  }

  public Lawyer get(Integer id) {
    return this.lawyerMapper.get(id);
  }

  public LawyerDto getByCognitoUsername(String cognitoUsername) {
    Lawyer lawyer = this.lawyerMapper.getByCName(cognitoUsername);
    if (lawyer == null) {
      return null;
    }
    return covert(lawyer);
  }

  public LawyerDto getByUsername(String username) {
    Lawyer lawyer = this.lawyerMapper.getByUsername(username);
    if (lawyer == null) {
      return null;
    }
    return covert(lawyer);
  }

  private LawyerDto covert(Lawyer lawyer) {
    LawyerDto.LawyerDtoBuilder builder = LawyerDto.builder();

    String profile = lawyer.getProfile();
    if (profile != null) {
      builder.profile(new Gson().fromJson(profile, LawyerProfile.class));
    }
    return builder
        .id(lawyer.getId())
        .cognitoId(lawyer.getCognitoUsername())
        .email(lawyer.getEmail())
        .firstName(lawyer.getFirstName())
        .middleName(lawyer.getMiddleName())
        .lastName(lawyer.getLastName())
        .phoneNumber(lawyer.getPhoneNumber())
        .specialization(lawyer.getSpecialization())
        .lawFirm(lawyer.getLawFirm())
        .experienceYears(lawyer.getExperienceYears())
        .createdAt(lawyer.getCreatedAt())
        .updatedAt(lawyer.getUpdatedAt())
        .build();
  }

  public void update(UpdateLawyerRequest request) {
    LawyerProfile profile = request.getProfile();
    Lawyer.LawyerBuilder lawyerBuilder = Lawyer.builder();
    if (profile != null) {
      if (StringUtils.isNullOrEmpty(profile.getEligibility().getNameofLawFirm())) {
        profile.getEligibility().setNameofLawFirm(request.getLawFirm());
      }
      lawyerBuilder.profile(new Gson().toJson(profile));
    }
    lawyerBuilder
        .id(request.getId())
        .firstName(request.getFirstName())
        .middleName(request.getMiddleName())
        .lastName(request.getLastName())
        .phoneNumber(request.getPhoneNumber())
        .specialization(request.getSpecialization())
        .lawFirm(request.getLawFirm())
        .experienceYears(request.getExperienceYears())
        .maxCapacity(request.getMaxCapacity())
        .priority(request.getPriority())
        .updatedAt(System.currentTimeMillis());
    //        System.out.println(lawyerBuilder.build());
    this.lawyerMapper.update(lawyerBuilder.build());
  }

  public List<Lawyer> getAvailableLawyers() {
    return lawyerMapper.getAvailableLawyer();
  }
}
