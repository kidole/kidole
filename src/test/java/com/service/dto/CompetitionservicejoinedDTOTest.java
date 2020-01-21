package com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class CompetitionservicejoinedDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitionservicejoinedDTO.class);
        CompetitionservicejoinedDTO competitionservicejoinedDTO1 = new CompetitionservicejoinedDTO();
        competitionservicejoinedDTO1.setId(1L);
        CompetitionservicejoinedDTO competitionservicejoinedDTO2 = new CompetitionservicejoinedDTO();
        assertThat(competitionservicejoinedDTO1).isNotEqualTo(competitionservicejoinedDTO2);
        competitionservicejoinedDTO2.setId(competitionservicejoinedDTO1.getId());
        assertThat(competitionservicejoinedDTO1).isEqualTo(competitionservicejoinedDTO2);
        competitionservicejoinedDTO2.setId(2L);
        assertThat(competitionservicejoinedDTO1).isNotEqualTo(competitionservicejoinedDTO2);
        competitionservicejoinedDTO1.setId(null);
        assertThat(competitionservicejoinedDTO1).isNotEqualTo(competitionservicejoinedDTO2);
    }
}
