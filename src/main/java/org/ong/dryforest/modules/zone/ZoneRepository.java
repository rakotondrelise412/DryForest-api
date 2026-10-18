package org.ong.dryforest.modules.zone;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ZoneRepository extends JpaRepository<Zone, Long>, JpaSpecificationExecutor<Zone> {

    Page<Zone> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Zone> findByUuidAndIsDeletedFalse(UUID uuid);

}