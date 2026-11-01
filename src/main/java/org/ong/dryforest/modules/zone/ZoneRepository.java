package org.ong.dryforest.modules.zone;

import org.ong.dryforest.enums.Locale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ZoneRepository
        extends JpaRepository<Zone, Long>,
        JpaSpecificationExecutor<Zone> {

    Page<Zone> findAllByIsDeletedFalse(
            Pageable pageable
    );

    Optional<Zone> findByUuidAndIsDeletedFalse(
            UUID uuid
    );


    @Query("""
        SELECT CASE WHEN COUNT(t) > 0
                   THEN true
                   ELSE false
              END
        FROM ZoneTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
          AND t.locale = :locale
          AND t.zone.isDeleted = false
    """)
    boolean existsDuplicateTranslation(
            @Param("name") String name,
            @Param("locale") Locale locale
    );


    @Query("""
        SELECT CASE WHEN COUNT(t) > 0
                   THEN true
                   ELSE false
              END
        FROM ZoneTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
          AND t.locale = :locale
          AND t.zone.id <> :zoneId
          AND t.zone.isDeleted = false
    """)
    boolean existsDuplicateTranslationForUpdate(
            @Param("name") String name,
            @Param("locale") Locale locale,
            @Param("zoneId") Long zoneId
    );
}