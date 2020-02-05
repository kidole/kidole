package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.FilesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Files} and its DTO {@link FilesDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CompetitionServicesOfferMapper.class, PrestationServiceMapper.class, RubriqueMapper.class, CompetitionMapper.class})
public interface FilesMapper extends EntityMapper<FilesDTO, Files> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "competitionServicesOffer.id", target = "competitionServicesOfferId")
    @Mapping(source = "prestationService.id", target = "prestationServiceId")
    @Mapping(source = "rubrique.id", target = "rubriqueId")
    @Mapping(source = "competition.id", target = "competitionId")
    FilesDTO toDto(Files files);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "competitionServicesOfferId", target = "competitionServicesOffer")
    @Mapping(source = "prestationServiceId", target = "prestationService")
    @Mapping(source = "rubriqueId", target = "rubrique")
    @Mapping(source = "competitionId", target = "competition")
    Files toEntity(FilesDTO filesDTO);

    default Files fromId(Long id) {
        if (id == null) {
            return null;
        }
        Files files = new Files();
        files.setId(id);
        return files;
    }
}
