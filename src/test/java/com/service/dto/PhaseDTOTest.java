package com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class PhaseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhaseDTO.class);
        PhaseDTO phaseDTO1 = new PhaseDTO();
        phaseDTO1.setId(1L);
        PhaseDTO phaseDTO2 = new PhaseDTO();
        assertThat(phaseDTO1).isNotEqualTo(phaseDTO2);
        phaseDTO2.setId(phaseDTO1.getId());
        assertThat(phaseDTO1).isEqualTo(phaseDTO2);
        phaseDTO2.setId(2L);
        assertThat(phaseDTO1).isNotEqualTo(phaseDTO2);
        phaseDTO1.setId(null);
        assertThat(phaseDTO1).isNotEqualTo(phaseDTO2);
    }
}
