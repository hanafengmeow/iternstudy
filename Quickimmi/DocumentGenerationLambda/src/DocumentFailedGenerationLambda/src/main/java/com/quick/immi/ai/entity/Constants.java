package com.quick.immi.ai.entity;

import software.amazon.awssdk.utils.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Constants {

    public final static Map<String, Env> REGION_ENV_MAP = ImmutableMap.of(
            "us-west-1", Env.builder()
                    .name("dev")
                    .dbConfigPath("db/config-dev.xml")
                    .generatedDocumentBucket("quickimmi-generated-document-bucket").
                    build(),
            "us-east-1", Env.builder().name("prod")
                    .dbConfigPath("db/config-prod.xml")
                    .generatedDocumentBucket("quickimmi-generated-document-bucket-prod").build()
    );

    public static List<String> MAIN_FROM_SET_WITH_SUPPLEMENT = Arrays.asList(
            DocumentType.I130.getName(),
            DocumentType.I130a.getName(),
            DocumentType.I485.getName(),
            DocumentType.I589.getName());

}
