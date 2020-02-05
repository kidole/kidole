package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Poules;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Poules} entity.
 */
public interface PoulesSearchRepository extends ElasticsearchRepository<Poules, Long> {
}
