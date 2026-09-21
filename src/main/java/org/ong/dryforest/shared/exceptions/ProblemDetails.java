package org.ong.dryforest.shared.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.Instant;
import java.util.Map;

public final class ProblemDetails {

    private ProblemDetails() {
    }

    public static ProblemDetail build(
            HttpStatus status,
            ErrorCode code,
            String message,
            HttpServletRequest request,
            Map<String, Object> extra
    ) {
        var problem = ProblemDetail.forStatus(status);

        problem.setTitle(code.name());
        problem.setDetail(message);

        problem.setProperty("code", code.name());
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("path", request.getRequestURI());

        if (extra != null) {
            extra.forEach(problem::setProperty);
        }

        return problem;
    }
}