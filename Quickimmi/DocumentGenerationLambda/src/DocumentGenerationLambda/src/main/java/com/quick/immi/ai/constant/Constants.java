package com.quick.immi.ai.constant;

import com.quick.immi.ai.entity.Env;
import software.amazon.awssdk.utils.ImmutableMap;

import java.util.Map;

public class Constants {
    public final static Map<String, Env> REGION_ENV_MAP = ImmutableMap.of(
            "us-west-1", Env.builder()
                    .name("dev")
                    .dbConfigPath("db/config-dev.xml")
                            .documentTemplateBucket("quickimmi-document-template-bucket")
                    .generatedDocumentBucket("quickimmi-generated-document-bucket").
                    build(),
            "us-east-1", Env.builder().name("prod")
                    .dbConfigPath("db/config-prod.xml")
                            .documentTemplateBucket("quickimmi-document-template-bucket-prod")
                    .generatedDocumentBucket("quickimmi-generated-document-bucket-prod").build()
    );

    public final static String DEFAULT_VALUE = "N/A";
}
