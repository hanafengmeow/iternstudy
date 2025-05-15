package com.quick.immi.ai.utils;

import static com.quick.immi.ai.utils.FormFillUtils.setValue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDComboBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;

@SuppressWarnings("unchecked")
public class PDFPrerunUtils {
    private static Map<String, String> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
//         String formName = "eoir-28";
//         String dest = "/Users/junma/workspace/DocumentGenerationLambda/src/DocumentGenerationLambda/src/main/resources/asylum/" + formName + "/";
//         String source = dest + formName + ".pdf";
//
//         File file = new File(source);
//         PDDocument document = PDDocument.load(file);
//         document.setAllSecurityToBeRemoved(true);
//
//
//         FormFillUtils.setValue(document, "AppearanceType_CB", "Primary");
//          document.save("test.pdf");
//          document.close();
//         Get checkbox mapping

//       String formName = "i-131";
//       String dest = "/Users/zhangyieva/Projects/Quickimmi/DocumentGenerationLambda/src/DocumentGenerationLambda/src/main/resources/commonForm/" + formName + "/";

        List<String> formList = new ArrayList<>();
        formList.add("ETA9089");
        formList.add("i-140");
        formList.add("i-526");
        formList.add("i-526e");
        formList.add("i-829");

        for(String formName : formList){

            String dest = "/Users/junma/workspace/DocumentGenerationLambda/src/DocumentGenerationLambda/src/main/resources/employmentBased/" + formName + "/";
            String source = dest + formName + ".pdf";
            File file = new File(source);
            PDDocument document = PDDocument.load(file);
            document.setAllSecurityToBeRemoved(true);
            Map<String, String> allFieldsMap = FormFillUtils.getAllFieldsMap(document);
            //change
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonFile = gson.toJson(allFieldsMap);
            try (FileWriter tempFile = new FileWriter(dest + formName + ".json")) {
                tempFile.write(jsonFile);
            }catch (IOException e){
                e.printStackTrace();
            }
            //        System.out.println(allFieldsMap);
            document.close();
        }

        //        document.save("i-130_remove_security.pdf");
//        String prerunDest = dest + formName + "_text_field_fillup_prerun.pdf";
//        String mappingDest = dest + formName + "_text_fillup_mapping.json";
//        String checkboxDest = dest + formName + "_checkbox_with_onvalue.txt";
//        // step 1
//        generateTextFieldMappingCode(source, prerunDest, mappingDest);
//        // step 2
//        generateCheckboxFieldMappingCode(source, checkboxDest);
//        File file = new File(source);
//        PDDocument document = PDDocument.load(file);
//        document.setAllSecurityToBeRemoved(true);
//
//        generateTextFieldMappingCode(source, "i-765_text_field_prerun.pdf", "765_text_field_prerun.json");

//        FormFillUtils.setValue(document, "form1[0].Page3[0].PtLine30b_YesNo[0]", "Y");
//        FormFillUtils.setValue(document, "form1[0].Page3[0].PtLine30b_YesNo[1]", "N");

//        FormFillUtils.setValue(document, "form1[0].Page4[0].Pt3Line1Checkbox[0]", "B");
//        FormFillUtils.setValue(document, "form1[0].Page4[0].Pt3Line1Checkbox[1]", "A");
//        FormFillUtils.setValue(document, "form1[0].Page4[0].Part3_Checkbox[0]", "C");
//        FormFillUtils.setValue(document, "form1[0].Page4[0].Pt4Line6_Checkbox[0]", "A");
//        FormFillUtils.setValue(document, "form1[0].Page5[0].Pt6Line3b_Unit[0]", " STE ");

//         FormFillUtils.setValue(document, "form1[0].Page6[0].Part5Line7_Checkbox[1]", "B");
//         FormFillUtils.setValue(document, "form1[0].Page6[0].Part5Line7b_Checkbox[0]", "Y");
//         FormFillUtils.setValue(document, "form1[0].Page6[0].Part5Line7b_Checkbox[1]", "N");
//
//          document.save("test.pdf");
//          document.close();
    }

    // step 1. Generate text field prerun mapping code
    public static void generateTextFieldMappingCode(String source, String prerunDest, String mappingDest) {
        PrintWriter writer = null;
        try {
            // Load the existing PDF document
            File file = new File(source);
            PDDocument document = PDDocument.load(file);
            document.setAllSecurityToBeRemoved(true);
            List<String> fieldList = FormFillUtils.getAllFields(document);

            int cnt = 100;
            // Save fillup prerun pdf
            for (String field : fieldList) {
                map.put(field, String.valueOf(cnt));
                setValue(document, field, String.valueOf(cnt));
                cnt++;
            }
            document.save(prerunDest);
            document.close();

            // Initialize the PrintWriter
            writer = new PrintWriter(new FileWriter(mappingDest));
            // Begin the JSON object
            writer.println("{");
            // Track the number of entries to handle the comma placement
            int size = map.size();
            int index = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                // System.out.println(entry.getKey() + "--" + entry.getValue());
                // Increase index count
                index++;
                // Write the key-value pair in JSON format
                writer.println("  \"" + entry.getKey() + "\": \"" + entry.getValue() + "\"" + (index < size ? "," : ""));
            }
            // End the JSON object
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


    // step 2. Generate checkbox field mapping code
    public static void generateCheckboxFieldMappingCode(String source, String dest) {
        PrintWriter writer = null; // Declare a PrintWriter
        try {
            // Load the existing PDF document
            File file = new File(source);
            PDDocument document = PDDocument.load(file);
            document.setAllSecurityToBeRemoved(true);
            List<String> fieldList = FormFillUtils.getAllFields(document);

            // Save fillup mapping text file
            writer = new PrintWriter(new FileWriter(dest));
            for (String field : fieldList) {
                PDDocumentCatalog docCatalog = document.getDocumentCatalog();
                PDAcroForm acroForm = docCatalog.getAcroForm();
                PDField pdField = acroForm.getField(field);
                String fieldType = pdField.getFieldType();
                if (fieldType.equalsIgnoreCase(FieldType.CheckBox.getValue())) {
                    PDButton btn = (PDButton) pdField;
                    // System.out.println("Key: '" + field + "'  value: '" + btn.getOnValues() + "'");
                    // writer.println("\"" + field + "\":\"" + btn.getOnValues() + "\",");
                    try {
                        writer.println("\"" + field + "\":\"" + btn.getOnValues() + "\",");
                    } catch (IllegalStateException e) {
                        // Print exception details
                        System.err.println("An IllegalStateException occurred: " + e.getMessage());
                        e.printStackTrace(); // Print stack trace for more details
                    }
                } else if (fieldType.equalsIgnoreCase(FieldType.DROP_DOWN.getValue())) {
                    PDComboBox comboBox = (PDComboBox) pdField;
                    // System.out.println("Key: '" + field + "'  value: '" + comboBox.getOptions() + "'");
                    writer.println("\"" + field + "\":\"" + comboBox.getOptions() + "\",");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                // Ensure the writer is closed after use
                writer.close();
            }
        }
    }

    private static void list(PDField field) {
        System.out.println(field.getFullyQualifiedName());
        System.out.println(field.getPartialName());
        System.out.println(field.getFieldType());
        if (field instanceof PDNonTerminalField) {
            PDNonTerminalField nonTerminalField = (PDNonTerminalField) field;
            for (PDField child : nonTerminalField.getChildren()) {
                list(child);
            }
        }
    }

}