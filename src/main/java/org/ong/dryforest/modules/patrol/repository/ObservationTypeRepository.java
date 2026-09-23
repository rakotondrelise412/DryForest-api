package org.ong.dryforest.modules.patrol.repository;


import org.ong.dryforest.modules.patrol.ObservationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationTypeRepository extends JpaRepository<ObservationType, Long> {
}
