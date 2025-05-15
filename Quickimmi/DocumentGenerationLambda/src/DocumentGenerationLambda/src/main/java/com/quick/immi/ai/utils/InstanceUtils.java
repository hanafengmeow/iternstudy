package com.quick.immi.ai.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class InstanceUtils {

    /**
     * Creates an instance of the given class type, populating all String fields with "N/A"
     * and recursively populating fields of any other class type, while skipping List types.
     *
     * @param clazz The class to instantiate and populate
     * @param <T>   The type of the class
     * @return An instance of the specified class type with default values populated
     */
    public static <T> T createInstanceWithDefaults(Class<T> clazz) {
        try {
            // Create an instance of the class
            T instance = clazz.getDeclaredConstructor().newInstance();

            // Populate default values for fields
            populateDefaults(instance, clazz);

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
        }
    }

    /**
     * Recursively populates fields of the given instance with default values, skipping List types.
     *
     * @param instance The object instance to populate
     * @param clazz    The class type of the instance
     * @param <T>      The type of the class
     * @throws IllegalAccessException if field access fails
     */
    private static <T> void populateDefaults(T instance, Class<?> clazz) throws IllegalAccessException {
        if (clazz == null || clazz == Object.class) {
            return;
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                continue; // Skip static and final fields
            }

            field.setAccessible(true); // Allow access to private fields

            // Check the field type
            if (field.getType() == String.class) {
                field.set(instance, "N/A"); // Set default "N/A" for String fields
            } else if (!field.getType().isPrimitive() && !List.class.isAssignableFrom(field.getType())) {
                // For non-primitive, non-List types, instantiate the field recursively
                Object nestedInstance = createInstanceWithDefaults(field.getType());
                field.set(instance, nestedInstance);
            }
        }

        // Process superclass fields if any (for inheritance)
        populateDefaults(instance, clazz.getSuperclass());
    }
}
