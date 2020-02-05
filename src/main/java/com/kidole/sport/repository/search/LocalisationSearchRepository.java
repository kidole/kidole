package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Localisation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Localisation} entity.
 */
public interface LocalisationSearchRepository extends ElasticsearchRepository<Localisation, Long> {
}
