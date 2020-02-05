package com.kidole.sport.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kidole.sport.web.rest.TestUtil;

public class MatchSheetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchSheet.class);
        MatchSheet matchSheet1 = new MatchSheet();
        matchSheet1.setId(1L);
        MatchSheet matchSheet2 = new MatchSheet();
        matchSheet2.setId(matchSheet1.getId());
        assertThat(matchSheet1).isEqualTo(matchSheet2);
        matchSheet2.setId(2L);
        assertThat(matchSheet1).isNotEqualTo(matchSheet2);
        matchSheet1.setId(null);
        assertThat(matchSheet1).isNotEqualTo(matchSheet2);
    }
}
