package com.quick.immi.ai.dao;

import com.quick.immi.ai.entity.Lawyer;
import org.apache.ibatis.annotations.Param;

public interface LawyerMapper {
  Lawyer get(@Param("id") Integer id);

  Lawyer getByUsername(@Param("username") String username);
}
