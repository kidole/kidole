package com.kidole.sport.repository.search;

import com.kidole.sport.domain.Discipline;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Discipline} entity.
 */
public interface DisciplineSearchRepository extends ElasticsearchRepository<Discipline, Long> {
}
