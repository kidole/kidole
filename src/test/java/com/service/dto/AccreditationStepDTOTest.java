package com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class AccreditationStepDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccreditationStepDTO.class);
        AccreditationStepDTO accreditationStepDTO1 = new AccreditationStepDTO();
        accreditationStepDTO1.setId(1L);
        AccreditationStepDTO accreditationStepDTO2 = new AccreditationStepDTO();
        assertThat(accreditationStepDTO1).isNotEqualTo(accreditationStepDTO2);
        accreditationStepDTO2.setId(accreditationStepDTO1.getId());
        assertThat(accreditationStepDTO1).isEqualTo(accreditationStepDTO2);
        accreditationStepDTO2.setId(2L);
        assertThat(accreditationStepDTO1).isNotEqualTo(accreditationStepDTO2);
        accreditationStepDTO1.setId(null);
        assertThat(accreditationStepDTO1).isNotEqualTo(accreditationStepDTO2);
    }
}
