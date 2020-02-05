package com.kidole.sport.repository;

import com.kidole.sport.domain.Rubrique;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rubrique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RubriqueRepository extends JpaRepository<Rubrique, Long> {

}
