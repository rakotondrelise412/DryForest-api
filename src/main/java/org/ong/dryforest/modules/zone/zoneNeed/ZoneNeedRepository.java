
package org.ong.dryforest.modules.zone.zoneNeed;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ZoneNeedRepository
        extends JpaRepository<ZoneNeed, Long>,
        JpaSpecificationExecutor<ZoneNeed> {

    Page<ZoneNeed> findAllByIsDeletedFalse(Pageable pageable);

    Optional<ZoneNeed> findByUuidAndIsDeletedFalse(UUID uuid);
}
