package com.service.mapper;

import com.domain.*;
import com.service.dto.PhaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Phase} and its DTO {@link PhaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {DisciplineMapper.class})
public interface PhaseMapper extends EntityMapper<PhaseDTO, Phase> {

    @Mapping(source = "discipline.id", target = "disciplineId")
    PhaseDTO toDto(Phase phase);

    @Mapping(target = "journees", ignore = true)
    @Mapping(target = "removeJournee", ignore = true)
    @Mapping(target = "format", ignore = true)
    @Mapping(source = "disciplineId", target = "discipline")
    Phase toEntity(PhaseDTO phaseDTO);

    default Phase fromId(Long id) {
        if (id == null) {
            return null;
        }
        Phase phase = new Phase();
        phase.setId(id);
        return phase;
    }
}
