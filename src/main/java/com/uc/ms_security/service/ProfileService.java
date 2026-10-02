package com.uc.ms_security.service;

import com.uc.ms_security.dto.profile.CreateProfileDTO;
import com.uc.ms_security.dto.profile.ProfileResponseDTO;
import com.uc.ms_security.dto.profile.UpdateProfileDTO;
import com.uc.ms_security.entity.Profile;
import com.uc.ms_security.entity.User;
import com.uc.ms_security.exception.ApplicationException;
import com.uc.ms_security.exception.ErrorCase;
import com.uc.ms_security.mapper.ProfileMapper;
import com.uc.ms_security.repository.ProfileRepository;
import com.uc.ms_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;
    
    //CREA PERFIL PARA UN USUARIO
    public ProfileResponseDTO create(Long userId, CreateProfileDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Usuario no encontrado con id: " + userId
                ));

        if (profileRepository.existsByUserId(userId)) {
            throw new ApplicationException(
                    ErrorCase.ALREADY_EXISTS,
                    "El usuario ya tiene un perfil"
            );
        }

        Profile profile = profileMapper.toEntity(dto);
        profile.setUser(user);
        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toResponseDTO(savedProfile);
    }

    //BUSCAR PERFIL POR ID DE USUARIO
    //Busca un perfil dado un ID de Usuario y lo retorna
    private Profile findProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Perfil no encontrado para el usuario con id: " + userId
                ));
    }

    //RETORNA UN OBJETO TIPO "profile" Y LO CONVIERTE A "ProfileResponseDTO"
    //Retorna un perfil con algunos atributos filtrados
    public ProfileResponseDTO findByUserId(Long userId) {
        return profileMapper.toResponseDTO(findProfileByUserId(userId));
    }

    //ACTUALIZA PERFIL SEGUN UN USUARIO
    public ProfileResponseDTO updateByUserId(Long userId, UpdateProfileDTO dto) {
        Profile profile = findProfileByUserId(userId);

        if (dto.getPhone() != null && !profile.getPhone().equals(dto.getPhone())
                && profileRepository.existsByPhone(dto.getPhone())) {
            throw new ApplicationException(
                    ErrorCase.ALREADY_EXISTS,
                    "El teléfono pertenece a otro perfil"
            );
        }

        
        profileMapper.updateEntity(dto, profile);
        return profileMapper.toResponseDTO(profileRepository.save(profile));
    }

    //ELIMINA PERFIL SEGUN UN USUARIO
    public void deleteByUserId(Long userId) {
        profileRepository.delete(findProfileByUserId(userId));
    }

}
