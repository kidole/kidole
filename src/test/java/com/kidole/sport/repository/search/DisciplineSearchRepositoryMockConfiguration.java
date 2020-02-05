package com.kidole.sport.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DisciplineSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DisciplineSearchRepositoryMockConfiguration {

    @MockBean
    private DisciplineSearchRepository mockDisciplineSearchRepository;

}
