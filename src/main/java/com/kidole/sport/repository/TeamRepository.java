package com.kidole.sport.repository;

import com.kidole.sport.domain.Team;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Team entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select team from Team team where team.user.login = ?#{principal.username}")
    List<Team> findByUserIsCurrentUser();

}
