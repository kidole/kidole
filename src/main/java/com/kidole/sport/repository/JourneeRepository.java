package com.kidole.sport.repository;

import com.kidole.sport.domain.Journee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Journee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JourneeRepository extends JpaRepository<Journee, Long> {

}
