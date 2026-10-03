package org.ong.dryforest.modules.zone.repository;

import org.ong.dryforest.modules.zone.ZoneNeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ZoneNeedRepository
        extends JpaRepository<ZoneNeed, Long> {

    Page<ZoneNeed> findAllByIsDeletedFalse(Pageable pageable);

    Optional<ZoneNeed> findByUuidAndIsDeletedFalse(UUID uuid);
}