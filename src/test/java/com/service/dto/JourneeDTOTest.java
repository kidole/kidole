package com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class JourneeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneeDTO.class);
        JourneeDTO journeeDTO1 = new JourneeDTO();
        journeeDTO1.setId(1L);
        JourneeDTO journeeDTO2 = new JourneeDTO();
        assertThat(journeeDTO1).isNotEqualTo(journeeDTO2);
        journeeDTO2.setId(journeeDTO1.getId());
        assertThat(journeeDTO1).isEqualTo(journeeDTO2);
        journeeDTO2.setId(2L);
        assertThat(journeeDTO1).isNotEqualTo(journeeDTO2);
        journeeDTO1.setId(null);
        assertThat(journeeDTO1).isNotEqualTo(journeeDTO2);
    }
}
