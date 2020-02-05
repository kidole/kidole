package com.kidole.sport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PrestationServiceMapperTest {

    private PrestationServiceMapper prestationServiceMapper;

    @BeforeEach
    public void setUp() {
        prestationServiceMapper = new PrestationServiceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(prestationServiceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(prestationServiceMapper.fromId(null)).isNull();
    }
}
