package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.CompetitionServicesOfferDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompetitionServicesOffer} and its DTO {@link CompetitionServicesOfferDTO}.
 */
@Mapper(componentModel = "spring", uses = {RubriqueMapper.class, CompetitionMapper.class})
public interface CompetitionServicesOfferMapper extends EntityMapper<CompetitionServicesOfferDTO, CompetitionServicesOffer> {

    @Mapping(source = "rubric.id", target = "rubricId")
    @Mapping(source = "rubric.rubriqueName", target = "rubricRubriqueName")
    @Mapping(source = "competition.id", target = "competitionId")
    CompetitionServicesOfferDTO toDto(CompetitionServicesOffer competitionServicesOffer);

    @Mapping(source = "rubricId", target = "rubric")
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "removeFile", ignore = true)
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
