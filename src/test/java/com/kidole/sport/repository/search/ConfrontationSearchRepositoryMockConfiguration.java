package com.kidole.sport.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ConfrontationSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ConfrontationSearchRepositoryMockConfiguration {

    @MockBean
    private ConfrontationSearchRepository mockConfrontationSearchRepository;

}
