package org.ong.dryforest.shared.utils;

import org.ong.dryforest.shared.exceptions.ApiException;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Supplier;

public final class ServiceUtils {

    private ServiceUtils() {
    }

    public static <T> T getOrThrow(
            Supplier<Optional<T>> supplier,
            ErrorCode code,
            String message
    ) {
        return getOrThrow(
                supplier,
                code,
                message,
                HttpStatus.NOT_FOUND
        );
    }

    public static <T> T getOrThrow(
            Supplier<Optional<T>> supplier,
            ErrorCode code,
            String message,
            HttpStatus status
    ) {
        return supplier.get()
                .orElseThrow(() ->
                        new ApiException(
                                status,
                                code,
                                message
                        )
                );
    }
}