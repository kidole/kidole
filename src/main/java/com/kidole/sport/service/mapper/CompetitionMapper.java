package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.CompetitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Competition} and its DTO {@link CompetitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CompetitionMapper extends EntityMapper<CompetitionDTO, Competition> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    CompetitionDTO toDto(Competition competition);

    @Mapping(target = "localises", ignore = true)
    @Mapping(target = "removeLocalise", ignore = true)
    @Mapping(target = "accreditations", ignore = true)
    @Mapping(target = "removeAccreditation", ignore = true)
    @Mapping(target = "competitionServices", ignore = true)
    @Mapping(target = "removeCompetitionService", ignore = true)
    @Mapping(target = "accreditationSteps", ignore = true)
    @Mapping(target = "removeAccreditationStep", ignore = true)
    @Mapping(target = "formats", ignore = true)
    @Mapping(target = "removeFormat", ignore = true)
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "removeFiles", ignore = true)
    @Mapping(source = "userId", target = "user")
    Competition toEntity(CompetitionDTO competitionDTO);

    default Competition fromId(Long id) {
        if (id == null) {
            return null;
        }
        Competition competition = new Competition();
        competition.setId(id);
        return competition;
    }
}
