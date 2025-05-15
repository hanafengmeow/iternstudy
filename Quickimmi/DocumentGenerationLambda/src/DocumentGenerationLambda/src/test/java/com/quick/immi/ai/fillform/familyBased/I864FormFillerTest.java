package com.quick.immi.ai.fillform.familyBased;

import com.google.gson.Gson;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.familyBased.i864.I864Table;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class I864FormFillerTest {

    private I864FormFiller formFiller;
    private I864Table i864Table;

    @BeforeEach
    void setUp() throws Exception {
        // Create a Gson instance
        Gson gson = new Gson();
        // Read the JSON file from src/test/resources
        try (Reader reader = new InputStreamReader(
            Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream("i864-test.json")), StandardCharsets.UTF_8)) {
            i864Table = gson.fromJson(reader, I864Table.class);
        }

        FormGenerationTask task = FormGenerationTask.builder()
            .id(12L)  // 'L' for long value
            .caseId(31L)
            .userId(1)
            .status("0")
            .build();

        formFiller = new I864FormFiller(i864Table, task);
        formFiller.init();
    }


    @Test
    void testFillDocument() throws Exception {
        formFiller.fillDocument();
        formFiller.saveLocal();
    }
}
