package com.kidole.sport.repository.search;

import com.kidole.sport.domain.PrestationService;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PrestationService} entity.
 */
public interface PrestationServiceSearchRepository extends ElasticsearchRepository<PrestationService, Long> {
}
