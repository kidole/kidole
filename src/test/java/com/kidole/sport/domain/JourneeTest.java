package com.kidole.sport.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kidole.sport.web.rest.TestUtil;

public class JourneeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Journee.class);
        Journee journee1 = new Journee();
        journee1.setId(1L);
        Journee journee2 = new Journee();
        journee2.setId(journee1.getId());
        assertThat(journee1).isEqualTo(journee2);
        journee2.setId(2L);
        assertThat(journee1).isNotEqualTo(journee2);
        journee1.setId(null);
        assertThat(journee1).isNotEqualTo(journee2);
    }
}
