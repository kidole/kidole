package com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class OptionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionsDTO.class);
        OptionsDTO optionsDTO1 = new OptionsDTO();
        optionsDTO1.setId(1L);
        OptionsDTO optionsDTO2 = new OptionsDTO();
        assertThat(optionsDTO1).isNotEqualTo(optionsDTO2);
        optionsDTO2.setId(optionsDTO1.getId());
        assertThat(optionsDTO1).isEqualTo(optionsDTO2);
        optionsDTO2.setId(2L);
        assertThat(optionsDTO1).isNotEqualTo(optionsDTO2);
        optionsDTO1.setId(null);
        assertThat(optionsDTO1).isNotEqualTo(optionsDTO2);
    }
}
