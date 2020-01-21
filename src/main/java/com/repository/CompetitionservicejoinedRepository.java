package com.repository;

import com.domain.Competitionservicejoined;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Competitionservicejoined entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionservicejoinedRepository extends JpaRepository<Competitionservicejoined, Long> {

}
