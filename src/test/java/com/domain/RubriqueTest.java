package com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class RubriqueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rubrique.class);
        Rubrique rubrique1 = new Rubrique();
        rubrique1.setId(1L);
        Rubrique rubrique2 = new Rubrique();
        rubrique2.setId(rubrique1.getId());
        assertThat(rubrique1).isEqualTo(rubrique2);
        rubrique2.setId(2L);
        assertThat(rubrique1).isNotEqualTo(rubrique2);
        rubrique1.setId(null);
        assertThat(rubrique1).isNotEqualTo(rubrique2);
    }
}
