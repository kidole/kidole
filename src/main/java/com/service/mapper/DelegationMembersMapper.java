package com.service.mapper;

import com.domain.*;
import com.service.dto.DelegationMembersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DelegationMembers} and its DTO {@link DelegationMembersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DelegationMembersMapper extends EntityMapper<DelegationMembersDTO, DelegationMembers> {


    @Mapping(target = "users", ignore = true)
    @Mapping(target = "removeUser", ignore = true)
    @Mapping(target = "delegation", ignore = true)
    DelegationMembers toEntity(DelegationMembersDTO delegationMembersDTO);

    default DelegationMembers fromId(Long id) {
        if (id == null) {
            return null;
        }
        DelegationMembers delegationMembers = new DelegationMembers();
        delegationMembers.setId(id);
        return delegationMembers;
    }
}
