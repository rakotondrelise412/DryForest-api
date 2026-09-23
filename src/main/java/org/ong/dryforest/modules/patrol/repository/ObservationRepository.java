package org.ong.dryforest.modules.patrol.repository;


import org.ong.dryforest.modules.patrol.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
}
