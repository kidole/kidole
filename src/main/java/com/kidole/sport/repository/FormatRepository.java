package com.kidole.sport.repository;

import com.kidole.sport.domain.Format;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Format entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormatRepository extends JpaRepository<Format, Long> {

}
