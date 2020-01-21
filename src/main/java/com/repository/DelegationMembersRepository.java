package com.repository;

import com.domain.DelegationMembers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DelegationMembers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DelegationMembersRepository extends JpaRepository<DelegationMembers, Long> {

}
