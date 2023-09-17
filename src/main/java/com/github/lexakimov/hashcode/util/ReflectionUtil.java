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
        } catch (RuntimeException e) {
            if (e.getMessage().contains(
                "Unable to make field final float java.util.HashMap.loadFactor accessible: module java.base does not \"opens java.util\"")) {
                System.err.println(
                    "You should run application with VM argument: --add-opens java.base/java.util=ALL-UNNAMED");
                System.exit(1);
            }
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
