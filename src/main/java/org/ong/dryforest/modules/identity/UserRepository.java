package org.ong.dryforest.modules.identity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @EntityGraph(attributePaths = {"person"})
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"person"})
    Optional<User> findById(Long id);

    boolean existsByUsername(String username);
}