package org.ong.dryforest.shared.exceptions;

public enum ErrorCode {

    VALIDATION_ERROR,
    INTERNAL_ERROR,
    REFLECTION_ACCESS_ERROR,

    UNAUTHORIZED,
    FORBIDDEN,

    INVALID_SORT_FIELD,

    USER_NOT_FOUND,
    USER_ALREADY_EXISTS,
}