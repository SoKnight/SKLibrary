package ru.soknight.lib.tool;

public class Validate {

    public static void notNull(Object object, String fieldName) {
        if(object == null)
            throw new IllegalArgumentException("'" + fieldName + "' cannot be null!");
    }

    public static void notEmpty(String string, String fieldName) {
        if(string == null || string.isEmpty())
            throw new IllegalArgumentException("'" + fieldName + "' cannot be empty!");
    }

    public static void isTrue(boolean condition, String message) {
        if(!condition)
            throw new IllegalArgumentException(message);
    }

    public static void isFalse(boolean condition, String message) {
        if(condition)
            throw new IllegalArgumentException(message);
    }

}
