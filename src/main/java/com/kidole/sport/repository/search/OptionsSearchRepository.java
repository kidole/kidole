package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Options;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Options} entity.
 */
public interface OptionsSearchRepository extends ElasticsearchRepository<Options, Long> {
}
