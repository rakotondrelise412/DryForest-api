package org.ong.dryforest.modules.patrol.repository;


import org.ong.dryforest.modules.patrol.PatrolGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatrolGroupRepository extends JpaRepository<PatrolGroup, Long> {
}
