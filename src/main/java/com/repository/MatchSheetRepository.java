package com.repository;

import com.domain.MatchSheet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MatchSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchSheetRepository extends JpaRepository<MatchSheet, Long> {

}
