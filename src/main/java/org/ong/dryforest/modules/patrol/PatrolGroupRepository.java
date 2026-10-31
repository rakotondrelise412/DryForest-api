package org.ong.dryforest.modules.patrol;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatrolGroupRepository
        extends JpaRepository<PatrolGroup, Long>,
        JpaSpecificationExecutor<PatrolGroup> {

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM PatrolGroupTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
    """)
    boolean existsDuplicateTranslation(
            @Param("name") String name
    );

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM PatrolGroupTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
          AND t.patrolGroup.id <> :patrolGroupId
    """)
    boolean existsDuplicateTranslationForUpdate(
            @Param("name") String name,
            @Param("patrolGroupId") Long patrolGroupId
    );
}