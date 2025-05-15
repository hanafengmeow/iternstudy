package com.quick.immi.ai.dao;

import com.quick.immi.ai.entity.Document;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DocumentMapper {
  void update(Document document);

  Document get(@Param("id") Long id);
}
