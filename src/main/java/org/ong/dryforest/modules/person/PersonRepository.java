package org.ong.dryforest.modules.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByIdAndIsDeletedFalse(Long id);

    List<Person> findAllByIsDeletedFalse();

    boolean existsByEmailAndIsDeletedFalse(String email);
}