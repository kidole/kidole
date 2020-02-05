package com.kidole.sport.repository;

import com.kidole.sport.domain.Poules;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Poules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoulesRepository extends JpaRepository<Poules, Long> {

}
