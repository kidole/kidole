package com.kidole.sport.repository;

import com.kidole.sport.domain.DelegationMembers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the DelegationMembers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DelegationMembersRepository extends JpaRepository<DelegationMembers, Long> {

    @Query("select delegationMembers from DelegationMembers delegationMembers where delegationMembers.user.login = ?#{principal.username}")
    List<DelegationMembers> findByUserIsCurrentUser();

}
