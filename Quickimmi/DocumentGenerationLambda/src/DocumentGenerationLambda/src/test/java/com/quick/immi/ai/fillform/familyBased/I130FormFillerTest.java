package com.quick.immi.ai.fillform.familyBased;

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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDFieldTree;
import org.apache.pdfbox.pdmodel.interactive.form.PDTerminalField;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.util.logging.Logger;


import com.google.gson.Gson;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.familyBased.i130.I130Table;
import com.quick.immi.ai.fillform.familyBased.I130FormFiller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class I130FormFillerTest {

   private I130FormFiller formFiller;
   private I130Table i130Table;
   private static final Logger logger = Logger.getLogger(I130FormFiller.class.getName());

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() throws Exception {
        // Create a Gson instance
        Gson gson = new Gson();
        // Read the JSON file from src/test/resources
        try (Reader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("i130-test.json"), StandardCharsets.UTF_8)) {
            i130Table = gson.fromJson(reader, I130Table.class);
        }
        FormGenerationTask task = FormGenerationTask.builder()
                .id(12l)
                .caseId(31l)
                .userId(1)
                .status("0")
                .build();

        formFiller = new I130FormFiller(i130Table, task);
        formFiller.init();
    }

    @Test
    void testFillDocument() throws Exception {
        formFiller.fillDocument();
        formFiller.saveLocal();
    }

//    @Test
//    void name() {
//        StringBuffer sb = new StringBuffer();
//        while (sb.length() <= 2200) {
//            sb.append("Hello ");
//        }
//        System.out.println(sb);
//    }

    public static List<PDField> getAllFields(PDDocument document) {
        List<PDField> result = new ArrayList<>();

        PDDocumentCatalog docCatalog = document.getDocumentCatalog();

        PDAcroForm acroForm = docCatalog.getAcroForm();

        PDFieldTree fieldTree = acroForm.getFieldTree();
        Iterator<PDField> fieldTreeIterator = fieldTree.iterator();
        while (fieldTreeIterator.hasNext()) {
            PDField field = fieldTreeIterator.next();
            if (field == null){
                // Log a message indicating the field is null
                continue;// Skip this iteration and go to the next field
            }
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
            File output = new File("page_" + (pageIndex + 1) + ".png");
            ImageIO.write(image, "png", output);
        }
        // Close the document
        document.close();
    }
}