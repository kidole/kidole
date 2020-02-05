package com.kidole.sport.repository.search;

import com.kidole.sport.domain.MatchSheet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MatchSheet} entity.
 */
public interface MatchSheetSearchRepository extends ElasticsearchRepository<MatchSheet, Long> {
}
