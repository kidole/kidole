package com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class ConfrontationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfrontationDTO.class);
        ConfrontationDTO confrontationDTO1 = new ConfrontationDTO();
        confrontationDTO1.setId(1L);
        ConfrontationDTO confrontationDTO2 = new ConfrontationDTO();
        assertThat(confrontationDTO1).isNotEqualTo(confrontationDTO2);
        confrontationDTO2.setId(confrontationDTO1.getId());
        assertThat(confrontationDTO1).isEqualTo(confrontationDTO2);
        confrontationDTO2.setId(2L);
        assertThat(confrontationDTO1).isNotEqualTo(confrontationDTO2);
        confrontationDTO1.setId(null);
        assertThat(confrontationDTO1).isNotEqualTo(confrontationDTO2);
    }
}
