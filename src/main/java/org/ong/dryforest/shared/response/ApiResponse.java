package org.ong.dryforest.shared.response;

import org.jspecify.annotations.Nullable;

import java.time.Instant;

public record ApiResponse<T>(
        String message,
        @Nullable T data,
        Instant timestamp
) {
    public static <T> ApiResponse<T> of(T data, String message) {
        return new ApiResponse<>(
                message,
                data,
                Instant.now()
        );
    }
}