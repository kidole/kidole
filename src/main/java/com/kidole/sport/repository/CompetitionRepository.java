package com.kidole.sport.repository;

import com.kidole.sport.domain.Competition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Competition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    @Query("select competition from Competition competition where competition.user.login = ?#{principal.username}")
    List<Competition> findByUserIsCurrentUser();

}
