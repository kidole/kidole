package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.DisciplineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Discipline} and its DTO {@link DisciplineDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitionMapper.class, PhaseMapper.class})
public interface DisciplineMapper extends EntityMapper<DisciplineDTO, Discipline> {

    @Mapping(source = "competition.id", target = "competitionId")
    @Mapping(source = "competition.competitionName", target = "competitionCompetitionName")
    @Mapping(source = "phase.id", target = "phaseId")
    @Mapping(source = "phase.phaseName", target = "phasePhaseName")
    DisciplineDTO toDto(Discipline discipline);

    @Mapping(source = "competitionId", target = "competition")
    @Mapping(source = "phaseId", target = "phase")
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
