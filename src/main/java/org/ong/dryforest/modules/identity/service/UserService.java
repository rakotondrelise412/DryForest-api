package org.ong.dryforest.modules.identity.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ong.dryforest.shared.repository.RepositoryFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final RepositoryFactory factory;

    @Override
    @SneakyThrows(UsernameNotFoundException.class)
    public UserDetails loadUserByUsername(String username) {
        return factory.getUserRepository()
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}