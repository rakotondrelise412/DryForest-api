package org.ong.dryforest.shared.repository;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.identity.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RepositoryFactory {
    private final Map<String, JpaRepository<?, ?>> repositories;

    @SuppressWarnings("unchecked")
    private <T> T getRepositoryFor(String name) {
        var repository = repositories.get(name);
        if (repository == null) {
            throw new IllegalArgumentException(String.format("Repository for %s not found", name));
        }
        return (T) repository;
    }

    public UserRepository getUserRepository() {
        return this.getRepositoryFor("userRepository");
    }
}
