package com.kidole.sport.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AccreditationStepSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AccreditationStepSearchRepositoryMockConfiguration {

    @MockBean
    private AccreditationStepSearchRepository mockAccreditationStepSearchRepository;

}
