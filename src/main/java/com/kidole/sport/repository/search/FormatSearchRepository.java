package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Format;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Format} entity.
 */
public interface FormatSearchRepository extends ElasticsearchRepository<Format, Long> {
}
