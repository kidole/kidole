package com.service.mapper;

import com.domain.*;
import com.service.dto.CompetitionservicejoinedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Competitionservicejoined} and its DTO {@link CompetitionservicejoinedDTO}.
 */
@Mapper(componentModel = "spring", uses = {PrestationServiceMapper.class, CompetitionMapper.class})
public interface CompetitionservicejoinedMapper extends EntityMapper<CompetitionservicejoinedDTO, Competitionservicejoined> {

    @Mapping(source = "prestationService.id", target = "prestationServiceId")
    @Mapping(source = "competition.id", target = "competitionId")
    CompetitionservicejoinedDTO toDto(Competitionservicejoined competitionservicejoined);

    @Mapping(source = "prestationServiceId", target = "prestationService")
    @Mapping(source = "competitionId", target = "competition")
    Competitionservicejoined toEntity(CompetitionservicejoinedDTO competitionservicejoinedDTO);

    default Competitionservicejoined fromId(Long id) {
        if (id == null) {
            return null;
        }
        Competitionservicejoined competitionservicejoined = new Competitionservicejoined();
        competitionservicejoined.setId(id);
        return competitionservicejoined;
    }
}
