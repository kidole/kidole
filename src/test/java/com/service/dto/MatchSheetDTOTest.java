package com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class MatchSheetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchSheetDTO.class);
        MatchSheetDTO matchSheetDTO1 = new MatchSheetDTO();
        matchSheetDTO1.setId(1L);
        MatchSheetDTO matchSheetDTO2 = new MatchSheetDTO();
        assertThat(matchSheetDTO1).isNotEqualTo(matchSheetDTO2);
        matchSheetDTO2.setId(matchSheetDTO1.getId());
        assertThat(matchSheetDTO1).isEqualTo(matchSheetDTO2);
        matchSheetDTO2.setId(2L);
        assertThat(matchSheetDTO1).isNotEqualTo(matchSheetDTO2);
        matchSheetDTO1.setId(null);
        assertThat(matchSheetDTO1).isNotEqualTo(matchSheetDTO2);
    }
}
