package com.service.mapper;

import com.domain.*;
import com.service.dto.TeamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Team} and its DTO {@link TeamDTO}.
 */
@Mapper(componentModel = "spring", uses = {ConfrontationMapper.class, PoulesMapper.class, DelegationMapper.class})
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {

    @Mapping(source = "confrontation.id", target = "confrontationId")
    @Mapping(source = "poules.id", target = "poulesId")
    @Mapping(source = "delegation.id", target = "delegationId")
    TeamDTO toDto(Team team);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "removeUser", ignore = true)
    @Mapping(target = "score", ignore = true)
    @Mapping(source = "confrontationId", target = "confrontation")
    @Mapping(source = "poulesId", target = "poules")
    @Mapping(source = "delegationId", target = "delegation")
    Team toEntity(TeamDTO teamDTO);

    default Team fromId(Long id) {
        if (id == null) {
            return null;
        }
        Team team = new Team();
        team.setId(id);
        return team;
    }
}
