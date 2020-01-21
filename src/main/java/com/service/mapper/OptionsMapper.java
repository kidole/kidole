package com.service.mapper;

import com.domain.*;
import com.service.dto.OptionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Options} and its DTO {@link OptionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitionMapper.class})
public interface OptionsMapper extends EntityMapper<OptionsDTO, Options> {

    @Mapping(source = "competition.id", target = "competitionId")
    OptionsDTO toDto(Options options);

    @Mapping(source = "competitionId", target = "competition")
    Options toEntity(OptionsDTO optionsDTO);

    default Options fromId(Long id) {
        if (id == null) {
            return null;
        }
        Options options = new Options();
        options.setId(id);
        return options;
    }
}
