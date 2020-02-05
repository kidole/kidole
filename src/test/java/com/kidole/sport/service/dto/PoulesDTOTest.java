package com.kidole.sport.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kidole.sport.web.rest.TestUtil;

public class PoulesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoulesDTO.class);
        PoulesDTO poulesDTO1 = new PoulesDTO();
        poulesDTO1.setId(1L);
        PoulesDTO poulesDTO2 = new PoulesDTO();
        assertThat(poulesDTO1).isNotEqualTo(poulesDTO2);
        poulesDTO2.setId(poulesDTO1.getId());
        assertThat(poulesDTO1).isEqualTo(poulesDTO2);
        poulesDTO2.setId(2L);
        assertThat(poulesDTO1).isNotEqualTo(poulesDTO2);
        poulesDTO1.setId(null);
        assertThat(poulesDTO1).isNotEqualTo(poulesDTO2);
    }
}
