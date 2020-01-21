package com.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PhaseMapperTest {

    private PhaseMapper phaseMapper;

    @BeforeEach
    public void setUp() {
        phaseMapper = new PhaseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(phaseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(phaseMapper.fromId(null)).isNull();
    }
}
