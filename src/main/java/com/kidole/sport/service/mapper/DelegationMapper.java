package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.DelegationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Delegation} and its DTO {@link DelegationDTO}.
 */
@Mapper(componentModel = "spring", uses = {DelegationMembersMapper.class})
public interface DelegationMapper extends EntityMapper<DelegationDTO, Delegation> {

    @Mapping(source = "delegateMember.id", target = "delegateMemberId")
    @Mapping(source = "delegateMember.delegationMembersCode", target = "delegateMemberDelegationMembersCode")
    DelegationDTO toDto(Delegation delegation);

    @Mapping(source = "delegateMemberId", target = "delegateMember")
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
