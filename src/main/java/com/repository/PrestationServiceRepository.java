package com.repository;

import com.domain.PrestationService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrestationService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestationServiceRepository extends JpaRepository<PrestationService, Long> {

}
