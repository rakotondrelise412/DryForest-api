package org.ong.dryforest.modules.patrol.repository;


import org.ong.dryforest.modules.patrol.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
}
