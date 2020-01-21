package com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class CompetitionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitionDTO.class);
        CompetitionDTO competitionDTO1 = new CompetitionDTO();
        competitionDTO1.setId(1L);
        CompetitionDTO competitionDTO2 = new CompetitionDTO();
        assertThat(competitionDTO1).isNotEqualTo(competitionDTO2);
        competitionDTO2.setId(competitionDTO1.getId());
        assertThat(competitionDTO1).isEqualTo(competitionDTO2);
        competitionDTO2.setId(2L);
        assertThat(competitionDTO1).isNotEqualTo(competitionDTO2);
        competitionDTO1.setId(null);
        assertThat(competitionDTO1).isNotEqualTo(competitionDTO2);
    }
}
