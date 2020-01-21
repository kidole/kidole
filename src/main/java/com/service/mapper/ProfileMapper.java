package com.service.mapper;

import com.domain.*;
import com.service.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AccreditationMapper.class})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "userAccreditation.id", target = "userAccreditationId")
    ProfileDTO toDto(Profile profile);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "userAccreditationId", target = "userAccreditation")
    Profile toEntity(ProfileDTO profileDTO);

    default Profile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}
