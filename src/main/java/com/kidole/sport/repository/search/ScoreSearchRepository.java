package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Score;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Score} entity.
 */
public interface ScoreSearchRepository extends ElasticsearchRepository<Score, Long> {
}
