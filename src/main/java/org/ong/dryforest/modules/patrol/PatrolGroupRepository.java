package org.ong.dryforest.modules.patrol;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatrolGroupRepository extends JpaRepository<PatrolGroup, Long> {
}
