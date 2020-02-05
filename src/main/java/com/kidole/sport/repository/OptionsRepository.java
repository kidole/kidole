package com.kidole.sport.repository;

import com.kidole.sport.domain.Options;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Options entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionsRepository extends JpaRepository<Options, Long> {

}
