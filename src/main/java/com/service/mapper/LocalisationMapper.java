package com.service.mapper;

import com.domain.*;
import com.service.dto.LocalisationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Localisation} and its DTO {@link LocalisationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitionMapper.class, PrestationServiceMapper.class})
public interface LocalisationMapper extends EntityMapper<LocalisationDTO, Localisation> {

    @Mapping(source = "competition.id", target = "competitionId")
    @Mapping(source = "prestationService.id", target = "prestationServiceId")
    LocalisationDTO toDto(Localisation localisation);

    @Mapping(target = "confrontation", ignore = true)
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
