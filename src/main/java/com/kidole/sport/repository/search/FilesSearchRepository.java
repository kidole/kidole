package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Files;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Files} entity.
 */
public interface FilesSearchRepository extends ElasticsearchRepository<Files, Long> {
}
