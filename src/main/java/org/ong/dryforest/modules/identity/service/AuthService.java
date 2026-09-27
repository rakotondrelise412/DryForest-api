package org.ong.dryforest.modules.identity.service;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.identity.User;
import org.ong.dryforest.modules.identity.UserMapper;
import org.ong.dryforest.modules.identity.UserRepository;
import org.ong.dryforest.modules.identity.dto.AuthRequest;
import org.ong.dryforest.modules.identity.dto.AuthResponse;
import org.ong.dryforest.modules.identity.dto.UserDto;
import org.ong.dryforest.shared.security.CurrentUser;
import org.ong.dryforest.shared.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final CurrentUser currentUser;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthResponse login(AuthRequest authRequest) {

        var authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        authRequest.username(),
                        authRequest.password()
                );

        var authenticationResponse =
                authenticationManager.authenticate(authenticationRequest);

        var ctx = SecurityContextHolder.getContext();
        ctx.setAuthentication(authenticationResponse);

        var user = currentUser.username();

        return new AuthResponse(
                jwtService.generateToken(user)
        );
    }

    @Transactional(readOnly = true)
    public UserDto getCurrentUser() {

        String username = currentUser.username();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Utilisateur introuvable : " + username
                        )
                );

        return userMapper.toDto(user);
    }
}