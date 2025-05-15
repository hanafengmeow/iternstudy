package com.quick.immi.ai.fillform.employmentBased;

import com.google.gson.Gson;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.employmentBased.i140.I140Table;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class I140FormFillerTest {

    private I140FormFiller formFiller;
    private I140Table i140Table;

    @BeforeEach
    void setUp() throws Exception {
        // Create a Gson instance
        Gson gson = new Gson();
        // Read the JSON file from src/test/resources
        try (Reader reader = new InputStreamReader(
            Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream("i140-test.json")), StandardCharsets.UTF_8)) {
            i140Table = gson.fromJson(reader, I140Table.class);
        }

        FormGenerationTask task = FormGenerationTask.builder()
            .id(12L)  // 'L' for long value
            .caseId(31L)
            .userId(1)
            .status("0")
            .build();

        formFiller = new I140FormFiller(i140Table, task);
        formFiller.init();
    }

    @Test
    void testFillDocument() throws Exception {
        formFiller.fillDocument();
        formFiller.saveLocal();
    }
}
