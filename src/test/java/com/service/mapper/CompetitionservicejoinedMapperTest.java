package com.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CompetitionservicejoinedMapperTest {

    private CompetitionservicejoinedMapper competitionservicejoinedMapper;

    @BeforeEach
    public void setUp() {
        competitionservicejoinedMapper = new CompetitionservicejoinedMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(competitionservicejoinedMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(competitionservicejoinedMapper.fromId(null)).isNull();
    }
}
