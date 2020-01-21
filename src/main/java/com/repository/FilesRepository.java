package com.repository;

import com.domain.Files;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Files entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {

}
