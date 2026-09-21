package org.ong.dryforest.modules.identity.dto;

import java.io.Serializable;

public record AuthResponse(
        String accessToken
) implements Serializable {
}
