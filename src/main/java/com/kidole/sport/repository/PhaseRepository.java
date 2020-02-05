package com.kidole.sport.repository;

import com.kidole.sport.domain.Phase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Phase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {

}
