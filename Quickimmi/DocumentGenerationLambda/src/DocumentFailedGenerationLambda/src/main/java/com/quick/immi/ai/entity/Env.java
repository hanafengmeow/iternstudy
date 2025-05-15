package com.quick.immi.ai.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Env {
    private String name;
    private String generatedDocumentBucket;
    private String dbConfigPath;
}
