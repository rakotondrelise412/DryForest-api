package org.ong.dryforest.shared.sort;

import org.ong.dryforest.shared.utils.SearchUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class SortEngine {

    private SortEngine() {
    }

    public static Map.Entry<String, List<String>> build(
            Class<?> entityClass) {

        List<String> sortableFieldNames = new ArrayList<>();
        String defaultSort = null;

        for (var field : SearchUtils.getAllFields(entityClass)) {
            SortableField annotation =
                    field.getAnnotation(SortableField.class);

            if (annotation == null) {
                continue;
            }

            String pathKey = annotation.field().isBlank()
                    ? field.getName()
                    : annotation.field();

            if (annotation.initialize() && defaultSort == null) {
                defaultSort = pathKey;
            }

            if (!sortableFieldNames.contains(pathKey)) {
                sortableFieldNames.add(pathKey);
            }
        }

        if (defaultSort == null) {
            defaultSort = "id";
        }

        if (!sortableFieldNames.contains("id")) {
            sortableFieldNames.add("id");
        }

        return Map.entry(defaultSort, sortableFieldNames);
    }
}
