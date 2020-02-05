package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Delegation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Delegation} entity.
 */
public interface DelegationSearchRepository extends ElasticsearchRepository<Delegation, Long> {
}
