package com.quick.immi.ai.fillform.commonForm;

import com.google.gson.Gson;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.i485.I485Table;
import com.quick.immi.ai.fillform.I485.I485FormFiller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDFieldTree;
import org.apache.pdfbox.pdmodel.interactive.form.PDTerminalField;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

class I485FormFillerTest {

    private I485FormFiller formFiller;
    private I485Table i485Table;

    @TempDir
    private Path tempDir; // JUnit Jupiter provides this temporary directory

    @BeforeEach
    void setUp() throws Exception {
        // Create a Gson instance
        Gson gson = new Gson();
        // Read the JSON file from src/test/resources
        try (Reader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("i485-test.json"), StandardCharsets.UTF_8)) {
                    i485Table = gson.fromJson(reader, I485Table.class);
        }
        FormGenerationTask task = FormGenerationTask.builder()
                .id(12L)
                .caseId(31L)
                .userId(1)
                .status("0")
                .build();

        formFiller = new I485FormFiller(i485Table, task);
        formFiller.init();
    }

    @Test
    void testFillDocument() throws Exception {
        formFiller.fillDocument();
        formFiller.saveLocal();
    }

    public static List<PDField> getAllFields(PDDocument document) {
        List<PDField> result = new ArrayList<>();

        PDDocumentCatalog docCatalog = document.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        PDFieldTree fieldTree = acroForm.getFieldTree();
        Iterator<PDField> fieldTreeIterator = fieldTree.iterator();
        while (fieldTreeIterator.hasNext()) {
            PDField field = fieldTreeIterator.next();
            if (field instanceof PDTerminalField) {
                result.add(field);
            }
        }
        return result;
    }

    public void toImage(PDDocument document) throws IOException {
        // Create a PDFRenderer object
        PDFRenderer renderer = new PDFRenderer(document);
        // Iterate over each page and convert it to an image
        for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
            // Render the page to an image
            BufferedImage image = renderer.renderImage(pageIndex);
            // Save the image
            File output = new File(tempDir.toFile(), "page_" + (pageIndex + 1) + ".png");
            ImageIO.write(image, "png", output);
        }
        // Close the document
        document.close();
    }
}