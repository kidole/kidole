package com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class AccreditationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accreditation.class);
        Accreditation accreditation1 = new Accreditation();
        accreditation1.setId(1L);
        Accreditation accreditation2 = new Accreditation();
        accreditation2.setId(accreditation1.getId());
        assertThat(accreditation1).isEqualTo(accreditation2);
        accreditation2.setId(2L);
        assertThat(accreditation1).isNotEqualTo(accreditation2);
        accreditation1.setId(null);
        assertThat(accreditation1).isNotEqualTo(accreditation2);
    }
}
