package com.kidole.sport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CompetitionServicesOfferMapperTest {

    private CompetitionServicesOfferMapper competitionServicesOfferMapper;

    @BeforeEach
    public void setUp() {
        competitionServicesOfferMapper = new CompetitionServicesOfferMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(competitionServicesOfferMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(competitionServicesOfferMapper.fromId(null)).isNull();
    }
}
