package com.uc.ms_security.mapper;

import com.uc.ms_security.dto.profile.CreateProfileDTO;
import com.uc.ms_security.dto.profile.ProfileResponseDTO;
import com.uc.ms_security.dto.profile.UpdateProfileDTO;
import com.uc.ms_security.entity.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfileMapper {

    public Profile toEntity(CreateProfileDTO dto) {
        Profile profile = new Profile();

        profile.setPhone(dto.getPhone());
        profile.setBirthDate(dto.getBirthDate());

        return profile;
    }

    public void updateEntity(UpdateProfileDTO dto, Profile profile) {
        if (dto.getPhone() != null) {
            profile.setPhone(dto.getPhone());
        }

        profile.setBirthDate(dto.getBirthDate());
    }

    public ProfileResponseDTO toResponseDTO(Profile profile) {
        //Retorno temprano (No se recomienda hacerlo)
        if (profile == null) { //Cómo el usuario es recien creado, no tiene perfil, entonces si es null, retorno null
            return null;
        }

        return new ProfileResponseDTO(
                profile.getId(),
                profile.getPhone(),
                profile.getBirthDate()
        );
    }
}
