package com.quick.immi.ai.utils;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.google.gson.Gson;
import com.quick.immi.ai.entity.FormMapping;
import crawlercommons.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.form.*;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;

@Slf4j
public class FormFillUtils {
    private static final Logger logger = Logger.getLogger(FormFillUtils.class.getName());
    private final static String FORM_MAPPING_LOCATION_FORMAT = "mapping/%s.json";

    public static PDDocument deepCopy(PDDocument pdDocument) throws IOException {
        PDDocument result = new PDDocument();
        for(PDPage page : pdDocument.getPages()){
            result.importPage(page);
        }
        return result;
    }


    public static void setReadonly(PDDocument pdDocument) throws IOException {
        // get the document catalog
        PDAcroForm acroForm = pdDocument.getDocumentCatalog().getAcroForm();
        if(acroForm != null) {
            acroForm.flatten();
        }
    }
    public static PDDocument getForm(FormMapping formType) {
        try {
            String currentDocumentTemplateBucket = S3Utils.getCurrentDocumentTemplateBucket();
            S3ObjectInputStream objectContent = S3Utils.getObjectContent(currentDocumentTemplateBucket,
                    formType.getName() + ".pdf");
            PDDocument document = PDDocument.load(objectContent);
            document.setAllSecurityToBeRemoved(true);
            return document;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<PDDocument> getPDFs(List<String> s3Paths) {
        try {
            List<String> pdfPaths = new ArrayList<>();
            List<String> buckets = new ArrayList<>();
            for (String s3Path : s3Paths) {
                String[] parts = s3Path.split("/");
                if (parts[parts.length - 1].endsWith(".pdf")) {
                    String pdfPath = String.join("/", parts[parts.length - 3], parts[parts.length - 2], parts[parts.length - 1]);
                    pdfPaths.add(pdfPath);
                    buckets.add(parts[parts.length - 4]);
                }
            }
            System.out.println(pdfPaths);
            List<PDDocument> documents = new ArrayList<>();
            for (int i = 0; i < pdfPaths.size(); i++) {
                System.out.println("bucket: " + buckets.get(i) + " pdfPath: " + pdfPaths.get(i));
                S3ObjectInputStream objectContent = S3Utils.getObjectContent(buckets.get(i), pdfPaths.get(i));
                PDDocument document = PDDocument.load(objectContent);
                document.setAllSecurityToBeRemoved(true);
                documents.add(document);
            }
            return documents;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveForm(PDDocument document, String key) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            logger.info("Saving form to output stream: " + key);
            document.save(outputStream);
            byte[] bytes = outputStream.toByteArray();
            logger.info("Saving to S3: " + key);
            S3Utils.save(S3Utils.getCurrentGeneratedDocumentBucket(), key, bytes);
            document.close();
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }

    public static void saveFormToLocal(PDDocument document, String path) throws IOException {
        try {
            // Perform operations on the document
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            byte[] bytes = outputStream.toByteArray();
            
            // Write bytes to file
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(bytes);
            }
        } finally {
            document.close();
        }
    }

    public static Properties getFormMapping(FormMapping form) {
        String formMapping = String.format(FORM_MAPPING_LOCATION_FORMAT, form.getName());
        try (Reader reader = new InputStreamReader(
                FormFillUtils.class.getClassLoader().getResourceAsStream(formMapping),
                StandardCharsets.UTF_8)) {
            return new Gson().fromJson(reader, Properties.class);
        } catch (Exception e){
            logger.severe(String.format("fail to get form mapping for %s ", formMapping) + e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static List<String> getAllFields(PDDocument document) {
        List<String> result = new ArrayList<>();

        PDDocumentCatalog docCatalog = document.getDocumentCatalog();

        PDAcroForm acroForm = docCatalog.getAcroForm();

        PDFieldTree fieldTree = acroForm.getFieldTree();
        Iterator<PDField> fieldTreeIterator = fieldTree.iterator();
        while (fieldTreeIterator.hasNext()) {
            PDField field = fieldTreeIterator.next();
            if (field instanceof PDTerminalField) {
                String fullyQualifiedName = field.getFullyQualifiedName();
                result.add(fullyQualifiedName);
            }
        }
        return result;
    }

    public static Map<String, String> getAllFieldsMap(PDDocument document) {
        Map<String, String> result = new HashMap<>();

        PDDocumentCatalog docCatalog = document.getDocumentCatalog();

        PDAcroForm acroForm = docCatalog.getAcroForm();

        PDFieldTree fieldTree = acroForm.getFieldTree();
        Iterator<PDField> fieldTreeIterator = fieldTree.iterator();
        while (fieldTreeIterator.hasNext()) {
            PDField field = fieldTreeIterator.next();
            if (field instanceof PDTerminalField) {
                String fullyQualifiedName = field.getFullyQualifiedName();
                String alternateFieldName = field.getAlternateFieldName();
                while (result.containsKey(alternateFieldName)){
                    alternateFieldName += "_duplicate";
                }
                result.put(alternateFieldName, fullyQualifiedName);
            }
        }
        return result;
    }


    public static void setValue(PDDocument document, String formFieldName, String value) throws IOException {
        try {
            // The following objects have a chance of containing null value
            // Could we put them in a try catch loop to avoid any crashes
            
            PDDocumentCatalog docCatalog = document.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();
            
            PDField field = acroForm.getField(formFieldName);
            
            String fieldType = field.getFieldType();
            
            if (FieldType.Text.getValue().equals(fieldType)) {
                setUpTextFieldValue(field, value);
            } else {
                setUpNonTextFieldValue(field, value);
            }
        } catch (Exception e) {
            logger.severe("Exception encountered when setting value: " + e.getMessage());
        }
    }

    public static void setUpNonTextFieldValue(PDField field, String value) throws IOException {
        if (field != null) {
            field.setValue(value);
        }
    }


    public static void setUpTextFieldValue(PDField field, String value) throws IOException {
        if (field != null) {
            ((PDTextField) field).setDefaultAppearance("/Helv 8 Tf 0 g");
            field.setValue(value);

        }
    }

    public static FieldType getFieldType(String fieldName) {
        if (fieldName.endsWith("Checkbox")) {
            return FieldType.CheckBox;
        } else if (fieldName.endsWith("Dropdown")) {
            return FieldType.DROP_DOWN;
        } else {
            return FieldType.Text;
        }
    }

    public static <T> void fillAllTextFieldsInList(PDDocument pdDocument, 
                                                   Properties properties, 
                                                   String basePrefix, 
                                                   List<T> objects) throws IOException, IllegalAccessException {
        if (objects == null || objects.isEmpty()) {
            return;
        }
        
        int index = 1;
        for (T object : objects) {
            String prefix = basePrefix + "." + index++;
            fillAllTextField(pdDocument, properties, prefix, object);
        }
    }

    public static <T> void fillAllTextField(PDDocument pdDocument,
                                            Properties properties,
                                            String prefix,
                                            T object) throws IOException, IllegalAccessException {
        if(object == null){
            return;
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Field> allFields = Arrays.asList(fields);
        for (Field field : allFields) {
            if (field.getType().equals(String.class)) {
                FormFillUtils.fillField(pdDocument, properties,
                        prefix,
                        object, field);
            }
        }
    }

    public static <T> void fillAllField(PDDocument pdDocument,
                                            Properties properties,
                                            String prefix,
                                            T object) throws IOException, IllegalAccessException {
        if(object == null){
            return;
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Field> allFields = Arrays.asList(fields);
        for (Field field : allFields) {
            logger.info(field.getName());
            field.setAccessible(true);

            if (field.getType().equals(String.class)) {
                FormFillUtils.fillField(pdDocument, properties,
                        prefix,
                        object, field);
            } else if(field.getType().equals(List.class)){
                String key = field.getName();
                String newPrefix = Strings.isBlank(prefix) ? key : prefix + "." + key;
                List objectValueList = (List)field.get(object);
                if (objectValueList != null) {
                    for(int i = 0; i < objectValueList.size(); i++){
                        fillAllField(pdDocument, properties,String.format("%s.%s", newPrefix, i + 1), objectValueList.get(i));
                    }
                }
            } else {
                //object class
                String key = field.getName();
                String newPrefix = Strings.isBlank(prefix) ? key : prefix + "." + key;
                Object objectValue = field.get(object);
                if(objectValue != null){
                    fillAllField(pdDocument, properties, newPrefix, objectValue);
                }
            }
        }
    }

    public static void fillField(PDDocument document,
                                 Properties properties,
                                 String prefix,
                                 Object object,
                                 Field field
    ) throws IllegalAccessException, IOException {
        String key = field.getName();
        if(!Strings.isBlank(prefix)){
            key = prefix + "." + key;
        }
        String formPosition = properties.getProperty(key);
        if(formPosition == null){
            logger.warning(String.format("miss %s form field value", key));
            return;
        }
        field.setAccessible(true);
        Object objectValue = field.get(object);
        if (objectValue != null) {
            String value = (String) objectValue;
            setValue(document, formPosition, value);
        }
    }

    
}
