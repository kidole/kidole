package com.kidole.sport.repository.search;

import com.kidole.sport.domain.CompetitionServicesOffer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CompetitionServicesOffer} entity.
 */
public interface CompetitionServicesOfferSearchRepository extends ElasticsearchRepository<CompetitionServicesOffer, Long> {
}
