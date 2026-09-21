package org.ong.dryforest.shared.security;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.identity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CurrentUser {

    public String username() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : null;
    }

    public User get() {
        var user = principal();
        if (user == null) {
            return null;
        }
        return (User) user;
    }

    private UserDetails principal() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? (UserDetails) auth.getPrincipal() : null;
    }
}