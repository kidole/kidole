package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Confrontation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Confrontation} entity.
 */
public interface ConfrontationSearchRepository extends ElasticsearchRepository<Confrontation, Long> {
}
