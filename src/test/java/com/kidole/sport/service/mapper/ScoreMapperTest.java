package com.kidole.sport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ScoreMapperTest {

    private ScoreMapper scoreMapper;

    @BeforeEach
    public void setUp() {
        scoreMapper = new ScoreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(scoreMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scoreMapper.fromId(null)).isNull();
    }
}
