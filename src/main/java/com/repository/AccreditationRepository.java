package com.repository;

import com.domain.Accreditation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Accreditation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccreditationRepository extends JpaRepository<Accreditation, Long> {

}
