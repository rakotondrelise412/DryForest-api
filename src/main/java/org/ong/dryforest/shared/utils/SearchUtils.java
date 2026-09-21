package org.ong.dryforest.shared.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class SearchUtils {
    private SearchUtils() {
    }

    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();

        while (type != null && type != Object.class) {
            for (var field : type.getDeclaredFields()) {
                field.setAccessible(true);
                fields.add(field);
            }
            type = type.getSuperclass();
        }

        return fields;
    }
}
