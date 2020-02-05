package com.kidole.sport.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kidole.sport.web.rest.TestUtil;

public class AccreditationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccreditationDTO.class);
        AccreditationDTO accreditationDTO1 = new AccreditationDTO();
        accreditationDTO1.setId(1L);
        AccreditationDTO accreditationDTO2 = new AccreditationDTO();
        assertThat(accreditationDTO1).isNotEqualTo(accreditationDTO2);
        accreditationDTO2.setId(accreditationDTO1.getId());
        assertThat(accreditationDTO1).isEqualTo(accreditationDTO2);
        accreditationDTO2.setId(2L);
        assertThat(accreditationDTO1).isNotEqualTo(accreditationDTO2);
        accreditationDTO1.setId(null);
        assertThat(accreditationDTO1).isNotEqualTo(accreditationDTO2);
    }
}
