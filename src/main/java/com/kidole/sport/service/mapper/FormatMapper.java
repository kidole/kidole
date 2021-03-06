package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.FormatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Format} and its DTO {@link FormatDTO}.
 */
@Mapper(componentModel = "spring", uses = {PhaseMapper.class, CompetitionMapper.class})
public interface FormatMapper extends EntityMapper<FormatDTO, Format> {

    @Mapping(source = "phase.id", target = "phaseId")
    @Mapping(source = "phase.phaseName", target = "phasePhaseName")
    @Mapping(source = "competition.id", target = "competitionId")
    FormatDTO toDto(Format format);

    @Mapping(source = "phaseId", target = "phase")
    @Mapping(source = "competitionId", target = "competition")
    Format toEntity(FormatDTO formatDTO);

    default Format fromId(Long id) {
        if (id == null) {
            return null;
        }
        Format format = new Format();
        format.setId(id);
        return format;
    }
}
