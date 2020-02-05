package com.kidole.sport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MatchSheetMapperTest {

    private MatchSheetMapper matchSheetMapper;

    @BeforeEach
    public void setUp() {
        matchSheetMapper = new MatchSheetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(matchSheetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(matchSheetMapper.fromId(null)).isNull();
    }
}
