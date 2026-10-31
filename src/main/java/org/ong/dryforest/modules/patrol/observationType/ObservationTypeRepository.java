package org.ong.dryforest.modules.patrol.observationType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationTypeRepository
        extends JpaRepository<ObservationType, Long>,
        JpaSpecificationExecutor<ObservationType> {

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM ObservationTypeTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
    """)
    boolean existsDuplicateTranslation(
            @Param("name") String name
    );

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM ObservationTypeTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
          AND t.observationType.id <> :observationTypeId
    """)
    boolean existsDuplicateTranslationForUpdate(
            @Param("name") String name,
            @Param("observationTypeId") Long observationTypeId
    );
}