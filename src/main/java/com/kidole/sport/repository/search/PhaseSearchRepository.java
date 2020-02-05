package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Phase;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Phase} entity.
 */
public interface PhaseSearchRepository extends ElasticsearchRepository<Phase, Long> {
}
