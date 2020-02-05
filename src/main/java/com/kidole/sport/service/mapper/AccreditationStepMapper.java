package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.AccreditationStepDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccreditationStep} and its DTO {@link AccreditationStepDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitionMapper.class})
public interface AccreditationStepMapper extends EntityMapper<AccreditationStepDTO, AccreditationStep> {

    @Mapping(source = "competition.id", target = "competitionId")
    AccreditationStepDTO toDto(AccreditationStep accreditationStep);

    @Mapping(source = "competitionId", target = "competition")
    AccreditationStep toEntity(AccreditationStepDTO accreditationStepDTO);

    default AccreditationStep fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccreditationStep accreditationStep = new AccreditationStep();
        accreditationStep.setId(id);
        return accreditationStep;
    }
}
