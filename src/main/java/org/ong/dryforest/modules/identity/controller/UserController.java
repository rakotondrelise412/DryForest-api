package org.ong.dryforest.modules.identity.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.identity.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}