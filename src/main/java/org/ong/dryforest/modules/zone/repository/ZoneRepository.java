package org.ong.dryforest.modules.zone.repository;

import org.ong.dryforest.modules.zone.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

    Page<Zone> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Zone> findByUuidAndIsDeletedFalse(UUID uuid);
}