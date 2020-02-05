package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Rubrique;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Rubrique} entity.
 */
public interface RubriqueSearchRepository extends ElasticsearchRepository<Rubrique, Long> {
}
