package org.ong.dryforest.shared.search;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Searchable {

    String field() default "";

    Type type() default Type.EQUAL;

    boolean nullable() default true;

    boolean isEltOr() default false;

    enum Type {
        EQUAL,
        CONTAINS,
        STARTS_WITH,
        ENDS_WITH,
        GREATER_THAN,
        GREATER_OR_EQUAL,
        LESS_THAN,
        LESS_OR_EQUAL,
        IN,
    }
}