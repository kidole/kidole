package com.service.mapper;

import com.domain.*;
import com.service.dto.MatchSheetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchSheet} and its DTO {@link MatchSheetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MatchSheetMapper extends EntityMapper<MatchSheetDTO, MatchSheet> {


    @Mapping(target = "confrontation", ignore = true)
    MatchSheet toEntity(MatchSheetDTO matchSheetDTO);

    default MatchSheet fromId(Long id) {
        if (id == null) {
            return null;
        }
        MatchSheet matchSheet = new MatchSheet();
        matchSheet.setId(id);
        return matchSheet;
    }
}
