package com.kidole.sport.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kidole.sport.web.rest.TestUtil;

public class AccreditationStepTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccreditationStep.class);
        AccreditationStep accreditationStep1 = new AccreditationStep();
        accreditationStep1.setId(1L);
        AccreditationStep accreditationStep2 = new AccreditationStep();
        accreditationStep2.setId(accreditationStep1.getId());
        assertThat(accreditationStep1).isEqualTo(accreditationStep2);
        accreditationStep2.setId(2L);
        assertThat(accreditationStep1).isNotEqualTo(accreditationStep2);
        accreditationStep1.setId(null);
        assertThat(accreditationStep1).isNotEqualTo(accreditationStep2);
    }
}
