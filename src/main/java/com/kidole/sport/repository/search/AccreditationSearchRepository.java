package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Accreditation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Accreditation} entity.
 */
public interface AccreditationSearchRepository extends ElasticsearchRepository<Accreditation, Long> {
}
