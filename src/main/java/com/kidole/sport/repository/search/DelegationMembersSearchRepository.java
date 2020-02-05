package com.kidole.sport.repository.search;

import com.kidole.sport.domain.DelegationMembers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DelegationMembers} entity.
 */
public interface DelegationMembersSearchRepository extends ElasticsearchRepository<DelegationMembers, Long> {
}
