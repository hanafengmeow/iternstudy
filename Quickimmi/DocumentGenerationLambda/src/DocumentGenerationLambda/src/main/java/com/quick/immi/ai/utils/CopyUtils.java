package com.quick.immi.ai.utils;

import crawlercommons.utils.Strings;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Logger;

public class CopyUtils {
    private static final Logger logger = Logger.getLogger(CopyUtils.class.getName());

    public static void copy(Object source, Object target, Properties properties, String prefix) {
        //copy field from source to target object.
        try {
            Class<?> sourceClass = source.getClass();
            Field[] sourceFields = sourceClass.getDeclaredFields();

            Class<?> targetClass = target.getClass();
            Field[] targetFields = targetClass.getDeclaredFields();

            Map<String, Field> targetObjectFieldMap = new HashMap<>();

            for (Field field : targetFields) {
                targetObjectFieldMap.put(field.getName(), field);
            }

            for (Field field : sourceFields) {
                try {
                    Field targetField = targetObjectFieldMap.get(field.getName());
                    if (targetField == null) {
                        logger.warning(field.getName() + " doesn't exist in target " + prefix);
                        continue;
                    }
                    targetField.setAccessible(true);
                    field.setAccessible(true);

                    Object objectValue = field.get(source);
                    Object targetFieldValue = targetField.get(target);
                    if (field.getType().equals(String.class)) {
                        //avoid override pre-defined value
                        if(targetFieldValue != null && !targetFieldValue.equals("")){
                            continue;
                        }
                        if (objectValue == null || objectValue.equals("")) {
                            objectValue = "N/A";
                        }
                        targetField.set(target, objectValue);
                    } else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {//boolean value --- checkbox is set
                        if (objectValue.equals(true)) {
                            String value = properties.getProperty(prefix + "." + field.getName());
                            targetField.set(target, value);
                        }
                    }
                } catch (Exception exp) {
                    logger.severe("fail to copy value " + exp);
                    exp.printStackTrace();
                }
            }
        } catch (Exception exp) {
            logger.severe("fail to copy from: + " + source + "to " + target + "due to " + exp);
            exp.printStackTrace();
        }
    }

    //    public static void copyAllWithList(Object source, Object target, Properties properties, String prefix){
//
//        if(target == null){
//            return;
//        }
//        //copy field from source to target object.
//        Class<?> sourceClass = source.getClass();
//        Field[] sourceFields = sourceClass.getDeclaredFields();
//
//        Class<?> targetClass = target.getClass();
//        Field[] targetFields = targetClass.getDeclaredFields();
//
//        Map<String, Field> targetObjectFieldMap = new HashMap<>();
//
//        for(Field field : targetFields){
//            targetObjectFieldMap.put(field.getName(), field);
//        }
//
//        for(Field field : sourceFields){
//            try {
//                Field targetField = targetObjectFieldMap.get(field.getName());
//                if (targetField == null) {
//                    logger.warning(field.getName() + " doesn't exist in target " + prefix);
//                    continue;
//                }
//                targetField.setAccessible(true);
//                field.setAccessible(true);
//
//                Object objectValue = field.get(source);
//                if (field.getType().equals(String.class)) {
//                    if (objectValue == null || objectValue.equals("")) {
//                        objectValue = "N/A";
//                    }
//                    targetField.set(target, objectValue);
//
//                } else if (field.getType().equals(List.class)){
//                    String key = field.getName();
//                    String newPrefix = Strings.isBlank(prefix) ? key : prefix + "." + key;
//                    List<?> objectValueList = (List<?>) objectValue;
//                    if (objectValueList != null) {
//                        // Create a new list instance for the target
//                        List<Object> targetList = new ArrayList<>();
//                        // Get the generic type of the list elements
//                        Class<?> listElementType = null;
//                        if (!objectValueList.isEmpty()) {
//                            listElementType = objectValueList.get(0).getClass();
//                        }
//                        for(int i = 0; i < objectValueList.size(); i++){
//                            Object item = objectValueList.get(i);
//                            if ( item != null) {
//                                Object targetItem = listElementType.getDeclaredConstructor().newInstance();
//                                copyAllWithList(item, targetItem, properties, String.format("%s.%s", newPrefix, i + 1));
//                                // System.out.println("Target Item: " + targetItem);
//                                targetList.add(targetItem);
//                            }
//                        }
//                        // System.out.println("Target List: " + targetList);
//                        targetField.set(target, targetList);
//                    }
//                } else if(field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)){ // boolean value --- checkbox is set
//                    if(objectValue.equals(true)){
//                        String value = properties.getProperty(prefix + "." + field.getName());
//                        targetField.set(target, value);
//                    }
//                } else {
//                    // Field is an object (class instance)
//                    Object targetObject = null;
//                    if (objectValue != null) {
//                        // Create a new instance of the target field's type
//                        targetObject = targetField.getType().getDeclaredConstructor().newInstance();
//
//                        // Construct the new prefix for property keys
//                        String key = field.getName();
//                        String newPrefix = Strings.isBlank(prefix) ? key : prefix + "." + key;
//
//                        // Recursively copy the fields from the source object to the target object
//                        copyAllWithList(objectValue, targetObject, properties, newPrefix);
//                    }
//
//                    // Set the target field with the new object (or null if objectValue was null)
//                    targetField.set(target, targetObject);
//                }
//            } catch (Exception exp){
//                logger.severe("fail to copy value " + exp);
//                exp.printStackTrace();
//            }
//        }
//    }
    public static void copyAllWithList(Object source, Object target, Properties properties, String prefix) {
        if (source == null || target == null) {
            return;
        }
        // Copy fields from source to target object.
        Class<?> sourceClass = source.getClass();
        Field[] sourceFields = sourceClass.getDeclaredFields();

        Class<?> targetClass = target.getClass();
        Field[] targetFields = targetClass.getDeclaredFields();

        Map<String, Field> targetObjectFieldMap = new HashMap<>();

        for (Field field : targetFields) {
            targetObjectFieldMap.put(field.getName(), field);
        }

        for (Field field : sourceFields) {
            try {
                Field targetField = targetObjectFieldMap.get(field.getName());
                if (targetField == null) {
                    logger.warning(field.getName() + " doesn't exist in target " + prefix);
                    continue;
                }
                targetField.setAccessible(true);
                field.setAccessible(true);

                Object objectValue = field.get(source);
                if (field.getType().equals(String.class)) {
                    if (objectValue == null || objectValue.equals("")) {
                        objectValue = "N/A";
                    }
                    targetField.set(target, objectValue);

                } else if (field.getType().equals(List.class)) {
                    String key = field.getName();
                    String newPrefix = Strings.isBlank(prefix) ? key : prefix + "." + key;
                    List<?> objectValueList = (List<?>) objectValue;
                    if (objectValueList != null) {
                        // Create a new list instance for the target
                        List<Object> targetList = new ArrayList<>();

                        // Get the target list's element type
                        Class<?> targetListElementType = null;
                        Type genericFieldType = targetField.getGenericType();
                        if (genericFieldType instanceof ParameterizedType) {
                            ParameterizedType aType = (ParameterizedType) genericFieldType;
                            Type[] fieldArgTypes = aType.getActualTypeArguments();
                            if (fieldArgTypes.length > 0) {
                                Type fieldArgType = fieldArgTypes[0];
                                if (fieldArgType instanceof Class<?>) {
                                    targetListElementType = (Class<?>) fieldArgType;
                                } else if (fieldArgType instanceof ParameterizedType) {
                                    targetListElementType = (Class<?>) ((ParameterizedType) fieldArgType).getRawType();
                                }
                            }
                        }
                        if (targetListElementType == null) {
                            throw new RuntimeException("Cannot determine target list element type for field: " + targetField.getName());
                        }

                        for (int i = 0; i < objectValueList.size(); i++) {
                            Object item = objectValueList.get(i);
                            if (item != null) {
                                Object targetItem = targetListElementType.getDeclaredConstructor().newInstance();
                                copyAllWithList(item, targetItem, properties, String.format("%s.%s", newPrefix, i + 1));
                                targetList.add(targetItem);
                            }
                        }
                        targetField.set(target, targetList);
                    }
                } else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) { // boolean value --- checkbox is set
                    if (Boolean.TRUE.equals(objectValue)) {
                        String value = properties.getProperty(prefix + "." + field.getName());
                        targetField.set(target, value);
                    }
                } else {
                    // Field is an object (class instance)
                    Object targetObject = null;
                    if (objectValue != null) {
                        // Create a new instance of the target field's type
                        targetObject = targetField.getType().getDeclaredConstructor().newInstance();

                        // Construct the new prefix for property keys
                        String key = field.getName();
                        String newPrefix = Strings.isBlank(prefix) ? key : prefix + "." + key;

                        // Recursively copy the fields from the source object to the target object
                        copyAllWithList(objectValue, targetObject, properties, newPrefix);
                    }

                    // Set the target field with the new object (or null if objectValue was null)
                    targetField.set(target, targetObject);
                }
            } catch (Exception exp) {
                logger.severe("fail to copy value " + exp);
                exp.printStackTrace();
            }
        }
    }


    static class A {
        private boolean ele1;
        private String ele2;

        public A(boolean ele1, String ele2) {
            this.ele1 = ele1;
            this.ele2 = ele2;
        }
    }


    static class B {
        private String ele1;
        private String ele2 = "shouldn't override";

        @Override
        public String toString() {
            return "B{" +
                    "ele1='" + ele1 + '\'' +
                    ", ele2='" + ele2 + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        A aObject = new A(true, "");
        B bObject = new B();

        Properties properties = new Properties();

        properties.setProperty("prefix.ele1", "ele1Value");

        CopyUtils.copy(aObject, bObject, properties, "prefix");

        System.out.println(bObject);
    }
}
