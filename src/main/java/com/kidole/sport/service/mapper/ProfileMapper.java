package com.kidole.sport.service.mapper;

import com.kidole.sport.domain.*;
import com.kidole.sport.service.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AccreditationMapper.class})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "accreditation.id", target = "accreditationId")
    @Mapping(source = "accreditation.accreditationName", target = "accreditationAccreditationName")
    ProfileDTO toDto(Profile profile);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "accreditationId", target = "accreditation")
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
