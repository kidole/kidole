package com.kidole.sport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DelegationMembersMapperTest {

    private DelegationMembersMapper delegationMembersMapper;

    @BeforeEach
    public void setUp() {
        delegationMembersMapper = new DelegationMembersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(delegationMembersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(delegationMembersMapper.fromId(null)).isNull();
    }
}
