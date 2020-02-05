package com.kidole.sport.repository.search;

import com.kidole.sport.domain.AccreditationStep;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AccreditationStep} entity.
 */
public interface AccreditationStepSearchRepository extends ElasticsearchRepository<AccreditationStep, Long> {
}
