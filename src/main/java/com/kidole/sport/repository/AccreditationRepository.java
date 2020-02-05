package com.kidole.sport.repository;

import com.kidole.sport.domain.Accreditation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Accreditation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccreditationRepository extends JpaRepository<Accreditation, Long> {

}
