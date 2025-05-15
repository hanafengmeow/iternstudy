/* (C) 2024 */
package com.quick.immi.ai.dao;

import com.quick.immi.ai.entity.Lawyer;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LawyerMapper {
  Lawyer get(@Param("id") Integer id);

  Lawyer getByUsername(@Param("username") String username);

  Lawyer getByCName(@Param("cognitoUsername") String cognitoUsername);

  Integer create(Lawyer entity);

  List<Lawyer> getAvailableLawyer();

  void update(Lawyer lawyer);

  void delete(Long id);
}
