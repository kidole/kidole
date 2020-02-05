package com.kidole.sport.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kidole.sport.web.rest.TestUtil;

public class ConfrontationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Confrontation.class);
        Confrontation confrontation1 = new Confrontation();
        confrontation1.setId(1L);
        Confrontation confrontation2 = new Confrontation();
        confrontation2.setId(confrontation1.getId());
        assertThat(confrontation1).isEqualTo(confrontation2);
        confrontation2.setId(2L);
        assertThat(confrontation1).isNotEqualTo(confrontation2);
        confrontation1.setId(null);
        assertThat(confrontation1).isNotEqualTo(confrontation2);
    }
}
