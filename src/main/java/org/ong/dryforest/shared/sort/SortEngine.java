package org.ong.dryforest.shared.sort;

import org.ong.dryforest.shared.utils.SearchUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class SortEngine {

    private SortEngine() {
    }

    public static <T> Map.Entry<String, List<String>> build(T clazz) {
        List<String> sortableFieldNames = new ArrayList<>();
        String defaultSort = null;
        for (var field : SearchUtils.getAllFields(clazz.getClass())) {
            SortableField annotation = field.getAnnotation(SortableField.class);

            if (annotation == null) {
                continue;
            }

            var pathKey = annotation.field().isBlank()
                    ? field.getName()
                    : annotation.field();

            if (defaultSort == null && annotation.initialize()) {
                defaultSort = pathKey;
            }

            sortableFieldNames.add(pathKey);
        }

        return Map.entry(defaultSort == null ? "id" : defaultSort, sortableFieldNames);
    }

}