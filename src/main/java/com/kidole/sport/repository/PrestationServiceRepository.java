package com.kidole.sport.repository;

import com.kidole.sport.domain.PrestationService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PrestationService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestationServiceRepository extends JpaRepository<PrestationService, Long> {

    @Query("select prestationService from PrestationService prestationService where prestationService.user.login = ?#{principal.username}")
    List<PrestationService> findByUserIsCurrentUser();

}
