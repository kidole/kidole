package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.LocalisationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Localisation} and its DTO {@link LocalisationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitionMapper.class, PrestationServiceMapper.class})
public interface LocalisationMapper extends EntityMapper<LocalisationDTO, Localisation> {

    @Mapping(source = "competition.id", target = "competitionId")
    @Mapping(source = "prestationService.id", target = "prestationServiceId")
    LocalisationDTO toDto(Localisation localisation);

    @Mapping(source = "competitionId", target = "competition")
    @Mapping(source = "prestationServiceId", target = "prestationService")
    Localisation toEntity(LocalisationDTO localisationDTO);

    default Localisation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Localisation localisation = new Localisation();
        localisation.setId(id);
        return localisation;
    }
}
