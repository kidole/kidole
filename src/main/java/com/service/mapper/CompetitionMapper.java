package com.service.mapper;

import com.domain.*;
import com.service.dto.CompetitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Competition} and its DTO {@link CompetitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompetitionMapper extends EntityMapper<CompetitionDTO, Competition> {


    @Mapping(target = "localisations", ignore = true)
    @Mapping(target = "removeLocalisation", ignore = true)
    @Mapping(target = "accreditateUsers", ignore = true)
    @Mapping(target = "removeAccreditateUser", ignore = true)
    @Mapping(target = "competitionServicesOffers", ignore = true)
    @Mapping(target = "removeCompetitionServicesOffer", ignore = true)
    @Mapping(target = "accreditationSteps", ignore = true)
    @Mapping(target = "removeAccreditationStep", ignore = true)
    @Mapping(target = "competitionservicejoineds", ignore = true)
    @Mapping(target = "removeCompetitionservicejoined", ignore = true)
    @Mapping(target = "disciplines", ignore = true)
    @Mapping(target = "removeDiscipline", ignore = true)
    @Mapping(target = "options", ignore = true)
    @Mapping(target = "removeOptions", ignore = true)
    @Mapping(target = "formats", ignore = true)
    @Mapping(target = "removeFormat", ignore = true)
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "removeFiles", ignore = true)
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
