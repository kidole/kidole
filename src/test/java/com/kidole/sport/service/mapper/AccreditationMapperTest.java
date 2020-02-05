package com.kidole.sport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AccreditationMapperTest {

    private AccreditationMapper accreditationMapper;

    @BeforeEach
    public void setUp() {
        accreditationMapper = new AccreditationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(accreditationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(accreditationMapper.fromId(null)).isNull();
    }
}
