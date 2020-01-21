package com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class FilesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Files.class);
        Files files1 = new Files();
        files1.setId(1L);
        Files files2 = new Files();
        files2.setId(files1.getId());
        assertThat(files1).isEqualTo(files2);
        files2.setId(2L);
        assertThat(files1).isNotEqualTo(files2);
        files1.setId(null);
        assertThat(files1).isNotEqualTo(files2);
    }
}
