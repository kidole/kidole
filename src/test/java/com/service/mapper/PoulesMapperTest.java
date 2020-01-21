package com.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PoulesMapperTest {

    private PoulesMapper poulesMapper;

    @BeforeEach
    public void setUp() {
        poulesMapper = new PoulesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(poulesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(poulesMapper.fromId(null)).isNull();
    }
}
