package com.kidole.sport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class OptionsMapperTest {

    private OptionsMapper optionsMapper;

    @BeforeEach
    public void setUp() {
        optionsMapper = new OptionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(optionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(optionsMapper.fromId(null)).isNull();
    }
}
