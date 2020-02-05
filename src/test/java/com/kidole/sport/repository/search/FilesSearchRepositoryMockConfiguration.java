package com.kidole.sport.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link FilesSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FilesSearchRepositoryMockConfiguration {

    @MockBean
    private FilesSearchRepository mockFilesSearchRepository;

}
