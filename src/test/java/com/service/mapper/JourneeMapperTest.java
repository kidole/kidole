package com.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class JourneeMapperTest {

    private JourneeMapper journeeMapper;

    @BeforeEach
    public void setUp() {
        journeeMapper = new JourneeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(journeeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(journeeMapper.fromId(null)).isNull();
    }
}
