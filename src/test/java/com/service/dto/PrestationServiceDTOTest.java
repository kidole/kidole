package com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class PrestationServiceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrestationServiceDTO.class);
        PrestationServiceDTO prestationServiceDTO1 = new PrestationServiceDTO();
        prestationServiceDTO1.setId(1L);
        PrestationServiceDTO prestationServiceDTO2 = new PrestationServiceDTO();
        assertThat(prestationServiceDTO1).isNotEqualTo(prestationServiceDTO2);
        prestationServiceDTO2.setId(prestationServiceDTO1.getId());
        assertThat(prestationServiceDTO1).isEqualTo(prestationServiceDTO2);
        prestationServiceDTO2.setId(2L);
        assertThat(prestationServiceDTO1).isNotEqualTo(prestationServiceDTO2);
        prestationServiceDTO1.setId(null);
        assertThat(prestationServiceDTO1).isNotEqualTo(prestationServiceDTO2);
    }
}
