package com.service.mapper;

import com.domain.*;
import com.service.dto.CompetitionServicesOfferDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompetitionServicesOffer} and its DTO {@link CompetitionServicesOfferDTO}.
 */
@Mapper(componentModel = "spring", uses = {RubriqueMapper.class, CompetitionMapper.class})
public interface CompetitionServicesOfferMapper extends EntityMapper<CompetitionServicesOfferDTO, CompetitionServicesOffer> {

    @Mapping(source = "rubrique.id", target = "rubriqueId")
    @Mapping(source = "competition.id", target = "competitionId")
    CompetitionServicesOfferDTO toDto(CompetitionServicesOffer competitionServicesOffer);

    @Mapping(source = "rubriqueId", target = "rubrique")
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "removeFiles", ignore = true)
    @Mapping(source = "competitionId", target = "competition")
    CompetitionServicesOffer toEntity(CompetitionServicesOfferDTO competitionServicesOfferDTO);

    default CompetitionServicesOffer fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompetitionServicesOffer competitionServicesOffer = new CompetitionServicesOffer();
        competitionServicesOffer.setId(id);
        return competitionServicesOffer;
    }
}
