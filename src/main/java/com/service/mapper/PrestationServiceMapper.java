package com.service.mapper;

import com.domain.*;
import com.service.dto.PrestationServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrestationService} and its DTO {@link PrestationServiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {RubriqueMapper.class})
public interface PrestationServiceMapper extends EntityMapper<PrestationServiceDTO, PrestationService> {

    @Mapping(source = "rubrique.id", target = "rubriqueId")
    PrestationServiceDTO toDto(PrestationService prestationService);

    @Mapping(source = "rubriqueId", target = "rubrique")
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "removeFiles", ignore = true)
    @Mapping(target = "localisations", ignore = true)
    @Mapping(target = "removeLocalisation", ignore = true)
    @Mapping(target = "competitionservicejoined", ignore = true)
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
