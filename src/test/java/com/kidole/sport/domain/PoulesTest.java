package com.kidole.sport.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kidole.sport.web.rest.TestUtil;

public class PoulesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Poules.class);
        Poules poules1 = new Poules();
        poules1.setId(1L);
        Poules poules2 = new Poules();
        poules2.setId(poules1.getId());
        assertThat(poules1).isEqualTo(poules2);
        poules2.setId(2L);
        assertThat(poules1).isNotEqualTo(poules2);
        poules1.setId(null);
        assertThat(poules1).isNotEqualTo(poules2);
    }
}
