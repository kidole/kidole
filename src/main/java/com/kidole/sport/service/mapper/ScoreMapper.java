package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.ScoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Score} and its DTO {@link ScoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {TeamMapper.class, ConfrontationMapper.class})
public interface ScoreMapper extends EntityMapper<ScoreDTO, Score> {

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.teamName", target = "teamTeamName")
    @Mapping(source = "confrontation.id", target = "confrontationId")
    ScoreDTO toDto(Score score);

    @Mapping(source = "teamId", target = "team")
    @Mapping(source = "confrontationId", target = "confrontation")
    Score toEntity(ScoreDTO scoreDTO);

    default Score fromId(Long id) {
        if (id == null) {
            return null;
        }
        Score score = new Score();
        score.setId(id);
        return score;
    }
}
