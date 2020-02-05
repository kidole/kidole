package com.kidole.sport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class RubriqueMapperTest {

    private RubriqueMapper rubriqueMapper;

    @BeforeEach
    public void setUp() {
        rubriqueMapper = new RubriqueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(rubriqueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rubriqueMapper.fromId(null)).isNull();
    }
}
