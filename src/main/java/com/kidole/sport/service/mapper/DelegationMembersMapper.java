package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.DelegationMembersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DelegationMembers} and its DTO {@link DelegationMembersDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface DelegationMembersMapper extends EntityMapper<DelegationMembersDTO, DelegationMembers> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    DelegationMembersDTO toDto(DelegationMembers delegationMembers);

    @Mapping(source = "userId", target = "user")
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
