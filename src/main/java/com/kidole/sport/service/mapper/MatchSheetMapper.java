package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.MatchSheetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchSheet} and its DTO {@link MatchSheetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MatchSheetMapper extends EntityMapper<MatchSheetDTO, MatchSheet> {



    default MatchSheet fromId(Long id) {
        if (id == null) {
            return null;
        }
        MatchSheet matchSheet = new MatchSheet();
        matchSheet.setId(id);
        return matchSheet;
    }
}
