package com.service.mapper;

import com.domain.*;
import com.service.dto.DisciplineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Discipline} and its DTO {@link DisciplineDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitionMapper.class})
public interface DisciplineMapper extends EntityMapper<DisciplineDTO, Discipline> {

    @Mapping(source = "competition.id", target = "competitionId")
    DisciplineDTO toDto(Discipline discipline);

    @Mapping(target = "phases", ignore = true)
    @Mapping(target = "removePhase", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "removeCategory", ignore = true)
    @Mapping(source = "competitionId", target = "competition")
    Discipline toEntity(DisciplineDTO disciplineDTO);

    default Discipline fromId(Long id) {
        if (id == null) {
            return null;
        }
        Discipline discipline = new Discipline();
        discipline.setId(id);
        return discipline;
    }
}
