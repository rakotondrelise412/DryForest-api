package org.ong.dryforest.modules.zone.zoneType;

import org.ong.dryforest.enums.Locale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZoneTypeRepository
        extends JpaRepository<ZoneType, Long>,
        JpaSpecificationExecutor<ZoneType> {

    Page<ZoneType> findAllByIsDeletedFalse(Pageable pageable);

    Optional<ZoneType> findByIdAndIsDeletedFalse(Long id);

    @Query("""
            SELECT COUNT(t) > 0
            FROM ZoneTypeTranslation t
            JOIN t.zoneType z
            WHERE z.isDeleted = false
              AND t.locale = :locale
              AND LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
            """)
    boolean existsTranslationName(
            @Param("locale") Locale locale,
            @Param("name") String name
    );

    @Query("""
            SELECT COUNT(t) > 0
            FROM ZoneTypeTranslation t
            JOIN t.zoneType z
            WHERE z.isDeleted = false
              AND z.id <> :zoneTypeId
              AND t.locale = :locale
              AND LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
            """)
    boolean existsTranslationNameForOtherZoneType(
            @Param("locale") Locale locale,
            @Param("name") String name,
            @Param("zoneTypeId") Long zoneTypeId
    );
}