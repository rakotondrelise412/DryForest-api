package org.ong.dryforest.modules.patrol.incidentType;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentTypeRepository
        extends JpaRepository<IncidentType, Long>,
        JpaSpecificationExecutor<IncidentType> {

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM IncidentTypeTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
    """)
    boolean existsDuplicateTranslation(
            @Param("name") String name
    );

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM IncidentTypeTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
          AND t.incidentType.id <> :incidentTypeId
    """)
    boolean existsDuplicateTranslationForUpdate(
            @Param("name") String name,
            @Param("incidentTypeId") Long incidentTypeId
    );
}
