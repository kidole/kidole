package com.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ConfrontationMapperTest {

    private ConfrontationMapper confrontationMapper;

    @BeforeEach
    public void setUp() {
        confrontationMapper = new ConfrontationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(confrontationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(confrontationMapper.fromId(null)).isNull();
    }
}
