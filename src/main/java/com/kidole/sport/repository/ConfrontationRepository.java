package com.kidole.sport.repository;

import com.kidole.sport.domain.Confrontation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Confrontation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfrontationRepository extends JpaRepository<Confrontation, Long> {

}
