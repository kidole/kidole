package com.kidole.sport.repository;

import com.kidole.sport.domain.Files;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Files entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {

    @Query("select files from Files files where files.user.login = ?#{principal.username}")
    List<Files> findByUserIsCurrentUser();

}
