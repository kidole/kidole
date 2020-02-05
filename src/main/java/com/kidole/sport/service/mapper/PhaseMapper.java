package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.PhaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Phase} and its DTO {@link PhaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PhaseMapper extends EntityMapper<PhaseDTO, Phase> {


    @Mapping(target = "journnees", ignore = true)
    @Mapping(target = "removeJournnee", ignore = true)
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
