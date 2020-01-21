package com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class ScoreDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScoreDTO.class);
        ScoreDTO scoreDTO1 = new ScoreDTO();
        scoreDTO1.setId(1L);
        ScoreDTO scoreDTO2 = new ScoreDTO();
        assertThat(scoreDTO1).isNotEqualTo(scoreDTO2);
        scoreDTO2.setId(scoreDTO1.getId());
        assertThat(scoreDTO1).isEqualTo(scoreDTO2);
        scoreDTO2.setId(2L);
        assertThat(scoreDTO1).isNotEqualTo(scoreDTO2);
        scoreDTO1.setId(null);
        assertThat(scoreDTO1).isNotEqualTo(scoreDTO2);
    }
}
