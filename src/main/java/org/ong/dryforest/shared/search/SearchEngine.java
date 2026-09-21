package org.ong.dryforest.shared.search;

import jakarta.persistence.criteria.*;
import org.ong.dryforest.shared.exceptions.ApiException;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.utils.SearchUtils;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class SearchEngine {

    private SearchEngine() {
    }

    public static <T> Predicate build(
            T filters,
            CriteriaBuilder cb,
            Root<?> root
    ) {

        if (filters == null) {
            return cb.conjunction();
        }

        List<Predicate> andPredicates = new ArrayList<>();
        List<Predicate> orPredicates = new ArrayList<>();

        for (var field : SearchUtils.getAllFields(filters.getClass())) {
            Searchable ann = field.getAnnotation(Searchable.class);

            if (ann == null) {
                continue;
            }

            var value = getValue(filters, field);

            if (value == null && ann.nullable()) {
                continue;
            }

            var pathKey = ann.field().isBlank()
                    ? field.getName()
                    : ann.field();

            Path<?> path = resolvePath(root, pathKey);

            var predicate = buildPredicate(
                    cb,
                    path,
                    value,
                    ann.type()
            );

            if (ann.isEltOr()) {
                orPredicates.add(predicate);
            } else {
                andPredicates.add(predicate);
            }
        }

        var andPart = andPredicates.isEmpty() ? null : cb.and(andPredicates.toArray(new Predicate[0]));
        var orPart = orPredicates.isEmpty() ? null : cb.or(orPredicates.toArray(new Predicate[0]));

        if (andPart != null && orPart != null) {
            return cb.and(andPart, orPart);
        }

        return andPart != null ? andPart : orPart != null ? orPart : cb.conjunction();
    }


    private static Object getValue(Object obj, Field field) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new ApiException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.REFLECTION_ACCESS_ERROR,
                    "Cannot access field: " + field.getName()
            );
        }
    }

    private static Path<?> resolvePath(
            Root<?> root,
            String fieldPath
    ) {
        var parts = fieldPath.split("\\.");

        Path<?> path = root;

        for (var part : parts) {
            path = path.get(part);
        }

        return path;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Predicate buildPredicate(
            CriteriaBuilder cb,
            Path<?> path,
            Object value,
            Searchable.Type type
    ) {

        return switch (type) {

            case EQUAL -> cb.equal(path, value);

            case CONTAINS -> cb.like(
                    cb.lower(path.as(String.class)),
                    "%" + value.toString().toLowerCase() + "%"
            );

            case STARTS_WITH -> cb.like(
                    cb.lower(path.as(String.class)),
                    value.toString().toLowerCase() + "%"
            );

            case ENDS_WITH -> cb.like(
                    cb.lower(path.as(String.class)),
                    "%" + value.toString().toLowerCase()
            );

            case GREATER_THAN -> cb.greaterThan(
                    (Expression<? extends Comparable>) path,
                    (Comparable) value
            );

            case LESS_THAN -> cb.lessThan(
                    (Expression<? extends Comparable>) path,
                    (Comparable) value
            );

            case GREATER_OR_EQUAL -> cb.greaterThanOrEqualTo(
                    (Expression<? extends Comparable>) path,
                    (Comparable) value
            );

            case LESS_OR_EQUAL -> cb.lessThanOrEqualTo(
                    (Expression<? extends Comparable>) path,
                    (Comparable) value
            );

            case IN -> path.in(value);
        };
    }
}