package org.ong.dryforest.modules.identity.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.identity.dto.AuthRequest;
import org.ong.dryforest.modules.identity.dto.AuthResponse;
import org.ong.dryforest.modules.identity.service.AuthService;
import org.ong.dryforest.shared.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return ApiResponse.of(
                authService.login(authRequest),
                "Login successful"
        );
    }
}
