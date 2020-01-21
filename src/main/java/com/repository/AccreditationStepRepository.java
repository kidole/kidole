package com.repository;

import com.domain.AccreditationStep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccreditationStep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccreditationStepRepository extends JpaRepository<AccreditationStep, Long> {

}
