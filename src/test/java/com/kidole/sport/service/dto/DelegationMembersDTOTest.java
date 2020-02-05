package com.kidole.sport.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kidole.sport.web.rest.TestUtil;

public class DelegationMembersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DelegationMembersDTO.class);
        DelegationMembersDTO delegationMembersDTO1 = new DelegationMembersDTO();
        delegationMembersDTO1.setId(1L);
        DelegationMembersDTO delegationMembersDTO2 = new DelegationMembersDTO();
        assertThat(delegationMembersDTO1).isNotEqualTo(delegationMembersDTO2);
        delegationMembersDTO2.setId(delegationMembersDTO1.getId());
        assertThat(delegationMembersDTO1).isEqualTo(delegationMembersDTO2);
        delegationMembersDTO2.setId(2L);
        assertThat(delegationMembersDTO1).isNotEqualTo(delegationMembersDTO2);
        delegationMembersDTO1.setId(null);
        assertThat(delegationMembersDTO1).isNotEqualTo(delegationMembersDTO2);
    }
}
