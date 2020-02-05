package com.kidole.sport.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kidole.sport.web.rest.TestUtil;

public class PrestationServiceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrestationService.class);
        PrestationService prestationService1 = new PrestationService();
        prestationService1.setId(1L);
        PrestationService prestationService2 = new PrestationService();
        prestationService2.setId(prestationService1.getId());
        assertThat(prestationService1).isEqualTo(prestationService2);
        prestationService2.setId(2L);
        assertThat(prestationService1).isNotEqualTo(prestationService2);
        prestationService1.setId(null);
        assertThat(prestationService1).isNotEqualTo(prestationService2);
    }
}
