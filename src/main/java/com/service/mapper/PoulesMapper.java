package com.service.mapper;

import com.domain.*;
import com.service.dto.PoulesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Poules} and its DTO {@link PoulesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PoulesMapper extends EntityMapper<PoulesDTO, Poules> {


    @Mapping(target = "teams", ignore = true)
    @Mapping(target = "removeTeam", ignore = true)
    Poules toEntity(PoulesDTO poulesDTO);

    default Poules fromId(Long id) {
        if (id == null) {
            return null;
        }
        Poules poules = new Poules();
        poules.setId(id);
        return poules;
    }
}
