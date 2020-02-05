package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.PrestationServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrestationService} and its DTO {@link PrestationServiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {RubriqueMapper.class, UserMapper.class})
public interface PrestationServiceMapper extends EntityMapper<PrestationServiceDTO, PrestationService> {

    @Mapping(source = "rubrique.id", target = "rubriqueId")
    @Mapping(source = "rubrique.rubriqueName", target = "rubriqueRubriqueName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    PrestationServiceDTO toDto(PrestationService prestationService);

    @Mapping(source = "rubriqueId", target = "rubrique")
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "removeFiles", ignore = true)
    @Mapping(target = "localisations", ignore = true)
    @Mapping(target = "removeLocalisation", ignore = true)
    @Mapping(source = "userId", target = "user")
    PrestationService toEntity(PrestationServiceDTO prestationServiceDTO);

    default PrestationService fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrestationService prestationService = new PrestationService();
        prestationService.setId(id);
        return prestationService;
    }
}
