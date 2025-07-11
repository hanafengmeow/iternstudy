/* (C) 2024 */
package com.quick.immi.ai.dao;

import com.quick.immi.ai.entity.Document;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DocumentMapper {
  Long create(Document document);

  void delete(@Param("id") Long id);

  void update(Document document);

  Document get(@Param("id") Long id);

  List<Document> list(
      @Param("caseId") Long caseId,
      @Param("type") String type,
      @Param("identify") String identify,
      @Param("autoGenerated") Boolean autoGenerated,
      @Param("generationType") String generationType);
}
