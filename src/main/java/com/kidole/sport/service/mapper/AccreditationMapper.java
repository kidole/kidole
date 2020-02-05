package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.AccreditationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Accreditation} and its DTO {@link AccreditationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitionMapper.class})
public interface AccreditationMapper extends EntityMapper<AccreditationDTO, Accreditation> {

    @Mapping(source = "competition.id", target = "competitionId")
    AccreditationDTO toDto(Accreditation accreditation);

    @Mapping(source = "competitionId", target = "competition")
    Accreditation toEntity(AccreditationDTO accreditationDTO);

    default Accreditation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Accreditation accreditation = new Accreditation();
        accreditation.setId(id);
        return accreditation;
    }
}
