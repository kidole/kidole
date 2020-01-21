package com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class CompetitionServicesOfferTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitionServicesOffer.class);
        CompetitionServicesOffer competitionServicesOffer1 = new CompetitionServicesOffer();
        competitionServicesOffer1.setId(1L);
        CompetitionServicesOffer competitionServicesOffer2 = new CompetitionServicesOffer();
        competitionServicesOffer2.setId(competitionServicesOffer1.getId());
        assertThat(competitionServicesOffer1).isEqualTo(competitionServicesOffer2);
        competitionServicesOffer2.setId(2L);
        assertThat(competitionServicesOffer1).isNotEqualTo(competitionServicesOffer2);
        competitionServicesOffer1.setId(null);
        assertThat(competitionServicesOffer1).isNotEqualTo(competitionServicesOffer2);
    }
}
