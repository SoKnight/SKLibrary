package ru.soknight.lib.tool;

public class Validate {

    public static void notNull(Object object) {
        if(object == null)
            throw new IllegalArgumentException("the validated object is null!");
    }

    public static void notNull(Object object, String fieldName) {
        if(object == null)
            throw new IllegalArgumentException("'" + fieldName + "' cannot be null!");
    }

}
