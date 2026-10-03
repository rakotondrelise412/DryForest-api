package org.ong.dryforest.modules.zone.repository;

import org.ong.dryforest.modules.zone.ZoneType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneTypeRepository
        extends JpaRepository<ZoneType, Long> {

    Page<ZoneType> findAllByIsDeletedFalse(Pageable pageable);
}