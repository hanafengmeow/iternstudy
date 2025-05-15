package com.quick.immi.ai.fillform.asylum;

import com.google.gson.Gson;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.g28.G28Table;
import com.quick.immi.ai.fillform.G28FormFiller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

class G28FormFillerTest {

    private G28FormFiller formFiller;
    private G28Table g28Table;

    @TempDir
    private Path tempDir; // JUnit Jupiter provides this temporary directory

    @BeforeEach
    void setUp() throws Exception {
        // Create a Gson instance
        Gson gson = new Gson();
        // Read the JSON file from src/test/resources
        try (Reader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("g28-test.json"), StandardCharsets.UTF_8)) {
            g28Table = gson.fromJson(reader, G28Table.class);
        }

        FormGenerationTask task = FormGenerationTask.builder()
                .id(12l)
                .caseId(31l)
                .userId(1)
                .status("0")
                .build();

        formFiller = new G28FormFiller(g28Table, task);
        formFiller.init();
    }
//
//    @Test
//    void testFillDocument() throws Exception {
//        formFiller.fillDocument();
//        formFiller.saveLocal();
//    }
}