package com.service.mapper;

import com.domain.*;
import com.service.dto.DelegationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Delegation} and its DTO {@link DelegationDTO}.
 */
@Mapper(componentModel = "spring", uses = {DelegationMembersMapper.class})
public interface DelegationMapper extends EntityMapper<DelegationDTO, Delegation> {

    @Mapping(source = "delegationMembers.id", target = "delegationMembersId")
    DelegationDTO toDto(Delegation delegation);

    @Mapping(source = "delegationMembersId", target = "delegationMembers")
    @Mapping(target = "teams", ignore = true)
    @Mapping(target = "removeTeam", ignore = true)
    Delegation toEntity(DelegationDTO delegationDTO);

    default Delegation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Delegation delegation = new Delegation();
        delegation.setId(id);
        return delegation;
    }
}
