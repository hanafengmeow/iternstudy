/* (C) 2024 */
package com.quick.immi.ai.dto.request;

import com.quick.immi.ai.entity.DataCrawlingType;

public class TriggerDataCrawlingRequestDto {
  private Long caseId;
  private String crawlingMetaData;
  private DataCrawlingType dataCrawlingType;
}
