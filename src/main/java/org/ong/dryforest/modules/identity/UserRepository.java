package org.ong.dryforest.modules.identity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @EntityGraph(attributePaths = {"person", "person.site"})
    Optional<User> findByUsernameAndIsDeletedFalse(String username);

    @EntityGraph(attributePaths = {"person", "person.site"})
    Optional<User> findByIdAndIsDeletedFalse(Long id);

    @EntityGraph(attributePaths = {"person", "person.site"})
    List<User> findAllByIsDeletedFalse();

    boolean existsByUsername(String username);
}