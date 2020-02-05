package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.JourneeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Journee} and its DTO {@link JourneeDTO}.
 */
@Mapper(componentModel = "spring", uses = {PhaseMapper.class})
public interface JourneeMapper extends EntityMapper<JourneeDTO, Journee> {

    @Mapping(source = "phase.id", target = "phaseId")
    JourneeDTO toDto(Journee journee);

    @Mapping(target = "confrontations", ignore = true)
    @Mapping(target = "removeConfrontation", ignore = true)
    @Mapping(source = "phaseId", target = "phase")
    Journee toEntity(JourneeDTO journeeDTO);

    default Journee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Journee journee = new Journee();
        journee.setId(id);
        return journee;
    }
}
