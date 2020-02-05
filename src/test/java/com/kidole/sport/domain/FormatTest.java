package com.kidole.sport.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kidole.sport.web.rest.TestUtil;

public class FormatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Format.class);
        Format format1 = new Format();
        format1.setId(1L);
        Format format2 = new Format();
        format2.setId(format1.getId());
        assertThat(format1).isEqualTo(format2);
        format2.setId(2L);
        assertThat(format1).isNotEqualTo(format2);
        format1.setId(null);
        assertThat(format1).isNotEqualTo(format2);
    }
}
