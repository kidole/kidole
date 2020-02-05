package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.RubriqueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rubrique} and its DTO {@link RubriqueDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RubriqueMapper extends EntityMapper<RubriqueDTO, Rubrique> {


    @Mapping(target = "files", ignore = true)
    @Mapping(target = "removeFiles", ignore = true)
    Rubrique toEntity(RubriqueDTO rubriqueDTO);

    default Rubrique fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rubrique rubrique = new Rubrique();
        rubrique.setId(id);
        return rubrique;
    }
}
