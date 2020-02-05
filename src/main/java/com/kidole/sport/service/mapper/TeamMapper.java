package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.TeamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Team} and its DTO {@link TeamDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ConfrontationMapper.class, PoulesMapper.class, DelegationMapper.class})
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "confrontation.id", target = "confrontationId")
    @Mapping(source = "poules.id", target = "poulesId")
    @Mapping(source = "delegation.id", target = "delegationId")
    TeamDTO toDto(Team team);

    @Mapping(source = "userId", target = "user")
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
