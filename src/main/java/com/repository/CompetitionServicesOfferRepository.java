package com.repository;

import com.domain.CompetitionServicesOffer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompetitionServicesOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionServicesOfferRepository extends JpaRepository<CompetitionServicesOffer, Long> {

}
