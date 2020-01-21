package com.service.mapper;

import com.domain.*;
import com.service.dto.ConfrontationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Confrontation} and its DTO {@link ConfrontationDTO}.
 */
@Mapper(componentModel = "spring", uses = {MatchSheetMapper.class, LocalisationMapper.class, JourneeMapper.class})
public interface ConfrontationMapper extends EntityMapper<ConfrontationDTO, Confrontation> {

    @Mapping(source = "matchSheet.id", target = "matchSheetId")
    @Mapping(source = "localisation.id", target = "localisationId")
    @Mapping(source = "journee.id", target = "journeeId")
    ConfrontationDTO toDto(Confrontation confrontation);

    @Mapping(source = "matchSheetId", target = "matchSheet")
    @Mapping(source = "localisationId", target = "localisation")
    @Mapping(target = "scores", ignore = true)
    @Mapping(target = "removeScore", ignore = true)
    @Mapping(target = "teams", ignore = true)
    @Mapping(target = "removeTeam", ignore = true)
    @Mapping(source = "journeeId", target = "journee")
    Confrontation toEntity(ConfrontationDTO confrontationDTO);

    default Confrontation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Confrontation confrontation = new Confrontation();
        confrontation.setId(id);
        return confrontation;
    }
}
