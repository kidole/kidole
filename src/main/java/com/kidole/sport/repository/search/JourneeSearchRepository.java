package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Journee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Journee} entity.
 */
public interface JourneeSearchRepository extends ElasticsearchRepository<Journee, Long> {
}
