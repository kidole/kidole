package com.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AccreditationStepMapperTest {

    private AccreditationStepMapper accreditationStepMapper;

    @BeforeEach
    public void setUp() {
        accreditationStepMapper = new AccreditationStepMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(accreditationStepMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(accreditationStepMapper.fromId(null)).isNull();
    }
}
