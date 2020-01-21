package com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.web.rest.TestUtil;

public class DelegationMembersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DelegationMembers.class);
        DelegationMembers delegationMembers1 = new DelegationMembers();
        delegationMembers1.setId(1L);
        DelegationMembers delegationMembers2 = new DelegationMembers();
        delegationMembers2.setId(delegationMembers1.getId());
        assertThat(delegationMembers1).isEqualTo(delegationMembers2);
        delegationMembers2.setId(2L);
        assertThat(delegationMembers1).isNotEqualTo(delegationMembers2);
        delegationMembers1.setId(null);
        assertThat(delegationMembers1).isNotEqualTo(delegationMembers2);
    }
}
