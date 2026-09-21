package org.ong.dryforest.shared.sort;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SortableField {
    String field() default "";

    boolean initialize() default false;
}