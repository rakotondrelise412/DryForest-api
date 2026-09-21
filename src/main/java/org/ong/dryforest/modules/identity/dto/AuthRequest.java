package org.ong.dryforest.modules.identity.dto;

public record AuthRequest(
        String username,
        String password
) {
}
