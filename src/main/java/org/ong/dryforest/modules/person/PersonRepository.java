package org.ong.dryforest.modules.person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PersonRepository
        extends JpaRepository<Person, Long>,
        JpaSpecificationExecutor<Person> {

    @EntityGraph(attributePaths = {"site"})
    Optional<Person> findByIdAndIsDeletedFalse(Long id);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"site"})
    Page<Person> findAllByIsDeletedFalse(Pageable pageable);
}