/* (C) 2024 */
package com.quick.immi.ai.dao;

import com.quick.immi.ai.dto.request.CaseQueryRequest;
import com.quick.immi.ai.entity.ApplicationCase;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApplicationCaseMapper {
  Long create(ApplicationCase caseRequestDto);

  void delete(@Param("id") Long id);

  List<ApplicationCase> queryByCustomer(CaseQueryRequest caseQueryRequest);

  Integer getCountByCustomer(@Param("userId") Integer userId);

  List<ApplicationCase> queryByLawyer(CaseQueryRequest caseQueryRequest);

  Integer getCountByLawyer(@Param("lawyerId") Integer lawyerId);

  ApplicationCase get(@Param("id") Long id);

  void update(ApplicationCase applicationCase);

  void updateProgress(
      @Param("id") Long id,
      @Param("currentStep") String currentStep,
      @Param("progress") String progress);

  void updateTranslatedProfile(
      @Param("id") Long id, @Param("translatedProfile") String translatedProfile);
}
