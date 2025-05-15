package com.quick.immi.ai.fillform;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.i485.I485Table;
import com.quick.immi.ai.entity.i765.I765Table;
import com.quick.immi.ai.fillform.I485.I485FormFiller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.logging.Logger;


class I765FormFillerTest {
    private static final Logger logger = Logger.getLogger(I765FormFillerTest.class.getName());
    private I765FormFiller formFiller;
    private I765Table i765Table;

    @TempDir
    private Path tempDir; // JUnit Jupiter provides this temporary directory


    @BeforeEach
    void setUp() throws Exception {
        // Create a Gson instance
        Gson gson = new Gson();
        // Read the JSON file from src/test/resources
        try (Reader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("i765-test.json"), StandardCharsets.UTF_8)) {
            i765Table = gson.fromJson(reader, I765Table.class);
        }
        FormGenerationTask task = FormGenerationTask.builder()
                .id(12l)
                .caseId(31l)
                .formName("i765")
                .userId(1)
                .status("0")
                .build();

        formFiller = new I765FormFiller(i765Table, task);
        formFiller.init();
    }

    @Test
    void testFillDocument() throws Exception {
        System.out.println("Starting testFillDocument...");
        logger.info("Starting testFillDocument...");
        formFiller.fillDocument();
        formFiller.saveLocal();
        // formFiller.saveLocalSupplementResult();
    }
}