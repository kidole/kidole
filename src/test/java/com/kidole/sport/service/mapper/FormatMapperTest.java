package com.kidole.sport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class FormatMapperTest {

    private FormatMapper formatMapper;

    @BeforeEach
    public void setUp() {
        formatMapper = new FormatMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(formatMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(formatMapper.fromId(null)).isNull();
    }
}
