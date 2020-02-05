package com.kidole.sport.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link MatchSheetSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MatchSheetSearchRepositoryMockConfiguration {

    @MockBean
    private MatchSheetSearchRepository mockMatchSheetSearchRepository;

}