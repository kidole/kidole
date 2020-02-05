package com.kidole.sport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CompetitionMapperTest {

    private CompetitionMapper competitionMapper;

    @BeforeEach
    public void setUp() {
        competitionMapper = new CompetitionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(competitionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(competitionMapper.fromId(null)).isNull();
    }
}
