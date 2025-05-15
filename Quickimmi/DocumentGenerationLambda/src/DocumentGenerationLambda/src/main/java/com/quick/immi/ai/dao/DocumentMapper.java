package com.quick.immi.ai.dao;

import com.quick.immi.ai.entity.Document;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DocumentMapper {
  Long create(Document document);

  void update(Document document);

  Document get(@Param("id") Long id);

  List<Document> getByCaseIdAndType(@Param("caseId") Long caseId, @Param("type") String type, @Param("identify") String identify);
}
