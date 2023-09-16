package com.github.lexakimov.hashcode.util;

public class ReflectionUtil {

    public static <T> T getFieldValue(Object object, String fieldName, Class<T> returnType) {
        try {
            try {
                var field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return returnType.cast(field.get(object));
            } catch (NoSuchFieldException e) {
                var field = object.getClass().getSuperclass().getSuperclass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return returnType.cast(field.get(object));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
