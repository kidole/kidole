package com.kidole.sport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProfileMapperTest {

    private ProfileMapper profileMapper;

    @BeforeEach
    public void setUp() {
        profileMapper = new ProfileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(profileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(profileMapper.fromId(null)).isNull();
    }
}
