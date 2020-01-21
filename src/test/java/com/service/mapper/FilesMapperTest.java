package com.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class FilesMapperTest {

    private FilesMapper filesMapper;

    @BeforeEach
    public void setUp() {
        filesMapper = new FilesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(filesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(filesMapper.fromId(null)).isNull();
    }
}
