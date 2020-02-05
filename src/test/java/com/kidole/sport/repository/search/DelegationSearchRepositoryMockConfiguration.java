package com.kidole.sport.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DelegationSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DelegationSearchRepositoryMockConfiguration {

    @MockBean
    private DelegationSearchRepository mockDelegationSearchRepository;

}
