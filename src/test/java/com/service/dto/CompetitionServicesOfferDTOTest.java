package com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class CompetitionServicesOfferDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitionServicesOfferDTO.class);
        CompetitionServicesOfferDTO competitionServicesOfferDTO1 = new CompetitionServicesOfferDTO();
        competitionServicesOfferDTO1.setId(1L);
        CompetitionServicesOfferDTO competitionServicesOfferDTO2 = new CompetitionServicesOfferDTO();
        assertThat(competitionServicesOfferDTO1).isNotEqualTo(competitionServicesOfferDTO2);
        competitionServicesOfferDTO2.setId(competitionServicesOfferDTO1.getId());
        assertThat(competitionServicesOfferDTO1).isEqualTo(competitionServicesOfferDTO2);
        competitionServicesOfferDTO2.setId(2L);
        assertThat(competitionServicesOfferDTO1).isNotEqualTo(competitionServicesOfferDTO2);
        competitionServicesOfferDTO1.setId(null);
        assertThat(competitionServicesOfferDTO1).isNotEqualTo(competitionServicesOfferDTO2);
    }
}
