package com.quick.immi.ai.dao;

import com.quick.immi.ai.entity.ApplicationCase;
import org.apache.ibatis.annotations.Param;

public interface ApplicationCaseMapper {
    ApplicationCase get(@Param("id") Long id);
}
