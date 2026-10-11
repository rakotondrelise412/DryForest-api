package org.ong.dryforest.modules.zone.zoneType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZoneTypeRepository
        extends JpaRepository<ZoneType, Long>,
        JpaSpecificationExecutor<ZoneType> {

    Page<ZoneType> findAllByIsDeletedFalse(Pageable pageable);

    Optional<ZoneType> findByIdAndIsDeletedFalse(Long id);

}
