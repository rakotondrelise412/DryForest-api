package org.ong.dryforest.modules.identity.service;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.identity.dto.AuthRequest;
import org.ong.dryforest.modules.identity.dto.AuthResponse;
import org.ong.dryforest.shared.security.CurrentUser;
import org.ong.dryforest.shared.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final CurrentUser currentUser;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(AuthRequest authRequest) {
        var authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                authRequest.username(),
                authRequest.password()
        );
        var authenticationResponse = authenticationManager.authenticate(authenticationRequest);
        var ctx = SecurityContextHolder.getContext();
        ctx.setAuthentication(authenticationResponse);
        var user = currentUser.username();
        return new AuthResponse(jwtService.generateToken(user));
    }
}
