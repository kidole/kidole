package com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class CompetitionservicejoinedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competitionservicejoined.class);
        Competitionservicejoined competitionservicejoined1 = new Competitionservicejoined();
        competitionservicejoined1.setId(1L);
        Competitionservicejoined competitionservicejoined2 = new Competitionservicejoined();
        competitionservicejoined2.setId(competitionservicejoined1.getId());
        assertThat(competitionservicejoined1).isEqualTo(competitionservicejoined2);
        competitionservicejoined2.setId(2L);
        assertThat(competitionservicejoined1).isNotEqualTo(competitionservicejoined2);
        competitionservicejoined1.setId(null);
        assertThat(competitionservicejoined1).isNotEqualTo(competitionservicejoined2);
    }
}
